# Cubeacon Sample Android Project #

This repository is reserved for Android Cubeacon SDK Demos app.

## Cubeacon SDK Overview ##
Cubeacon SDK for Android is a library to allow interaction with any beacons. The SDK system requirements are Android 4.3 or above and Bluetooth Low Energy. 

**Cubeacon SDK allows for :**

  - Integrating with Cubeacon SaaS
  - Scanning any beacons on a foreground UI or on background as a service
  - Showing alert (foreground) or notifications (background) when any beacons entered region (onEnter), exited region (onExit) and nearest detected (onChange)
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
4. Add following permissions and service declaration to your `AndroidManifest.xml`:

    ```xml
    <!-- Needed permissions in order to connect to internet. -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

    <!-- Needed permissions in order to scan for beacons. -->
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />

    <!-- Declaration that this app is usable on phones with Bluetooth Low Energy. -->
    <uses-feature
        android:name="android.hardware.bluetooth_le"
        android:required="true" />
    ```
    
    ```xml
    <!-- Cubeacon service responsible for scanning beacons. -->
    <service android:name="com.eyro.cubeacon.CBService" 
        android:exported="false" />
    <!-- Service that responsible to send Analytic data -->
    <service android:name="com.kii.cloud.analytics.EventUploadService" />
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
CBApp.refreshBeaconInBackground(new CB.RefreshBeacon() {
    @Override
    public void onRefreshCompleted() {
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
        protected void onBeaconChanged(CBBeacon old, CBBeacon current) {
            // do something when nearest beacon changed
        }
    }
```

## Changelog ##
* 1.0.0 (November 10, 2014)
  - Add new base CBActivity class
  - Add kill switch based on monthly API call usage
  - Combine all required library into a jar library file
  - Fix automatic background and foreground scanning
  - Fix some bugs
* 0.5.0 (August 25, 2014)
  - Initial release

[CubeaconSaaS]:http://developer.cubeacon.com
[JavaDoc]:http://docs.cubeacon.com/sdk/android/references/index.html
[Issue]:https://github.com/cubeacon/android-cubeacon-sample/issues