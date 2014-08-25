package com.eyro.cubeacon.demos;

import android.app.Application;

import com.eyro.cubeacon.CBApp;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Cubeacon App Initialization
        CBApp.setup(this, "Cubeacon.properties");
        
        // Configure verbose debug logging.
        CBApp.enableDebugLogging(true);
    }
}
