package com.example.fastreading.trening;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fastreading.R;
import com.example.fastreading.Results;

import java.util.ArrayList;

public abstract class AbstractTrening extends AppCompatActivity {

    TextView textView;
    EditText editText;
    int time, rounds, size, width, count;
    boolean waitingForInput = true;
    ArrayList<String> userAnswers = null;
    ArrayList<String> givenNumbers = null;

    protected abstract void textChanged(CharSequence s); // wyłapywanie kiedy klient skończył wpisywać
    protected abstract void newText(); // wyswietlanie tekstu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trening);

        textView = findViewById(R.id.textView_speed);
        editText = findViewById(R.id.editText_speed);

        SharedPreferences sharedPreferences = getSharedPreferences("db", Context.MODE_PRIVATE);
        time = sharedPreferences.getInt("time", 1000);
        rounds = sharedPreferences.getInt("rounds", 10);
        size = sharedPreferences.getInt("size", 2);
        width = sharedPreferences.getInt("width", 20);
        count = sharedPreferences.getInt("count", 1);


        countdown();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChanged(s);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void countdown() {
        editText.setVisibility(View.INVISIBLE);
        textView.setTextSize(50);
        new Thread() {
            public void run() {
                try {
                    for(int i=3; i>=0; i--){
                        final int finalI = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String c = (finalI>0 ? String.valueOf(finalI) : "Start!");
                                textView.setText(c);
                            }
                        });
                        Thread.sleep(1000);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editText.setVisibility(View.VISIBLE);
                            textView.setTextSize(30);
                        }
                    });
                    startTest();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void startTest() {
        userAnswers = new ArrayList<>();
        givenNumbers = new ArrayList<>();
        new Thread() {
            public void run() {
                try {
                    for(int i=0; i<rounds; i++){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                newText();
                            }
                        });
                        Thread.sleep(time);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(" ");
                            }
                        });
                        while(waitingForInput){}
                        waitingForInput = true;
                    }
                    finishTest();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void finishTest() {
        Intent intent = new Intent(getApplicationContext(), Results.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("userAnswers", userAnswers);
        bundle.putStringArrayList("givenNumbers", givenNumbers);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
