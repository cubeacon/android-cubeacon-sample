# Cubeacon Sample Android Project #

This repository is reserved for Android Cubeacon SDK Demos app.

## Cubeacon SDK Overview ##
Cubeacon SDK for Android is a library to allow interaction with any beacons. The SDK system requirements are Android 4.3 or above and Bluetooth Low Energy. 

**Cubeacon SDK allows for :**

  - Integrating with Cubeacon SaaS
  - Scanning any beacons on a foreground UI or on background as a service
  - Showing alert (foreground) or notifications (background) when any beacons entered region (onEnter), exited region (onExit), nearest detected (onChange), onImmediate beacon, onNear beacon and onFar detected beacon.
  - Showing scenario based on beacons detected
  - Sending analytic to Cubeacon SaaS

**Cubeacon Link :**
 - [Cubeacon Software as a Service][CubeaconSaaS]
 - [Android JavaDoc Documentation][JavaDoc]
 - [Provide us Comments][Issue]

## Cubeacon SDK Installation ##
1. Register to [Cubeacon SaaS][CubeaconSaaS] and download `CubeaconSDK-Android-xxx.zip`.
2. Extract `CubeaconSDK-Android-xxx.zip`, copy `Cubeacon.properties` to the root of your project's Java `src` source folder.
3. Then copy `CuBeacon-xxx.jar` to your `libs` directory.
4. Add following permissions, activity and service declaration to your `AndroidManifest.xml`:

    ```xml
    <!-- Needed permissions in order to generate meta users. -->
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    
    <!-- Needed permissions in order to connect to internet. -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

    <!-- Needed permissions in order to scan for beacons. -->
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />

    <!-- Declaration that this app is usable on phones with Bluetooth Low Energy. -->
    <uses-feature
        android:name="android.hardware.bluetooth_le"
        android:required="true" />
    ```
    In application tag, add this lines of code :
    
    ```xml
    <!-- Storyline activity responsible to show campaign alert/ notification -->
    <activity 
        android:name="com.eyro.cubeacon.StorylineActivity"
        android:configChanges="orientation|screenSize">
    </activity>
        
    <!-- Cubeacon service responsible for scanning beacons. -->
    <service 
        android:name="com.eyro.cubeacon.CBService" 
        android:exported="false" />
    <!-- Service that responsible to send Analytic data -->
    <service 
        android:name="com.kii.cloud.analytics.EventUploadService" />
    ```
7. Create custom application class and add the following code to the `onCreate()` method to initialize the Cubeacon SDK.
    ```java
    @Override
    public void onCreate() {
        super.onCreate();
        // Cubeacon SDK Initialization
        CBApp.setup(this, "Cubeacon.properties");
    }
    ```
    Don't forget to initialize your custom application class to `AndroidManifest.xml`

    (Optional) You can add some custom setup :
    * Enable Cubeacon SDK debug logging when on development mode by calling `CBApp.enableDebugLogging(true)`.
    * Enable download image brohure on local path by calling `CBapp.enableDownloadImage(true)`.

## Usage and Demos ##
You can import `Cubeacon SDK Demos` that located in this repo to your ADT. After initialization, you must download all beacon data from Cloud first.

Quick start to download cloud data :
```java
CBApp.refreshBeaconInBackground(new CBConstant.RefreshBeacon() {
    @Override
    public void onRefreshCompleted(boolean result, String messages) {
        // do something after download complete
        // like showing main screen of your app
    }
});
```
(Info) `CBApp.refreshBeaconInBackground` are implemented using background `AsyncTask` method.

Then, extends your activity that used for detected beacon to `CBActivity` class and implement all abstract method :
```java
    public class MainActivity extends CBActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }
    
        @Override
        protected void onBeaconExited(CBBeacon beacon, long timeInterval) {
            // do something when beacon exited region
        }
    
        @Override
        protected void onBeaconEntered(CBBeacon beacon) {
            // do something when beacon entered region
        }
    
        @Override
        protected void onNearestBeaconChanged(CBBeacon old, CBBeacon current) {
            // do something when nearest beacon changed
            if(current.getStoryline().equals(Storyline.IMAGE)){
                // display a brochure image
            }else if(current.getStoryline().equals(Storyline.TEXT)){
                // show text alert/notification
            }else if(current.getStoryline().equals(Storyline.HTML)){
                // show html page via webview
            }else if(current.getStoryline().equals(Storyline.URL)){
                // open url in a webview/browser
            }else if(current.getStoryline().equals(Storyline.VIDEO)){
                // play a video streaming
            }
        }
        
        @Override
        protected void onBeaconEmpty() {
            // do something when no beacon detected
        }
        
        @Override
        protected void onImmediateBeacon(CBBeacon beacon) {
            // do something when beacon proximity are immediate
        }
        
        @Override
        protected void onNearBeacon(CBBeacon beacon) {
            // do something when beacon proximity are near
        }
        
        @Override
        protected void onFarBeacon(CBBeacon beacon) {
            // do something when beacon proximity are far
        }
    }
```

### Meta Users ###
By improving analytics usage and user engagement, Cubeacon SDK enhanced with `Meta User` module. This module is optional. So if you want to get user informations like `fullname` and `email`, show a form with 2 textinput and you can save into cloud like this :
```java
    CBUser.setUserData("User fullname", "User valid email");
    CBUser.currentUser().sendDataInBackground(new Callback() {
        @Override
        public void onCompleted(Exception exp) {
            if (exp == null)
                // show succesfull message
            else
                // show error message by exp.getMessage()
        }
    });
```

## Changelog ##
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

[CubeaconSaaS]:http://developer.cubeacon.com
[JavaDoc]:http://docs.cubeacon.com/sdk/android/references/index.html
[Issue]:https://github.com/cubeacon/android-cubeacon-sample/issues