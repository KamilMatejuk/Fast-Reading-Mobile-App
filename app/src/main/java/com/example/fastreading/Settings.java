package com.example.fastreading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fastreading.notifications.Reciever;

import java.security.KeyStore;
import java.util.Calendar;

public class Settings extends AppCompatActivity {

    EditText editText_time;
    EditText editText_rounds;
    EditText editText_size;
    EditText editText_width;
    EditText editText_count;

    private final static int MY_PERMISSIONS_REQUEST_WAKE_LOCK = 0;
    int millis, interval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        editText_time = initialize("editText_time", "db", "time", 1000);
        editText_rounds = initialize("editText_rounds", "db", "rounds", 10);
        editText_size = initialize("editText_size", "db", "size", 2);
        editText_width = initialize("editText_width", "db", "width", 20);
        editText_count = initialize("editText_count", "db", "count", 1);

        addSwitchListener();
    }

    private void addSwitchListener() {
        final Switch switchbtn = findViewById(R.id.switch_notifications);
        SharedPreferences sharedPreferences = getSharedPreferences("db", Context.MODE_PRIVATE);
        String alarm = sharedPreferences.getString("alarm", "none");
        switchbtn.setChecked(!alarm.equals("none"));
        System.out.println(alarm);

        switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker = new TimePickerDialog(Settings.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            System.out.println( selectedHour + ":" + selectedMinute);
                            saveNotification(selectedHour, selectedMinute);
                            createSystemNotifications(selectedHour, selectedMinute);
                        }
                    }, hour, minute, true);
                    mTimePicker.setOnCancelListener(new DialogInterface.OnCancelListener(){
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            switchbtn.setChecked(false);
                        }
                    });
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                } else {
                    deleteNotification();
                }
            }
        });
    }

    private void createSystemNotifications(int selectedHour, int selectedMinute) {

        millis = 1000 * (60*60*selectedHour + 60*selectedMinute);
        interval = 1000 * 60 * 60 * 24; //24h

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WAKE_LOCK}, MY_PERMISSIONS_REQUEST_WAKE_LOCK);
        } else {
            Intent myIntent = new Intent(this, Reciever.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (getApplicationContext(), 2, myIntent , PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  millis,
                    interval, pendingIntent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WAKE_LOCK: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    Intent myIntent = new Intent(this, Reciever.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast
                            (getApplicationContext(), 2, myIntent , PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  millis,
                            interval, pendingIntent);
                } else {
                    // permission denied, boo!
                }
            }
        }
    }

    private void saveNotification(int selectedHour, int selectedMinute) {
        SharedPreferences sharedPreferences = getSharedPreferences("db", Context.MODE_PRIVATE);
        String alarm = selectedHour+":"+selectedMinute;
        sharedPreferences.edit().putString("alarm", alarm).apply();
    }

    private void deleteNotification() {
        SharedPreferences sharedPreferences = getSharedPreferences("db", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("alarm").apply();
        //TODO wyłączyć powiadomienia
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

    public void changeNotificationTime(View view) {
    }
}
