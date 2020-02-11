package com.example.fastreading.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Reciever extends BroadcastReceiver {

    public Reciever() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, CustomIntentService.class);
        context.startService(intent1);
    }
}