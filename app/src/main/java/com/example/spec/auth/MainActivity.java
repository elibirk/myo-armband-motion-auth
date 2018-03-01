package com.example.spec.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView myoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myoText = (TextView) findViewById(R.id.MyoText);

    }//end onCreate

    public void SubmitButton(View v){
        //get the information from the user input
        EditText uname = findViewById(R.id.username);
        EditText pword = findViewById(R.id.password);

        String username = uname.getText().toString();
        String password = pword.getText().toString();

        //login info hardcoded only because this is a demonstration
        //actual work would have salted hashes in a database
        if(username.equals("SuperUser") && password.equals("supersecretpassword123")){
            Intent intent = new Intent(getApplicationContext(), SecretPage.class);
            getApplicationContext().startActivity(intent);
        }//end if
        else {
            myoText = (TextView) findViewById(R.id.MyoText);
            myoText.setText("Sorry, wrong values. Try again.");
        }//end else
    }//end SubmitButton

    public void useMyo(View v){
        //start the service that checks the Myo gestures for the correct pattern
        startService(new Intent(this, BackgroundService.class));
    }//end useMyo

}//end MainActivity

