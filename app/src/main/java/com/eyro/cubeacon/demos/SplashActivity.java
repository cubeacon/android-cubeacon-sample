package com.eyro.cubeacon.demos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.eyro.cubeacon.CBApp;
import com.eyro.cubeacon.callback.RequestCallback;
import com.eyro.cubeacon.constant.ScanningMode;
import com.eyro.cubeacon.listener.BeaconListener;
import com.eyro.cubeacon.model.CBBeacon;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // setting scan period
        CBApp.getInstance().setScanPeriod(ScanningMode.VERY_HIGH);

        // getting beacon's and storyline's data in the cloud
        CBApp.getInstance().getDataInBackground(new RequestCallback() {
            @Override
            public void onCompleted(Exception e) {
                // showing a toast when there is an exception happen
                if (e != null) {
                    new AlertDialog.Builder(SplashActivity.this)
                            .setTitle("ERROR")
                            .setMessage(e.getMessage())
                            .setNegativeButton("EXIT APP", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SplashActivity.this.finish();
                                }
                            })
                            .show();
                    return;
                }

                // start main activity when there is no exception happen
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
