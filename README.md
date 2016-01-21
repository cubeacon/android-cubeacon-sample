# Cubeacon Sample Android Project #

This repository is reserved for Android Cubeacon SDK Demos app.

## Cubeacon SDK Overview ##
Cubeacon SDK for Android is a library to allow interaction with any beacons. The SDK system requirements are Android 4.3 or above and Bluetooth Low Energy. 

**Cubeacon SDK allows for :**

  - Integrating with Cubeacon BaaS
  - Scanning any beacons on a foreground UI or on background as a service
  - Showing alert (foreground) or notifications (background) when any beacons entered region (onEnter), exited region (onExit), nearest detected (onChange), onImmediate beacon, onNear beacon and onFar detected beacon.
  - Showing campaign scenario based on beacons event
  - Sending analytic to Cubeacon BaaS

**Cubeacon Link :**
 - [Cubeacon Backend as a Service][CubeaconBaaS]
 - [Android JavaDoc Documentation][JavaDoc]
 - [Provide us Comments][Issue]

## Cubeacon SDK Installation ##
1. Register to [Cubeacon BaaS][CubeaconBaaS] and download `CubeaconSDK-Android-xxx.zip`.
2. Extract `CubeaconSDK-Android-xxx.zip`, copy `Cubeacon.properties` to the root of your project's Java `assets` folder.
3. Then copy `CubeaconSDK-xxx.aar` to your `libs` directory.
4. On your `build.gradle` within `app` module, add this lines below before `dependencies` tag
    ```
    repositories{
        flatDir {
            dirs 'libs'
        }
    }
    ```
    Then add `compile 'com.eyro.cubeacon:CubeaconSDK:1.5.0@aar'` into your `dependecies`.
5. Create custom application class and add the following code to the `onCreate()` method to initialize the Cubeacon SDK.
    ```java
    @Override
    public void onCreate() {
        super.onCreate();

        // Cubeacon SDK Initialization
        CBApp.initialize(this, "Cubeacon.properties");
    }
    ```
    Don't forget to initialize your custom application class to `AndroidManifest.xml`

    (Optional) You can add some custom setup :
    * Enable Cubeacon SDK debug logging when on development mode by calling `CBApp.enableDebugLogging();` before initialize SDK.

## Usage and Demos ##
You can import `Cubeacon Demos` that located in this repo to your Android Studio. After initialization, you must download all beacon data from Cloud first.

Quick start to download cloud data :
```java
CBApp.getInstance().getDataInBackground(new RequestCallback() {
    @Override
    public void onCompleted(Exception e) {
        if (e != null) {
            // show error message
            return;
        }
        // do something after download complete like showing main screen
    }
});
```
(Info) `CBApp.getInstance().getDataInBackground` are implemented using background `AsyncTask` method.

### Main Activity ###
#### 1. Using SDK Abstract Activity ####
Then, extends your activity that used for detected beacon to `CBAppCompatActivity` class and implement all abstract method :
```java
    public class MainActivity extends CBAppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }

        @Override
        public void onEnterBeacon(CBBeacon beacon) {
            // do something when beacon entered region
        }

        @Override
        public void onExitBeacon(CBBeacon beacon, long timeInterval) {
            // do something when beacon exited region
        }

        @Override
        public void onNearestBeaconChanged(CBBeacon old, CBBeacon current) {
            CBCampaignType campaign = current.getStoryline().getCampaign();

            // do something when nearest beacon changed
            if (campaign == CBCampaignType.IMAGE) {
                // display a brochure image
            } else if(campaign == CBCampaignType.HTML) {
                // show html page via webview
            } else if(campaign == CBCampaignType.URL) {
                // open url in a webview/browser
            } else if(campaign == CBCampaignType.VIDEO) {
                // play a video streaming
            }
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
```
#### 2. Using Custom Activity ####
If you want to extend to your own abstract or parent activity, you can manually manage Cubeacon SDK Service into your activity.
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    CBApp.getInstance().setBeaconListener(this, new BeaconListener() {
        @Override
        public void onImmediateBeacon(CBBeacon cbBeacon) {
            // do something when beacon in immediate proximity
        }

        @Override
        public void onNearBeacon(CBBeacon cbBeacon) {
            // do something when beacon in near proximity
        }

        @Override
        public void onFarBeacon(CBBeacon cbBeacon) {
            // do something when beacon in far proximity
        }

        @Override
        public void onEnterBeacon(CBBeacon cbBeacon) {
            // do something when beacon entering region
        }

        @Override
        public void onExitBeacon(CBBeacon cbBeacon, long interval) {
            // do something when beacon exiting region
        }

        @Override
        public void onNearestBeaconChanged(CBBeacon cbBeacon, CBBeacon cbBeacon1) {
            // do something when nearest beacon changed
        }

        @Override
        public void onEmptyBeacon() {
            // do something when there is no beacon detected
        }
    });
    // create Cubeacon service after initializing beacon's listener
    CBApp.getInstance().createService();
}

protected void onResume() {
    super.onResume();

    // Check all requirement service and permission.
    if (SystemRequirementManager.checkAllRequirementUsingDefaultDialog(this)) {
        // start Cubeacon service after all requirements complied
        CBApp.getInstance().startService();
    }
}

@Override
protected void onStart() {
    super.onStart();
    // Check if device supports Bluetooth Low Energy.
    if (!SystemRequirementChecker.isBluetoothLeAvailable(this)) {
        // do something if there is no BLE on smartphone
        // like showing alert dialog or finish the activity
    }
}
```

### Improve Battery Life ###
By setting scan beacon period, we can improve battery life without losing scanning result. Especially on production application. This is how you can do:

###### For Production ######
```java
// best on battery life with some delayed scanning result
CBApp.getInstance().setScanPeriod(ScanningMode.LOW);

// good on battery life and better scanning result
CBApp.getInstance().setScanPeriod(ScanningMode.NORMAL);
```

###### For Development ######
```java
// draining battery life and fast scanning result
CBApp.getInstance().setScanPeriod(ScanningMode.HIGH);

// draining a lot of battery life, but provide best scanning result
CBApp.getInstance().setScanPeriod(ScanningMode.VERY_HIGH);
```

### Meta Users ###
By improving analytics usage and user engagement, Cubeacon SDK enhanced with `Meta User` module. This module is optional. So if you want to get user information like `fullname` and `email`, show a form with 2 textinput and you can save into cloud like this :
```java
    CBUser.currentUser()
         .setName("Your fullname here...")
         .setEmail("Your email here...")
         .sendDataInBackground(new RequestCallback() {
             @Override
             public void onCompleted(Exception e) {
                 if (e != null) {
                     // show error message
                     return;
                 }
             // show success message
          }
    });
```

## Changelog ##
* 1.5.0 (January 23, 2016)
  - New enhancement scanning method for a faster detecting beacons.
  - New AAR package, comply with Android Studio.
  - New feature, added scan period mode for better battery life.
  - New System Requirements Checker, comply with Android M or higher.
  - Comply with AppCompat library v7 for activity bundled with SDK.
  - Add possibility to customize alert and notification.
  - Fix some bugs and improve stability.
* 1.3.1 (April 18, 2015)
  - Fix optional parameter when downloading data from cloud
  - Add control to URL WebView Activity
* 1.3.0 (February 28, 2015)
  - New storyline with custom campaign like showing Image, Video, Url web page and HTML formatted content.
  - New analytic data based new storyline
  - Comply with current Cubeacon SaaS v1.3.0
  - Support Android 5.0 Lollipop
* 1.2.0 (January 17, 2015)
  - Add meta user for analytics
  - Add storyline for beacon scenario
  - Comply with current Cubeacon SaaS v1.2.0
* 1.0.0 (November 10, 2014)
  - Add new base CBActivity class
  - Improve stability
  - Comply with current Cubeacon SaaS v1.0.0
  - Combine all required library into single file
  - Fix automatic background and foreground scanning
* 0.5.0 (August 25, 2014)
  - Initial release

[CubeaconBaaS]:https://developer.cubeacon.com
[JavaDoc]:http://docs.cubeacon.com/sdk/android/references/index.html
[Issue]:https://github.com/cubeacon/android-cubeacon-sample/issues