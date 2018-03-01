# myo-armband-android-motion-authentication
A simple Android application to test motion authentication with the Myo armband.

This application is a simple test to show that motions from the Myo armband could theoretically be used for authentication purposes. A user may enter the username and password combination (currently hard-coded since this is only an example) or connect with a Myo armband to perform a gesture pattern (again, hardcoded). Some unnecessary code is included to show how this application would expand if used more seriously.

This is in no way meant to be a secure or complete application.

To run this application, the Myo Android SDK is required. This was programmed with version 0.10.0 using Android Studio. Line 32 of the build.gradle file (myo-armband-android-motion-authentication/app/build.gradle) needs to be modified with the path to your downloaded sdk (as the developers created the sdk with Eclipse as the priority).
