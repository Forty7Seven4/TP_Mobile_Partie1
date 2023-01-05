package com.example.evolvedactivity;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageButton launchPhoneCallButton;
    private ImageButton openWebPageButton;
    private ImageButton openPersoActivityButton;
    private EditText phoneNumberEditText;
    private EditText urlEditText;
    private EditText challengerNumber1EditText;
    private EditText challengerNumber2EditText;
    private int CALL_Perm = 1;
    private final String DEFAULT_URL = "https://www.google.com/";
    private boolean isUserLoggedIn=false;
    private boolean isChallengePassed=false;
    private static final int LOGIN_REQUEST_CODE = 1;
    private static final int CHECK_REQUEST_CODE = 2;

    private String phoneNumberToCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, CALL_Perm);

        launchPhoneCallButton = (ImageButton) findViewById(R.id.LaunchPhoneCallButton);
        phoneNumberEditText = (EditText) findViewById(R.id.editTextPhone);

        openWebPageButton = (ImageButton) findViewById(R.id.openWebPageButton);
        urlEditText = (EditText) findViewById(R.id.urlEditText);

        openPersoActivityButton = (ImageButton) findViewById(R.id.openPersoActivityButton);

        challengerNumber1EditText = (EditText) findViewById(R.id.challengeNumber1EditText);
        challengerNumber2EditText = (EditText) findViewById(R.id.challengeNumber2EditText);


        launchPhoneCallButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isUserLoggedIn()){
                            openExplicitLoginActivity();
                        } else {
                            launchPhoneCall();
                        }
                    }
                }
        );

        openWebPageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                          if(challengerNumber1EditText.getText().length()==0||challengerNumber2EditText.getText().length()==0)

                              Toast.makeText(MainActivity.this, "provied numbers pls", Toast.LENGTH_SHORT).show();
                          else
                              openCheckActivity();


                    }
                }
        );

        openPersoActivityButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openImplicitLoginActivity();
                    }
                }
        );

        if(getIntent().getBooleanExtra("Result",false)){
            openWebPage();
        }
    }

    private void openWebPage(){
        String url = urlEditText.getText().toString();
        if (url.equals("")){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(DEFAULT_URL)));
        } else {
            String completeUrl = "https://"+url;
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(completeUrl)));
        }
    }

    private void openCheckActivity(){
        Intent Intent = new Intent(getApplicationContext(), CheckActivity.class);
        int challengeNumber1 = Integer.parseInt(challengerNumber1EditText.getText().toString());
        int challengeNumber2 = Integer.parseInt(challengerNumber2EditText.getText().toString());

            Intent.putExtra("challengeNumber1", challengeNumber1);
            Intent.putExtra("challengeNumber2", challengeNumber2);
            startActivity(Intent);

    }

    private void openPersoActivity(){
        Intent persoActivityIntent = new Intent(getApplicationContext(), PersoActivity.class);
        startActivity(persoActivityIntent);
    }

    private void openImplicitLoginActivity() {
        Intent loginActivityIntent = new Intent("login.ACTION");
        startActivity(loginActivityIntent);
    }

    private void openExplicitLoginActivity() {
        Intent loginActivityIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(loginActivityIntent, LOGIN_REQUEST_CODE);
    }

    private void launchPhoneCall() {
        String phoneNumber = phoneNumberEditText.getText().toString();
        Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
        phoneCallIntent.setData(Uri.parse("tel:"+phoneNumber));
        startActivity(phoneCallIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check the permission type using the requestCode
        if (requestCode == CALL_Perm) {
            //the array is empty if not granted
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "GRANTED CALL", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            boolean isUserLoggedIn = data.getExtras().getBoolean("isLoggedIn");
            setUserLoggedIn(isUserLoggedIn);
        }

        if (requestCode == CHECK_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            boolean isChallengePassed = data.getExtras().getBoolean("isChallengePassed");
            setChallengePassed(isChallengePassed);
        }

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("pendingPhoneCall", phoneNumberEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String pendingPhoneCall = savedInstanceState.getString("pendingPhoneCall");
       phoneNumberEditText.setText(pendingPhoneCall);
    }


    public boolean isUserLoggedIn() {
        return isUserLoggedIn;
    }

    public void setUserLoggedIn(boolean userLoggedIn) {
        isUserLoggedIn = userLoggedIn;
    }

    public boolean isChallengePassed() {
        return isChallengePassed;
    }

    public void setChallengePassed(boolean challengePassed) {
        isChallengePassed = challengePassed;
    }

    public String getPhoneNumberToCall() {
        return phoneNumberToCall;
    }

    public void setPhoneNumberToCall(String phoneNumberToCall) {
        this.phoneNumberToCall = phoneNumberToCall;
    }
}