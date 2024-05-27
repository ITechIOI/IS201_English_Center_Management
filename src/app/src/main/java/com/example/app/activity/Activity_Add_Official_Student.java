package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.app.R;

public class Activity_Add_Official_Student extends AppCompatActivity {
    EditText studentID, studentName, phoneNumber, gender, address, birthday;
    Button doneBtn, exitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_official_student);

        studentID = findViewById(R.id.idStudent);
        studentName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.address);
        birthday = findViewById(R.id.birthday);

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
                if (studentID.equals("") || address.equals("")
                        || birthday.equals("") || studentName.equals("")
                        || gender.equals("") || phoneNumber.equals("")) {
                    finish();
                }
            }
        });
    }
}