package com.example.spec.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class SecretPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secret_page);

        //stop the service now that we have gotten to the secret page
        Intent intent = new Intent(this, BackgroundService.class);
        stopService(intent);
    }//end onCreate

}//end SecretPage
