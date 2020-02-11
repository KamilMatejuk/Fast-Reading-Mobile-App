package com.example.fastreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastreading.external.ObjectSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Results extends AppCompatActivity {

    ArrayList<String> userAnswers = null;
    ArrayList<String> givenNumbers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            userAnswers = bundle.getStringArrayList("userAnswers");
            givenNumbers = bundle.getStringArrayList("givenNumbers");
            show();
        } else {
            finish();
            Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show();
        }

    }

    private void show() {
        int correct = 0, incorrect = 0;
        String s = "your Vs correct<br>";
        for(int i=0; i<userAnswers.size(); i++){
            String user = userAnswers.get(i);
            String given = givenNumbers.get(i);
            if(given.equals(user)){
                s += "<font color=green>"+user+" ... "+given+"</font><br>";
                correct++;
            } else {
                s += "<font color=red>"+user+" ... "+given+"</font><br>";
                incorrect++;
            }
        }
        s = "<br>"+ (int) (100*((double)correct)/(correct+incorrect)) + "%<br><br>" + s;

        save(correct, incorrect);

        TextView textView = findViewById(R.id.textView);
        textView.setText(Html.fromHtml(s));
    }

    private void save(int correct, int incorrect) {
        SharedPreferences sharedPreferences = getSharedPreferences("db", Context.MODE_PRIVATE);

        ArrayList<SavingRecord> savings = (ArrayList<SavingRecord>) ObjectSerializer.deserialize(
                sharedPreferences.getString("savings", null));

        if(savings != null){
            if(savings.get(savings.size()-1).isToday()){
                savings.get(savings.size()-1).addCorrect(correct);
                savings.get(savings.size()-1).addInorrect(incorrect);
            } else {
                SavingRecord todays = new SavingRecord();
                todays.addCorrect(correct);
                todays.addInorrect(incorrect);
                savings.add(todays);
            }
        } else {
            savings = new ArrayList<>();
            SavingRecord todays = new SavingRecord();
            todays.addCorrect(correct);
            todays.addInorrect(incorrect);
            savings.add(todays);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("savings", ObjectSerializer.serialize(savings)).apply();
    }

}
