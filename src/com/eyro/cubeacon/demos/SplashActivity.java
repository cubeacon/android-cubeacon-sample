package com.eyro.cubeacon.demos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.eyro.cubeacon.CB.RefreshBeaconCallback;
import com.eyro.cubeacon.CBApp;

public class SplashActivity extends Activity {

    private TextView	txtLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtLoading = (TextView) findViewById(R.id.txtProgressBarSplash);

        txtLoading.setText("Initializing Cubeacon");
        CBApp.refreshBeaconInBackground(this, new RefreshBeaconCallback() {
            
            @Override
            public void onRefreshCompleted() {
                Intent intent = new Intent(SplashActivity.this,
                        MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
