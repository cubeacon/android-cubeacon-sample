package com.eyro.cubeacon.demos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eyro.cubeacon.CBApp;
import com.eyro.cubeacon.CBBeacon;

public class MainActivity extends Activity {
    String tag = "MainActivity";
    ProgressDialog progress;
    TextView       textDemos;
    ScrollView main;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CBApp.createService(this);
        setContentView(R.layout.activity_main);
        textDemos = (TextView) findViewById(R.id.textDemos);
        main = (ScrollView) findViewById(R.id.main_view);
        CBApp.setBeaconListener(this, new CBApp.BeaconListener() {
            
            @Override
            public void onExit(CBBeacon beacon, long timeInterval) {
                // TODO Auto-generated method stub
                main.setBackgroundColor(Color.WHITE);
                Log.i(tag, beacon.getName() + " exited, major: " + beacon.getMajor() + ", minor: " + beacon.getMinor());
            }
            
            @Override
            public void onEnter(CBBeacon beacon) {
                // TODO Auto-generated method stub
                main.setBackgroundColor(beacon.getColor());
                textDemos.setText(beacon.getName());
                Log.i(tag, beacon.getName() + " entered, major: " + beacon.getMajor() + ", minor: " + beacon.getMinor());
            }
            
            @Override
            public void onChange(CBBeacon old, CBBeacon current) {
                // TODO Auto-generated method stub
                main.setBackgroundColor(current.getColor());
                textDemos.setText(current.getName());
                Log.i(tag, current.getName() + " changed, major: " + current.getMajor() + ", minor: " + current.getMinor());
            }
        });
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
    protected void onStart() {
        CBApp.startService(this);
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CBApp.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        CBApp.destroyService(this);
        super.onDestroy();
    }
}
