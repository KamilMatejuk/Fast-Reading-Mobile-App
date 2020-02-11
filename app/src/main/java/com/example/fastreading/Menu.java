package com.example.fastreading;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fastreading.trening.Observation;
import com.example.fastreading.trening.Speed;
import com.example.fastreading.trening.Width;

public class Menu extends AppCompatActivity {

    //todo zrobic posiomy zamiast ustawien

    private static final String CHANNEL_ID = "Channel_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        createChannelForNotifications();
    }

    private void createChannelForNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void btn1(View view) {
        //trening - coraz mniejszy czas wyswietlania, liczby z rosnacą liczbą cyfr
        startActivity(new Intent(this, Speed.class));
    }

    public void btn2(View view) {
        //width - coraz bardziej rozwalone po ekranie  w jednej linii
        startActivity(new Intent(this, Width.class));
//        Toast.makeText(getApplicationContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
    }

    public void btn3(View view) {
        //reflex - jeden wyraz, ale nie wiadomo gdzie sie pojawi na ekranie
        //startActivity(new Intent(this, Reflex.class));
        Toast.makeText(getApplicationContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
    }

    public void btn4(View view) {
        //observation - pare wyrazow coraz bardziej rozwalone po ekranie (góra, dół, prawo, lewo)
        //startActivity(new Intent(this, Observation.class));
        Toast.makeText(getApplicationContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
    }

    public void set(View view) {
        startActivity(new Intent(this, Settings.class));
    }

    public void history(View view) {
        startActivity(new Intent(this, History.class));
    }
}
