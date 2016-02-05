package com.eyro.cubeacon.demos;

import android.os.Bundle;
import android.widget.Toast;

import com.eyro.cubeacon.CBUser;
import com.eyro.cubeacon.activity.CBAppCompatActivity;
import com.eyro.cubeacon.callback.RequestCallback;
import com.eyro.cubeacon.constant.CBCampaignType;
import com.eyro.cubeacon.model.CBBeacon;

public class MainActivity extends CBAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Uncomment this to send name and email for meta user use
         *
         * CBUser.currentUser()
         *      .setName("Your fullname here...")
         *      .setEmail("Your email here...")
         *      .sendDataInBackground(new RequestCallback() {
         *          @Override
         *          public void onCompleted(Exception e) {
         *              if (e != null) {
         *                  Toast.makeText(MainActivity.this, "Error sending CBUser, cause : " + e.getMessage(), Toast.LENGTH_LONG).show();
         *                  return;
         *              }
         *          Toast.makeText(MainActivity.this, "CBUser send successfully...", Toast.LENGTH_LONG).show();
         *       }
         * });
        */
    }

    @Override
    public void onEnterBeacon(CBBeacon beacon) {
        // do something when beacon entered region
        CBCampaignType campaign = beacon.getStoryline().getCampaign();

        if(campaign.equals(CBCampaignType.IMAGE)){
            // display a brochure image
        } else if(campaign.equals(CBCampaignType.HTML)){
            // show html page via webview
        } else if(campaign.equals(CBCampaignType.URL)){
            // open url in a webview/browser
        } else if(campaign.equals(CBCampaignType.VIDEO)){
            // play a video streaming
        }
    }

    @Override
    public void onExitBeacon(CBBeacon beacon, long timeInterval) {
        // do something when beacon exited region
    }

    @Override
    public void onNearestBeaconChanged(CBBeacon old, CBBeacon current) {
        // do something when nearest beacon changed
    }

    @Override
    public void onEmptyBeacon() {
        // do something when no beacon detected
    }

    @Override
    public void onImmediateBeacon(CBBeacon beacon) {
        // do something when beacon proximity is immediate
    }

    @Override
    public void onNearBeacon(CBBeacon beacon) {
        // do something when beacon proximity is near
    }

    @Override
    public void onFarBeacon(CBBeacon beacon) {
        // do something when beacon proximity is far
    }
}
