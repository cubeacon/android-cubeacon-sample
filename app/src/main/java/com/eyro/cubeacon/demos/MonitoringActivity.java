package com.eyro.cubeacon.demos;

import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.eyro.cubeacon.CBBeacon;
import com.eyro.cubeacon.CBMonitoringListener;
import com.eyro.cubeacon.CBRegion;
import com.eyro.cubeacon.CBServiceListener;
import com.eyro.cubeacon.Cubeacon;
import com.eyro.cubeacon.MonitoringState;
import com.eyro.cubeacon.SystemRequirementManager;

import java.util.Locale;

public class MonitoringActivity extends AppCompatActivity implements CBServiceListener, CBMonitoringListener {

    private static final String TAG = MonitoringActivity.class.getSimpleName();
    public static final String INTENT_BEACON = "IntentBeacon";

    private TextView textState;

    private Cubeacon cubeacon;
    private CBBeacon beacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        // get beacon parcelable from intent activity
        beacon = getIntent().getParcelableExtra(INTENT_BEACON);

        // assign view
        textState = (TextView) findViewById(R.id.state);
        TextView textRegion = (TextView) findViewById(R.id.region);

        // set default value of region text
        String region = String.format(Locale.getDefault(), "\nUUID: %s\nMajor: %d - Minor : %d",
                beacon.getProximityUUID().toString().toUpperCase(), beacon.getMajor(), beacon.getMinor());
        textRegion.setText(getString(R.string.label_region, region));

        // assign local instance of Cubeacon manager
        cubeacon = Cubeacon.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // check all requirement like is BLE available, is bluetooth on/off,
        // location service for Android API 23 or later
        if (SystemRequirementManager.checkAllRequirementUsingDefaultDialog(this)) {
            // connecting to Cubeacon service when all requirements completed
            cubeacon.connect(this);
            // disable background mode, because we're going to use full
            // scanning resource in foreground mode
            cubeacon.setBackgroundMode(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // enable background mode when this activity paused
        cubeacon.setBackgroundMode(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // disconnect from Cubeacon service when this activity destroyed
        cubeacon.disconnect(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        // add monitoring listener implementation
        cubeacon.addMonitoringListener(this);
        try {
            // create a new region for monitoring beacon
            CBRegion region = new CBRegion("com.eyro.cubeacon.monitoring_region",
                    beacon.getProximityUUID(), beacon.getMajor(), beacon.getMinor());
            // start monitoring beacon using region
            cubeacon.startMonitoringForRegion(region);
        } catch (RemoteException e) {
            Log.e(TAG, "Error while start monitoring beacon, " + e);
        }
    }

    @Override
    public void didEnterRegion(CBRegion cbRegion) {
        // add code when entering region beacon
    }

    @Override
    public void didExitRegion(CBRegion cbRegion) {
        // add code when exiting region beacon
    }

    @Override
    public void didDetermineStateForRegion(final MonitoringState state, CBRegion cbRegion) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (state) {
                    case INSIDE:
                        textState.setText(getString(R.string.label_state, "ENTER REGION"));
                        break;
                    case OUTSIDE:
                        textState.setText(getString(R.string.label_state, "EXIT REGION"));
                        break;
                }
            }
        });
    }
}
