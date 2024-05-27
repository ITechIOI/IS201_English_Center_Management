package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;

public class Activity_Add_Schedule extends AppCompatActivity {
    EditText dayOfWeek, startTime, endTime, idClass, idClassroom;
    Button doneBtn, exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        String message = getIntent().getStringExtra("idSchedule");

        dayOfWeek = findViewById(R.id.dayOfWeek);
        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        idClass = findViewById(R.id.idClass);
        idClassroom = findViewById(R.id.idClassroom);

        doneBtn = findViewById(R.id.done_btn);
        exitBtn = findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayOfWeek.getText().equals("") || startTime.getText().equals("")
                        || endTime.getText().equals("") || idClass.getText().equals("")
                        || idClassroom.getText().equals("")) {
                    Toast.makeText(Activity_Add_Schedule.this, "Nhập lại", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });
    }

}