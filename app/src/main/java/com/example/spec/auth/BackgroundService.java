package com.example.spec.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.scanner.ScanActivity;

public class BackgroundService extends Service {
    private static final String TAG = "BackgroundService";

    private Toast mToast;

    private int spreadCount;
    //counts number of times the fingers are spread, since that's the most unique gesture
    //counts below are for if we decide to swap the gesture for authentication
    private int fistCount;
    private int doubleTapCount;
    private int wInCount;
    private int wOutCount;

    Myo globalMyo;

    // Classes that inherit from AbstractDeviceListener can be used to receive events from Myo devices.
    private DeviceListener mListener = new AbstractDeviceListener() {
        @Override
        public void onConnect(Myo myo, long timestamp) {
            globalMyo = myo;
            //tell the user that we're connected
            showToast(getString(R.string.connected));
            //start up the class for Myo connection
            Intent intent = new Intent(getApplicationContext(), ConnectingWithMyo.class);
            getApplicationContext().startActivity(intent);
        }//end onConnect

        @Override
        public void onDisconnect(Myo myo, long timestamp) {
            //this happens sometimes due to bluetooth, but the service will reconnect it
            showToast(getString(R.string.disconnected));
        }//end onDisconnect

        // onPose() detects pose changes
        @Override
        public void onPose(Myo myo, long timestamp, Pose pose) {
            Log.d("Myo", "pose");
            switch (pose) {
                case UNKNOWN:
                    Log.d("pose", "Unknown");
                    break;
                case REST:
                case DOUBLE_TAP:
                    switch (myo.getArm()) {
                        case LEFT:
                            break;
                        case RIGHT:
                            break;
                    }//end arm switch
                    Log.d("pose", "DoubleTap");
                    doubleTapCount++;
                    checkAuth();
                    break;
                case FIST:
                    Log.d("pose", "Fist");
                    fistCount++;
                    checkAuth();
                    break;
                case WAVE_IN:
                    Log.d("pose", "WaveIn");
                    wInCount++;
                    checkAuth();
                    break;
                case WAVE_OUT:
                    Log.d("pose", "WaveOut");
                    wOutCount++;
                    checkAuth();
                    break;
                case FINGERS_SPREAD:
                    Log.d("pose", "FingersSpread");
                    spreadCount++;
                    checkAuth();
                    break;
            }//end switch
        }//end onPose
    };//end devicelistener

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service","???");
        //initialize the count for the authentication.
        spreadCount = 0;

        // First, we initialize the Hub singleton with an application identifier.
        Hub hub = Hub.getInstance();
        if (!hub.init(this, getPackageName())) {
            showToast("Couldn't initialize Hub");
            stopSelf();
            return;
        }//end if

        //Don't send Thalmic labs our data
        hub.setSendUsageData(false);
        // Disable standard Myo locking policy. All poses will be delivered.
        hub.setLockingPolicy(Hub.LockingPolicy.NONE);

        // Next, register the DeviceListener
        hub.addListener(mListener);

        // Finally, scan for Myo devices and connect.
        //hub.attachToAdjacentMyo(); is not used because the lgv20 never picks up the myo with this function
        Intent intent = new Intent(this, ScanActivity.class);
        this.startActivity(intent);
    }//end onCreate

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Unregister the listener to keep things clean
        Hub.getInstance().removeListener(mListener);

        //stop the hub
        Hub.getInstance().shutdown();
    }//end onDestroy

    public void checkAuth() {
        //place conditions here, this is in a function so it can easily be replaced with
        //a new gesture pattern, or something more complex (i.e. an array) once technology is available
        //in this case, we check if the person has spread their fingers enough times
        if(spreadCount%4==0){
            Log.d("count", "4 UNLOCKED!");

            //starts up the secret page
            Intent intent = new Intent(getApplicationContext(), SecretPage.class);
            getApplicationContext().startActivity(intent);
        }//end if
    }//end checkAuth

    private void showToast(String text) {
        Log.w(TAG, text);
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }//end showToast
}//end BackgroundService
