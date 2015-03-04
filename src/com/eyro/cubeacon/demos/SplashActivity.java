package com.eyro.cubeacon.demos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.eyro.cubeacon.CBApp;
import com.eyro.cubeacon.CBConstant.RefreshCallback;

public class SplashActivity extends Activity {

    private TextView	txtLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtLoading = (TextView) findViewById(R.id.txtProgressBarSplash);

        txtLoading.setText("Initializing Cubeacon");
        CBApp.refreshBeaconInBackground(this, new RefreshCallback() {
            
            @Override
            public void onRefreshCompleted(Exception e) {
                if (e == null) {
                    Intent intent = new Intent(SplashActivity.this,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Log.e("SplashActivity", e.getMessage());
                }
            }
        });
    }
}
