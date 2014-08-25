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
1. Register to KiiCloud first using this [guide][KiiCloud].
2. Download `KiiCloudStorageSDK-xxx.jar` and `KiiAnalyticsSDK-xxx.jar`.
3. Register to [Cubeacon SaaS][CubeaconSaaS] and download `CubeaconSDK-Android-xxx.zip`.
4. Extract `CubeaconSDK-Android-xxx.zip`, copy `Cubeacon.properties` to the root of your project's Java `src` source folder.
5. Then copy `KiiCloudStorageSDK-xxx.jar`, `KiiAnalyticsSDK-xxx.jar` and `CuBeacon.jar` to your `libs` directory.
6. Add following permissions and service declaration to your `AndroidManifest.xml`:

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
    ```xml
    public void onCreate() {
        super.onCreate();
        // Cubeacon SDK Initialization
        CBApp.setup(this, "Cubeacon.properties");
    }
    ```
    Don't forget to initialize your custom class to `AndroidManifest.xml`

    (Optional) You can enable Cubeacon SDK debug logging by calling `CBApp.enableDebugLogging(true)`.

## Usage and Demos ##
You can import `Cubeacon SDK Demos` that located in this repo to your ADT. After initialization, you must download all beacon data from Cloud first.

Quick start to download cloud data :
```java
CBApp.refreshBeacon(new CBApp.RefreshBeacon() {
    @Override
    public void onBeforeRefresh() {
        // do something before download started
        // like showing a splashscreen or progressbar
    }
    
    @Override
    public void onAfterRefresh(String arg0) {
        // do something after download complete
        // like showing main screen of your app
    }
});
```
(Info) `CBApp.refreshBeacon` are implemented using background `AsyncTask` method.

Then, on main activity of your apps :
```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create Cubeacon SDK Service
        CBApp.createService(this);
        setContentView(R.layout.activity_main);
        // set beacon listener of Cubeacon SDK
        CBApp.setBeaconListener(this, new CBApp.BeaconListener() {
            
            @Override
            public void onExit(CBBeacon beacon, long timeInterval) {
                // do something when beacon exited region
            }
            
            @Override
            public void onEnter(CBBeacon beacon) {
                // do something when beacon entered region
            }
            
            @Override
            public void onChange(CBBeacon old, CBBeacon current) {
                // do something when nearest beacon changed
            }
        });
    }

    @Override
    protected void onStart() {
        // starting Cubeacon SDK service
        CBApp.startService(this);
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // call onActivityResult to Cubeacon SDK
        CBApp.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        // destroy Cubeacon SDK service
        CBApp.destroyService(this);
        super.onDestroy();
    }
```

## Changelog ##
* 0.5.0 (August 25, 2014)
  - Initial release

[CubeaconSaaS]:http://developer.cubeacon.com
[JavaDoc]:http://docs.cubeacon.com/sdk/android/references/index.html
[Issue]:https://github.com/cubeacon/android-cubeacon-sample/issues
[KiiCloud]:http://docs.cubeacon.com/saas/signup-kii/