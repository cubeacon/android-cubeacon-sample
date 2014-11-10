package com.eyro.cubeacon.demos;

import android.app.Application;
import android.util.Log;

import com.eyro.cubeacon.CBApp;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("Application", "Init Cubeacon APP");

        // Cubeacon App Initialization
        CBApp.setup(this, "Cubeacon.properties");

        // Configure verbose debug logging.
        CBApp.enableDebugLogging(true);
        
        // Configure download image brochure.
        CBApp.enableDownloadImage(true);
    }
}
