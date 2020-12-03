#### ITT Client
This is the Android client for the [ITT-Problem](https://github.com/Nyariki/ITT-PROBLEM/). It is written in Kotlin and built using a modified MVVM architecture.

- Open the project in Android studio.
- In the [constants](app/src/main/res/values/constants.xml) file, update the ```app_url``` to the address of the running Spring boot backend app.  
- If you are on the same network, the address can be obtained by running ```ipconfig```(windows) or ```ifconfig```(unix) on the console. Copy and use the IPV4 address, typically in the format ```192.168.xxx.xxx```  
- Run the app on Android Studio

Alternatively, the android app can be built on the command-line and the generated ```.apk``` file copied and run on a device or emulator. 
- Navigate to the [app](app) folder.
- Open the command-line and run ```gradlew assembleDebug```. The apk file will be generated.
- To build the APK and immediately install it on a running emulator or connected device, instead invoke the ```gradlew installDebug``` command.
