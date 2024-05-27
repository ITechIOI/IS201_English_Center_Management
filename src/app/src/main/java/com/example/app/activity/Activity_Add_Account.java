package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;

public class Activity_Add_Account extends AppCompatActivity {
    EditText idAccount, idUser, username, password;
    Button exitBtn, doneBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        idAccount = findViewById(R.id.idAccount);
        idUser = findViewById(R.id.idUser);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        exitBtn = findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        doneBtn = findViewById(R.id.done_btn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idAccount.getText().toString().equals("") || idUser.getText().toString().equals("")
                        || username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(Activity_Add_Account.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });


    }
}