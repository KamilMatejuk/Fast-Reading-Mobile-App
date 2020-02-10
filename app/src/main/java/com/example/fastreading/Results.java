package com.example.fastreading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

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
            //todo zapisać historie wynikow https://stackoverflow.com/questions/7057845/save-arraylist-to-sharedpreferences
        } else {
            finish();
            Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show();
        }

    }

    private void show() {
        int c = 0;
        String s = "your Vs correct<br>";
        for(int i=0; i<userAnswers.size(); i++){
            String user = userAnswers.get(i);
            String given = givenNumbers.get(i);
            if(given.equals(user)){
                s += "<font color=green>"+user+" ... "+given+"</font><br>";
                c++;
            } else {
                s += "<font color=red>"+user+" ... "+given+"</font><br>";
            }
        }
        s = "<br>"+ ((int) 100*c/userAnswers.size()) + "%<br><br>" + s;

        TextView textView = findViewById(R.id.textView);
        textView.setText(Html.fromHtml(s));
    }
}
