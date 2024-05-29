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

public class Activity_Add_Schedule extends AppCompatActivity {
    TextView startDate, endDate;
    DatePickerDialog.OnDateSetListener startDt, endDt;
    Button doneBtn, exitBtn;
    String[] dayOfWeekItem = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"};
    String[] classTimeItem = {"7h00 - 9h00", "9h00 - 11h00", "13h00 - 15h00", "15h00 - 17h00", "17h00 - 19h00", "19h00 - 21h00"};
    String[] idClassroomItem = {"Huhu", "Chào Loan"};
    AutoCompleteTextView dayOfWeek, classTime, idClassroom;
    ArrayAdapter<String> dayOfWeekAdapter, classTimeAdapter, idClassroomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        String message = getIntent().getStringExtra("idSchedule");

        dayOfWeek = findViewById(R.id.dayOfWeek);
        dayOfWeekAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, dayOfWeekItem);
        dayOfWeek.setAdapter(dayOfWeekAdapter);
        dayOfWeek.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        classTime = findViewById(R.id.classTime);
        classTimeAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, classTimeItem);
        classTime.setAdapter(classTimeAdapter);
        classTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        idClassroom = findViewById(R.id.idClassroom);
        idClassroomAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, idClassroomItem);
        idClassroom.setAdapter(idClassroomAdapter);
        idClassroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        startDate = findViewById(R.id.start_date);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Activity_Add_Schedule.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startDt,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        startDt = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                startDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        endDate = findViewById(R.id.end_date);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Activity_Add_Schedule.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        endDt,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        endDt = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                endDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

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
                if (dayOfWeek.getText().equals("") || idClassroom.getText().equals("")) {
                    Toast.makeText(Activity_Add_Schedule.this, "Nhập lại", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });
    }

}