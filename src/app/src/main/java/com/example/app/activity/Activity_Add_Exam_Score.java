package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;

public class Activity_Add_Exam_Score extends AppCompatActivity {
    EditText speak, write, listen, read;
    Button exitBtn, doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam_score);

        speak = findViewById(R.id.speaking_score);
        write = findViewById(R.id.writing_score);
        listen = findViewById(R.id.listening_score);
        read = findViewById(R.id.reading_score);
        String message = getIntent().getStringExtra("studentID");

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
                if (speak.getText().toString().equals("") || write.getText().toString().equals("")
                        || listen.getText().toString().equals("") || read.getText().toString().equals("")) {
                    Toast.makeText(Activity_Add_Exam_Score.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });
    }
}