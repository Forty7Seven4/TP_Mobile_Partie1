package com.example.evolvedactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckActivity extends AppCompatActivity {
    private Button cancelButton;
    private Button okButton;

    private TextView tv3;
    private TextView tv4;

    private EditText EditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        cancelButton = (Button) findViewById(R.id.cancelChallengeButton);
        okButton = (Button) findViewById(R.id.okChallengeButton);

        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);

        EditText = (EditText) findViewById(R.id.EditText);

        int nb1 = getIntent().getIntExtra("challengeNumber1",0);
        int nb2= getIntent().getIntExtra("challengeNumber2",0);
        String nb1str=String.valueOf(nb1);
        String nb2str=String.valueOf(nb2);
        tv3.setText(nb1str);
        tv4.setText(nb2str);

        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }
        );

        okButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        int nb3= Integer.parseInt(EditText.getText().toString());
                        if (nb3==nb1+nb2){
                            Intent Intent = new Intent(getApplicationContext(), MainActivity.class);
                            Intent.putExtra("Result", nb3==nb1+nb2);
                            startActivity(Intent);

                        }
                        else {
                            Toast.makeText(CheckActivity.this, "ANSWER INCORRECT", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }





}