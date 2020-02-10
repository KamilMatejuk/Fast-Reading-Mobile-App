package com.example.fastreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    EditText editText_time;
    EditText editText_rounds;
    EditText editText_size;
    EditText editText_width;
    EditText editText_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        editText_time = initialize("editText_time", "db", "time", 1000);
        editText_rounds = initialize("editText_rounds", "db", "rounds", 10);
        editText_size = initialize("editText_size", "db", "size", 2);
        editText_width = initialize("editText_width", "db", "width", 20);
        editText_count = initialize("editText_count", "db", "count", 1);
    }

    public void save(View view) {
        int time = getValue(editText_time);
        int rounds = getValue(editText_rounds);
        int size = getValue(editText_size);
        int width = getValue(editText_width);
        int count = getValue(editText_count);

        if(checkValues(editText_time, "Time", time, 50, 2000, "ms") &&
                checkValues(editText_rounds, "Rounds", rounds, 5, 20, "") &&
                checkValues(editText_size, "Size", size, 1, 10, "") &&
                checkValues(editText_width, "Width", width, 10, 100, "%") &&
                checkValues(editText_count, "Count", count, 1, 5, "")){
            SharedPreferences sharedPreferences = getSharedPreferences("db", Context.MODE_PRIVATE);
            sharedPreferences.edit().putInt("time", time).apply();
            sharedPreferences.edit().putInt("rounds", rounds).apply();
            sharedPreferences.edit().putInt("size", size).apply();
            sharedPreferences.edit().putInt("width", width).apply();
            sharedPreferences.edit().putInt("count", count).apply();
            finish();
            Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
        }
    }

    private EditText initialize(String editTextId, String dbName, String dbKey, int dbDefault) {
        EditText editText = null;
        try {
            editText = findViewById(R.id.class.getField(editTextId).getInt(null));
            SharedPreferences sharedPreferences = getSharedPreferences(dbName, Context.MODE_PRIVATE);
            editText.setHint(String.valueOf(sharedPreferences.getInt(dbKey, dbDefault)));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return editText;
    }

    private int getValue(EditText editText){
        try {
            String s = (editText.getText().toString().isEmpty() ? editText.getHint().toString() : editText.getText().toString());
            return Integer.parseInt(s);
        } catch (Exception e){
            return 0;
        }
    }

    private boolean checkValues(EditText editText, String name, int value, int min, int max, String unit){
        if(value < min || value > max){
            editText.setError(name+" should be between "+min+unit+" and "+max+unit);
            return false;
        } else {
            return true;
        }
    }

}
