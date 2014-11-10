package com.eyro.cubeacon.demos;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eyro.cubeacon.CBActivity;
import com.eyro.cubeacon.CBBeacon;

public class MainActivity extends CBActivity {
    String                     tag             = "MainActivity";
    ProgressDialog             progress;
    TextView                   textDemos;
    ScrollView                 main;
    static NotificationManager notificationManager;
    static final int           NOTIFICATION_ID = 123456;
    public static final String EXTRA_INTENT    = "NOTIFICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        setContentView(R.layout.activity_main);
        textDemos = (TextView) findViewById(R.id.textDemos);
        main = (ScrollView) findViewById(R.id.main_view);
    }

    void showNotif(String msg, String brochurePathFile){
        Intent notifyIntent = new Intent(MainActivity.this, BeaconActivity.class);
        notifyIntent.putExtra(MainActivity.EXTRA_INTENT, brochurePathFile);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(
                MainActivity.this,
                0,
                new Intent[]{notifyIntent},
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(MainActivity.this)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle("Notify Demo")
            .setContentText(msg)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
        .setCancelable(false)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    protected void onBeaconExited(CBBeacon beacon, long timeInterval) {
        // TODO Auto-generated method stub
        Log.i(tag, beacon.getName() + " exited, major: " + beacon.getMajor() + ", minor: " + beacon.getMinor());
    }

    @Override
    protected void onBeaconEntered(CBBeacon beacon) {
        // TODO Auto-generated method stub
        showNotif(beacon.getName() + " entered region", beacon.getBrochureUrl());
        Log.i(tag, beacon.getName() + " entered, major: " + beacon.getMajor() + ", minor: " + beacon.getMinor());
    }

    @Override
    protected void onBeaconChanged(CBBeacon old, CBBeacon current) {
        // TODO Auto-generated method stub
        //main.setBackground(Drawable.createFromPath(current.getBrochureLocalPath()));
        Log.i(tag, current.getName() + " changed, major: " + current.getMajor() + ", minor: " + current.getMinor());
    }
}
