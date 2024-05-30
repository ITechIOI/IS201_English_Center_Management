package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;

import java.util.Calendar;

public class Activity_Add_Staff extends AppCompatActivity {
    EditText idStaff, fullName, address, phoneNumber, type;
    TextView birthday;
    Button exitBtn, doneBtn;
    String[] genderItem = {"Nam", "Nữ"};
    AutoCompleteTextView gender;
    ArrayAdapter<String> genderAdapter;
    DatePickerDialog.OnDateSetListener birthDt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        
        String message1 = getIntent().getStringExtra("idStaff");
        String message2 = getIntent().getStringExtra("idTeacher");

        gender = findViewById(R.id.gender);
        genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
        gender.setAdapter(genderAdapter);
        gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        birthday = findViewById(R.id.birthday);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Activity_Add_Staff.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        birthDt,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        birthDt = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                birthday.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        idStaff = findViewById(R.id.idStaff);
        fullName = findViewById(R.id.fullName);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phoneNumber);
        type = findViewById(R.id.type);
        if (!message1.equals("")) {
            type.setText("Nhân viên ghi danh");

        }

        if (!message2.equals(""))   {
            type.setText("Giáo viên");
        }


        doneBtn = findViewById(R.id.doneBtn);
        exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idStaff.getText().equals("") || fullName.getText().equals("")
                        || address.getText().equals("") || phoneNumber.getText().equals("") || birthday.getText().equals("")) {
                    Toast.makeText(Activity_Add_Staff.this, "Nhập lại", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });
    }
}