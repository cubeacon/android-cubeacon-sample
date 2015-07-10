package com.eyro.cubeacon.demos;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

public class BeaconActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString(MainActivity.EXTRA_INTENT);
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        
        ImageView image = (ImageView) findViewById(R.id.beacon_brochure);
        image.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_compass));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
