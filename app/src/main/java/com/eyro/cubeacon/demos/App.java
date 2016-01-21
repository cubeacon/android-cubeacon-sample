package com.eyro.cubeacon.demos;

import android.app.Application;

import com.eyro.cubeacon.CBApp;

/**
 * Created by Eyro on 10/31/15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // settings for enable debug logging
        CBApp.enableDebugLogging();

        // initialize Cubeacon SDK
        CBApp.initialize(this, "Cubeacon.properties");
    }
}
