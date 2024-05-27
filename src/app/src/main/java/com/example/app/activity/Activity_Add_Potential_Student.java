package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.PotentialStudentDAO;
import com.example.app.model.PotentialStudentDTO;

import java.time.LocalDate;

public class Activity_Add_Potential_Student extends AppCompatActivity {
    EditText studentID, studentName, phoneNumber, gender, address, level, appointmentNumber;
    Button doneBtn, exitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_potential_student);

        studentID = findViewById(R.id.idStudent);
        studentName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        gender = findViewById(R.id.gender);
        level = findViewById(R.id.level);
        address = findViewById(R.id.address);
        appointmentNumber = findViewById(R.id.appointmentNumber);

        String message = getIntent().getStringExtra("studentID");
        if (!message.equals("")) {
            studentID.setText("1");
            /*studentName.setText();
            address.setText();
            phoneNumber.setText();
            gender.setText();
            level.setText();
            appointmentNumber.setText();*/
        }

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
                boolean acceptSwitch = true;    //Đúng thì mới trả về Fragmen_Setting

                if (studentID.getText().equals("") || address.getText().equals("")
                        || level.getText().equals("") || appointmentNumber.getText().equals("")
                        || studentName.getText().equals("") || gender.getText().equals("") || phoneNumber.getText().equals("")) {
                    acceptSwitch = false;
                }

                if (acceptSwitch) {
                    try {
                        int rowEffect = PotentialStudentDAO.getInstance(Activity_Add_Potential_Student.this).InsertPotentialStudent(
                                Activity_Add_Potential_Student.this, new PotentialStudentDTO(
                                        studentID.getText().toString(),
                                        studentName.getText().toString(),
                                        phoneNumber.getText().toString(),
                                        gender.getText().toString(),
                                        address.getText().toString(),
                                        level.getText().toString(),
                                        appointmentNumber.getText().toString())
                        );

                        if (rowEffect > 0) {
                            Toast.makeText(Activity_Add_Potential_Student.this,
                                    "Thêm học viên tiềm năng mới thành công", Toast.LENGTH_SHORT).show();
                            Log.d("Add potential student:", "success");
                        }else {
                            Log.d("Add potential student:", "fail");
                        }
                        studentID.setText(null);
                        studentName.setText(null);
                        address.setText(null);
                        phoneNumber.setText(null);
                        gender.setText(null);
                        level.setText(null);
                        appointmentNumber.setText(null);
                        finish();
                    } catch (Exception e) {
                        Log.d("Add potential student error:", "fail");
                    }
                }
                else Toast.makeText(Activity_Add_Potential_Student.this, "Nhập lại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}