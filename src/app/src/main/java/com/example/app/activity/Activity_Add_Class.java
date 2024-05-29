package com.example.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.app.R;
import com.example.app.adapter.ClassDAO;
import com.example.app.adapter.ProgramDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.adapter.TeacherDAO;
import com.example.app.model.ClassDTO;
import com.example.app.model.ProgramDTO;
import com.example.app.model.StaffDTO;
import com.example.app.model.TeacherDTO;

import java.util.List;

public class Activity_Add_Class extends AppCompatActivity {
    EditText classID, className, startDate, endDate, idTeacher, staffID;
    Button exitBtn, doneBtn;
    String[] roomIDItem = {"Hehe", "Hoho", "Huhu"};
    String[] programIDItem = {"Hmmmm", "Oke", "Ờm Ờm"};
    String[] dayOfWeekItem = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"};
    String[] classTimeItem = {"7h00 - 9h00", "9h00 - 11h00", "13h00 - 15h00", "15h00 - 17h00", "17h00 - 19h00", "19h00 - 21h00"};
    AutoCompleteTextView room, program, dayOfWeek, classTime;
    ArrayAdapter<String> roomAdapter, programAdapter, dayOfWeekAdapter, classTimeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        room = findViewById(R.id.roomID);
        program = findViewById(R.id.programID);

        roomAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, roomIDItem);
        programAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, programIDItem);

        room.setAdapter(roomAdapter);
        room.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });
        program.setAdapter(programAdapter);
        program.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

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

        classID = findViewById(R.id.classID);
        className = findViewById(R.id.class_name);
        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);
        idTeacher = findViewById(R.id.idTeacher);
        staffID = findViewById(R.id.staffID);
        exitBtn = findViewById(R.id.exit_btn);
        doneBtn = findViewById(R.id.done_btn);

        String message = getIntent().getStringExtra("classID");
        List<ClassDTO> listClass = ClassDAO.getInstance(Activity_Add_Class.this).selectClass(
                Activity_Add_Class.this, "ID_CLASS = ? AND STATUS = ?",
                new String[] {message, "0"});
        if (!message.equals("")) {

            Log.d("Id class send", listClass.get(0).toString());

            className.setText(listClass.get(0).getClassName());
            startDate.setText(listClass.get(0).getStartDate());
            endDate.setText(listClass.get(0).getEndDate());

            String idProgram = listClass.get(0).getIdProgram();
            List<ProgramDTO> program = ProgramDAO.getInstance(Activity_Add_Class.this).SelectProgram(
                    Activity_Add_Class.this, "ID_PROGRAM = ? AND STATUS = ?",
                    new String[] {idProgram, "0"});
            String idTeacher = listClass.get(0).getIdTeacher();
            List<TeacherDTO> teacher = TeacherDAO.getInstance(Activity_Add_Class.this).SelectTeacher(
                    Activity_Add_Class.this, "ID_TEACHER = ? AND STATUS = ?",
                    new String[] {idTeacher, "0"});
            String idStaff = listClass.get(0).getIdStaff();
            List<StaffDTO> staff = StaffDAO.getInstance(Activity_Add_Class.this).SelectStaffVer2(
                    Activity_Add_Class.this, "ID_STAFF = ? AND STATUS = ?",
                    new String[] {idStaff, "0"});

            /*programID.setText(program.get(0).getNameProgram().toString());
            teacherName.setText(teacher.get(0).getFullName().toString());*/
            staffID.setText(staff.get(0).getFullName().toString());
            // toolbar.setTitle(className.getText().toString()  + classID.getText().toString());


            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (className.getText().toString().equals("") || classID.getText().toString().equals("")
                            || startDate.getText().toString().equals("") || endDate.getText().toString().equals("")
                            || staffID.getText().toString().equals("")) {
                        Toast.makeText(Activity_Add_Class.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    } else {
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Class.this);
                        builder.setTitle("Xác nhận")
                                .setMessage("Bạn có chắc chắn muốn sửa thông tin lớp học này không?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                List<ProgramDTO> program = ProgramDAO.getInstance(Activity_Add_Class.this).SelectProgram(
                                        Activity_Add_Class.this, "NAME = ? AND STATUS = ?",
                                        new String[] {programID.getText().toString(), "0"});
                                List<TeacherDTO> teacher = TeacherDAO.getInstance(Activity_Add_Class.this).SelectTeacher(
                                        Activity_Add_Class.this, "FULLNAME = ? AND STATUS = ?",
                                        new String[] {teacherName.getText().toString(), "0"});
                                List<StaffDTO> staff = StaffDAO.getInstance(Activity_Add_Class.this).SelectStaffVer2(
                                        Activity_Add_Class.this, "FULLNAME = ? AND STATUS = ?",
                                        new String[] {staffID.getText().toString(), "0"});

                                ClassDTO classUpdate = new ClassDTO(message, className.getText().toString(),
                                        startDate.getText().toString(), endDate.getText().toString(),
                                        program.get(0).getIdProgram(), teacher.get(0).getIdTeacher(), staff.get(0).getIdStaff(), "0");

                                try {
                                    int rowEffect = ClassDAO.getInstance(Activity_Add_Class.this).UpdateClass(
                                            Activity_Add_Class.this, classUpdate,"ID_CLASS = ? AND STATUS = ?",
                                            new String[] {message, "0"});
                                    if (rowEffect > 0) {
                                        Toast.makeText(Activity_Add_Class.this, "Sửa thông tin lớp học thành công",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("Update class failed", "failed");
                                    }
                                } catch ( Exception e) {
                                    Log.d("Update class error: ", e.getMessage());
                                }
                            }
                        });
                        builder.setNegativeButton("Hủy", null);
                        builder.show();*/
                    }
                }
            });

            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Class.this);
                    builder.setTitle("Xác nhận")
                            .setMessage("Bạn chưa hoàn thành chỉnh sửa, bạn có chắc chắn muốn thoát?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy", null);
                    builder.show();
                }
            });

        } else {
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Class.this);
                    builder.setTitle("Xác nhận")
                            .setMessage("Bạn chưa hoàn thành, bạn có chắc chắn muốn thoát?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy", null);
                    builder.show();
                }
            });

            /*doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (className.getText().toString().equals("") || classID.getText().toString().equals("")
                            || startDate.getText().toString().equals("") || teacherName.getText().toString().equals("")
                            || endDate.getText().toString().equals("") || programID.getText().toString().equals("")
                            || staffID.getText().toString().equals("")) {
                        Toast.makeText(Activity_Add_Class.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    } else {

                        List<ProgramDTO> programNew = ProgramDAO.getInstance(Activity_Add_Class.this).SelectProgram(
                                Activity_Add_Class.this, "NAME = ? AND STATUS = ?",
                                new String[] {programID.getText().toString(), "0"});
                        List<TeacherDTO> teacherNew = TeacherDAO.getInstance(Activity_Add_Class.this).SelectTeacher(
                                Activity_Add_Class.this, "FULLNAME = ? AND STATUS = ?",
                                new String[] {teacherName.getText().toString(), "0"});
                        List<StaffDTO> staffNew = StaffDAO.getInstance(Activity_Add_Class.this).SelectStaffVer2(
                                Activity_Add_Class.this, "FULLNAME = ? AND STATUS = ?",
                                new String[] {staffID.getText().toString(), "0"});

                        ClassDTO classNew = new ClassDTO(message, className.getText().toString(),
                                startDate.getText().toString(), endDate.getText().toString(),
                                programNew.get(0).getIdProgram(), teacherNew.get(0).getIdTeacher(), staffNew.get(0).getIdStaff(), "0");

                        try {
                            int rowEffect = ClassDAO.getInstance(Activity_Add_Class.this).InsertClass(
                                    Activity_Add_Class.this, classNew);
                            if (rowEffect > 0) {
                                Toast.makeText(Activity_Add_Class.this, "Thêm lớp học mới thành công",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity_Add_Class.this, "Thêm lớp học mới thất bại",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Add new class failed", "failed");
                        }

                        className.setText("");
                        startDate.setText("");
                        endDate.setText("");
                        programID.setText("");
                        teacherName.setText("");
                        staffID.setText("");
                    }
                }
            });*/

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
                if (className.getText().toString().equals("") || classID.getText().toString().equals("")
                        || startDate.getText().toString().equals("") || idTeacher.getText().toString().equals("")
                        || endDate.getText().toString().equals("") || staffID.getText().toString().equals("")) {
                    Toast.makeText(Activity_Add_Class.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.normal_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}