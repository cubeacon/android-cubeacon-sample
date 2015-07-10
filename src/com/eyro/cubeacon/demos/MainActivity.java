package com.eyro.cubeacon.demos;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eyro.cubeacon.CBActivity;
import com.eyro.cubeacon.CBBeacon;
import com.eyro.cubeacon.CBConstant.CBCampaignType;

public class MainActivity extends CBActivity {
    String                     tag             = "MainActivity";
    ProgressDialog             progress;
    TextView                   textDemos;
    ScrollView                 main;
    static NotificationManager notificationManager;
    static final int           NOTIFICATION_ID = 123456;
    public static final String EXTRA_INTENT    = "MESSAGE";

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
    protected void onExitedBeacon(CBBeacon beacon, long timeInterval) {
        // TODO Auto-generated method stub
        Log.i(tag, beacon.getName() + " exited, major: " + beacon.getMajor() + ", minor: " + beacon.getMinor());
    }

    @Override
    protected void onEnteredBeacon(CBBeacon beacon) {
        // TODO Auto-generated method stub
		String message = beacon.getName() + " entered, major: " + beacon.getMajor() + ", minor: " + beacon.getMinor();
        Log.i(tag, message);
		Intent intent = new Intent(this, BeaconActivity.class);
		intent.putExtra(MainActivity.EXTRA_INTENT, message);
		startActivity(intent);
    }

    @Override
    protected void onEmptyBeacon() {
        // TODO Auto-generated method stub
        main.setBackground(new ColorDrawable(android.R.color.transparent));
    }

    @Override
    protected void onNearestBeaconChanged(CBBeacon old, CBBeacon current) {
        openCampaign(current);
        Log.i(tag, current.getName() + " changed, major: " + current.getMajor() + ", minor: " + current.getMinor());
    }
    
    @Override
    protected void onImmediateBeacon(CBBeacon beacon) {
        openCampaign(beacon);
        Log.i(tag, beacon.getName() + " on immediate proximity, major: " + beacon.getMajor() + ", minor: " + beacon.getMinor());
    }
    
    @Override
    protected void onNearBeacon(CBBeacon beacon) {
        openCampaign(beacon);
        Log.i(tag, beacon.getName() + " on near proximity, major: " + beacon.getMajor() + ", minor: " + beacon.getMinor());
    }
    
    @Override
    protected void onFarBeacon(CBBeacon beacon) {
        openCampaign(beacon);
        Log.i(tag, beacon.getName() + " on far proximity, major: " + beacon.getMajor() + ", minor: " + beacon.getMinor());
    }
    
    private void openCampaign(CBBeacon beacon) {
        CBCampaignType campaign = beacon.getStoryline().getCampaign();
        // TODO Auto-generated method stub
        if(campaign.equals(CBCampaignType.IMAGE)){
            // TODO Show brochure image
        }else if(campaign.equals(CBCampaignType.HTML)){
            // TODO Show html via webview
        }else if(campaign.equals(CBCampaignType.URL)){
            // TODO Open url in a webview/browser
        }else if(campaign.equals(CBCampaignType.VIDEO)){
            // TODO Play a video streaming
        }
    }
}
