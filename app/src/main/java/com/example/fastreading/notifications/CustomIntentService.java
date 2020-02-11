package com.example.fastreading.notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.fastreading.Menu;
import com.example.fastreading.R;
import com.example.fastreading.SavingRecord;
import com.example.fastreading.external.ObjectSerializer;

import java.util.ArrayList;
import java.util.Date;

public class CustomIntentService extends JobIntentService {

    // https://stackoverflow.com/questions/34517520/how-to-give-notifications-on-android-on-specific-time

    private static final int NOTIFICATION_ID = 1234;
    private static final String CHANNEL_ID = "Channel_id";
    private static final int MIN_DAILY_COUNT = 100;

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        SharedPreferences sharedPreferences = getSharedPreferences("db", Context.MODE_PRIVATE);

        ArrayList<SavingRecord> savings = (ArrayList<SavingRecord>) ObjectSerializer.deserialize(
                sharedPreferences.getString("savings", null));

        if(savings==null || !savings.get(savings.size()-1).isToday() || savings.get(savings.size()-1).getCount()<MIN_DAILY_COUNT){
            String title = "Exercise Reminder";
            String longText = "Remember to train your fast reading daily. \nYou haven't trained today!";

            Intent myIntent = new Intent(this, Menu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, myIntent, 0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setContentTitle(title)
                    .setContentText(longText)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(longText))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            System.out.println(new Date());
        }
    }
}