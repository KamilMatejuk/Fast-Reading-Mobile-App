package com.example.fastreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fastreading.trening.Observation;
import com.example.fastreading.trening.Speed;
import com.example.fastreading.trening.Width;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }

    public void btn1(View view) {
        //trening - coraz mniejszy czas wyswietlania, liczby z rosnacą liczbą cyfr
        startActivity(new Intent(this, Speed.class));
    }

    public void btn2(View view) {
        //width - coraz bardziej rozwalone po ekranie  w jednej linii
        //startActivity(new Intent(this, Width.class));
        Toast.makeText(getApplicationContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
    }

    public void btn3(View view) {
        //observation - coraz bardziej rozwalone po ekranie (góra, dół, prawo, lewo)
        //startActivity(new Intent(this, Observation.class));
        Toast.makeText(getApplicationContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
    }

    public void set(View view) {
        startActivity(new Intent(this, Settings.class));
    }

}
