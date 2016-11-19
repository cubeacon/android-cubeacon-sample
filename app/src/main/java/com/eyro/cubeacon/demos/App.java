package com.eyro.cubeacon.demos;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.eyro.cubeacon.CBBootstrapListener;
import com.eyro.cubeacon.CBBootstrapRegion;
import com.eyro.cubeacon.CBRegion;
import com.eyro.cubeacon.Cubeacon;
import com.eyro.cubeacon.LogLevel;
import com.eyro.cubeacon.Logger;
import com.eyro.cubeacon.MonitoringState;

import java.util.UUID;

/**
 * Created by Eyro on 10/31/15.
 */
public class App extends Application implements CBBootstrapListener {
    private static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        // set Cubeacon SDK log level to verbose mode
        Logger.setLogLevel(LogLevel.VERBOSE);

        // enable background power saver to save battery life up to 60%
        Cubeacon.setBackgroundPowerSaver(true);

        // initializing Cubeacon SDK
        Cubeacon.initialize(this);

        // setup region scanning when OS boot completed
        CBBootstrapRegion.setup(this,
                new CBRegion("com.eyro.cubeacon.bootstrap_region",
                        UUID.fromString("cb10023f-a318-3394-4199-a8730c7c1aec")));



    }

    @Override
    public void didEnterRegion(CBRegion region) {
        Log.d(TAG, "Entering region: " + region);
    }

    @Override
    public void didExitRegion(CBRegion region) {
        Log.d(TAG, "Exiting region: " + region);
    }

    @Override
    public void didDetermineStateForRegion(MonitoringState state, CBRegion region) {
        Log.d(TAG, "Region: " + region + ", state: " + state.name());
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MonitoringActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 1234,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(5678, notification);
    }
}
