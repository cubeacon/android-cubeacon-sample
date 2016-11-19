# Cubeacon SDK for Android #
### Overview ###
Cubeacon SDK for Android is a library to allow interaction with any beacons. The SDK system requirements are Android 4.3 or above and Bluetooth Low Energy.

Cubeacon SDK allows for:
  - Beacon ranging (scans beacons and optionally filters them by their properties).
  - Beacon monitoring (monitors regions for those devices that have entered/exited a region).

Learn more at:
 - [Android API Reference][JavaDoc] Documentation
 - Sample application [demos][Demos] of SDK usage
 - Read our [Wiki][Wiki] for a basic SDK usage
 - Download [Cubeacon App][PlayStore] in Google Play Store to see some implementation of Cubeacon SDK

### Installation ###
1. Download `cubeacon-x.y.z.aar` from Github [release][Github Release] page.
2. Then copy it to your `libs` directory.
3. On your `build.gradle` within `app` module, add this lines below before `dependencies` tag

    ```
    repositories{
        flatDir {
            dirs 'libs'
        }
    }
    ```

    Then add `compile 'com.eyro.cubeacon:cubeacon:x.y.z@aar'` into your `dependecies`.
4. Create custom application class and add the following code to the `onCreate()` method to initialize the Cubeacon SDK.
    ```java
    Cubeacon.initialize(this);
    ```
    Don't forget to add your custom application class into `AndroidManifest.xml`

    (Optional) You can add some custom setup :
    * Enable Cubeacon SDK debug logging when in development mode by calling `Logger.setLogLevel(LogLevel)` before initialize SDK.
    * You can choose `LogLevel` from : `NONE`, `ASSERT`, `DEBUG`, `ERROR`, `INFO`, `WARN`, or `VERBOSE`.

### Basic Usage ###
1. [Ranging][Ranging] for beacons
2. [Monitoring][Monitoring] beacon
3. [Bootstrap Region][Bootstrap Region] Monitoring
4. Show [notification][Notification]

### Changelog ###
Read about detailed changelog [here][Changelog]

[Demos]:https://github.com/cubeacon/android-cubeacon-sample
[Github Release]:https://github.com/cubeacon/android-cubeacon-sample/releases
[JavaDoc]:http://docs.cubeacon.com/sdk/android/references/index.html
[Issue]:https://github.com/cubeacon/android-cubeacon-sample/issues
[PlayStore]:https://play.google.com/store/apps/details?id=com.cubeacon.tools
[Wiki]:https://github.com/cubeacon/android-cubeacon-sample/wiki
[Ranging]:https://github.com/cubeacon/android-cubeacon-sample/wiki/Ranging-Beacons
[Monitoring]:https://github.com/cubeacon/android-cubeacon-sample/wiki/Monitoring-Beacon
[Bootstrap Region]:https://github.com/cubeacon/android-cubeacon-sample/wiki/Bootstrap-Monitoring
[Notification]:https://github.com/cubeacon/android-cubeacon-sample/wiki/Show-Notification
[Changelog]:https://github.com/cubeacon/android-cubeacon-sample/wiki/Changelog