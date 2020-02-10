package com.example.fastreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    TextView textView_time;
    EditText editText_time;
    TextView textView_count;
    EditText editText_count;
    TextView textView_size;
    EditText editText_size;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        textView_time = findViewById(R.id.textview_time);
        editText_time = findViewById(R.id.editText_time);
        textView_count = findViewById(R.id.textView_count);
        editText_count = findViewById(R.id.editText_count);
        textView_size = findViewById(R.id.textView_size);
        editText_size = findViewById(R.id.editText_size);

        sharedPreferences = getSharedPreferences("db", Context.MODE_PRIVATE);
        editText_time.setHint(String.valueOf(sharedPreferences.getInt("time", 1000)));
        editText_count.setHint(String.valueOf(sharedPreferences.getInt("count", 10)));
        editText_size.setHint(String.valueOf(sharedPreferences.getInt("size", 2)));
    }

    public void save(View view) {
        try {
            int timeMin=50, timeMax=2000;
            int countMin=5, countMax=20;
            int sizeMin=1, sizeMax=10;

            String t = (editText_time.getText().toString().isEmpty() ? editText_time.getHint().toString() : editText_time.getText().toString());
            String c = (editText_count.getText().toString().isEmpty() ? editText_count.getHint().toString() : editText_count.getText().toString());
            String s = (editText_size.getText().toString().isEmpty() ? editText_size.getHint().toString() : editText_size.getText().toString());
            int time = Integer.parseInt(t);
            int count = Integer.parseInt(c);
            int size = Integer.parseInt(s);
            if(time < timeMin || time > timeMax){
                editText_time.setError("Time should be between "+timeMin+" and "+timeMax);
            } else if (count < countMin || count > countMax){
                editText_count.setError("Count should be between "+countMin+" and "+countMax);
            } else if (size < sizeMin|| size > sizeMax){
                editText_size.setError("Size should be between "+sizeMin+" and "+sizeMax);
            } else {
                sharedPreferences.edit().putInt("time", time).apply();
                sharedPreferences.edit().putInt("count", count).apply();
                sharedPreferences.edit().putInt("size", size).apply();
                finish();
                Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex){
            System.out.println("Exception converting inout data into Integers");
        }
    }
}
