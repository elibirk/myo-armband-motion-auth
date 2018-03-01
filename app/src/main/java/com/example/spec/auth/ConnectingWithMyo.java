package com.example.spec.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ConnectingWithMyo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecting_with_myo);
    }//end onCreate

    public void cancelMyo(View v){
        //stop the service since we're giving up
        Intent intent = new Intent(this, BackgroundService.class);
        stopService(intent);

        //essentially go back to the main activity
        intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }//end cancelMyo

}//end ConnectingWithMyo
