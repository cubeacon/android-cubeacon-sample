package com.eyro.cubeacon.demos;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.eyro.cubeacon.CBBeacon;
import com.eyro.cubeacon.CBRangingListener;
import com.eyro.cubeacon.CBRegion;
import com.eyro.cubeacon.CBServiceListener;
import com.eyro.cubeacon.Cubeacon;
import com.eyro.cubeacon.SystemRequirementManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RangingActivity extends AppCompatActivity implements CBRangingListener, CBServiceListener, AdapterView.OnItemClickListener {

  private static final String TAG = RangingActivity.class.getSimpleName();
  private Cubeacon cubeacon;
  private ListView listView;
  private SimpleAdapter adapter;
  private List<Map<String, String>> data;
  private List<CBBeacon> beacons;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ranging);

    // assign view
    listView = findViewById(R.id.listview);

    // set default adapter
    String[] from = new String[]{"title", "subtitle"};
    int[] to = new int[]{android.R.id.text1, android.R.id.text2};
    data = new ArrayList<>();
    adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, from, to);

    // set adapter to listview
    listView.setAdapter(adapter);

    // set listview on item click listener
    listView.setOnItemClickListener(this);

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
  public void didRangeBeaconsInRegion(final List<CBBeacon> beacons, CBRegion region) {
    this.beacons = beacons;

    String title, subtitle;
    Map<String, String> map;

    // clear data
    data.clear();
    for (CBBeacon beacon : beacons) {
      title = beacon.getProximityUUID().toString().toUpperCase();
      subtitle = String.format(Locale.getDefault(), "M:%d m:%d RSSI:%d Accuracy:%.2fm",
          beacon.getMinor(), beacon.getMinor(), beacon.getRssi(), beacon.getAccuracy());
      map = new HashMap<>();
      map.put("title", title);
      map.put("subtitle", subtitle);
      data.add(map);
    }

    // update view using runnable
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        adapter.notifyDataSetChanged();
        if (getSupportActionBar() != null) {
          getSupportActionBar().setSubtitle("Ranged beacon : " + beacons.size());
        }
      }
    });
  }

  @Override
  public void onBeaconServiceConnect() {
    // add ranging listener implementation
    cubeacon.addRangingListener(this);
    try {
      // create a new region for ranging beacons
      CBRegion region = new CBRegion("com.eyro.cubeacon.ranging_region");
      // start ranging beacons using region
      cubeacon.startRangingBeaconsInRegion(region);
    } catch (RemoteException e) {
      Log.e(TAG, "Error while start ranging beacon, " + e);
    }
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    CBBeacon beacon = this.beacons.get(i);
    Intent intent = new Intent(this, MonitoringActivity.class);
    intent.putExtra(MonitoringActivity.INTENT_BEACON, beacon);
    startActivity(intent);
  }
}
