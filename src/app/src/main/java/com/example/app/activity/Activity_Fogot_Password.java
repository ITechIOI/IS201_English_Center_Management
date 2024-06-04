package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.R;

public class Activity_Fogot_Password extends AppCompatActivity {
    EditText username, phoneNumber;
    Button findPass;
    TextView turnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogot_password);

        username = findViewById(R.id.input_username);
        phoneNumber = findViewById(R.id.input_phone);
        findPass = findViewById(R.id.find_pass);
        turnBack = findViewById(R.id.turnBack);

        turnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }
}