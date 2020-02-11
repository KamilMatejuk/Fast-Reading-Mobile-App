package com.example.fastreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastreading.external.ObjectSerializer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class History extends AppCompatActivity {

    TextView textView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        textView = findViewById(R.id.textView);

        sharedPreferences = getSharedPreferences("db", Context.MODE_PRIVATE);
        ArrayList<SavingRecord> savings = (ArrayList<SavingRecord>) ObjectSerializer.deserialize(
                sharedPreferences.getString("savings", null));

        if(savings == null){
            textView.setText(R.string.empty_hist);
            findViewById(R.id.reset).setVisibility(View.INVISIBLE);
        } else {
            showRecords(savings);
        }
    }

    private void showRecords(ArrayList<SavingRecord> savings) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String s = "";
        for(SavingRecord record : savings){
            s += formatter.format(record.getDate())+", "+record.getCount()+" tries, "+record.getPercentage()+"% correct\n";
        }
        textView.setText(s);
    }

    public void reset(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("savings", ObjectSerializer.serialize(null)).apply();
        finish();
        Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show();
    }
}
