package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;

public class Activity_Add_Staff extends AppCompatActivity {
    TextView idStaff, fullName, address, phoneNumber, birthday, type;
    Button exitBtn, doneBtn;
    String[] genderItem = {"Nam", "Nữ"};
    AutoCompleteTextView gender;
    ArrayAdapter<String> genderAdapter;
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

        idStaff = findViewById(R.id.idStaff);
        fullName = findViewById(R.id.fullName);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phoneNumber);
        birthday = findViewById(R.id.birthday);
        type = findViewById(R.id.type);
        if (!message1.equals(""))
            type.setText("Nhân viên ghi danh");
        if (!message2.equals(""))
            type.setText("Giáo viên");


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