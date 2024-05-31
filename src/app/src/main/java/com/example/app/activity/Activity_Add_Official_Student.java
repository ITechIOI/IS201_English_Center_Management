package com.example.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.adapter.TeacherDAO;
import com.example.app.adapter.TeachingDAO;
import com.example.app.model.List_Adapter;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.TeachingDTO;

import java.util.Calendar;
import java.util.List;

public class Activity_Add_Official_Student extends AppCompatActivity {
    EditText studentName, phoneNumber, address, totalMoney;
    TextView birthday, collectingDate;
    Button doneBtn, exitBtn;
    String[] genderItem = {"Nam", "Nữ"};
    AutoCompleteTextView gender;
    ArrayAdapter<String> genderAdapter;
    String genderText = "";
    DatePickerDialog.OnDateSetListener birthDt, collectDt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_official_student);

        gender = findViewById(R.id.gender);
        genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
        gender.setAdapter(genderAdapter);

        gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                genderText = parent.getItemAtPosition(position).toString();
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
                        Activity_Add_Official_Student.this,
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

        collectingDate = findViewById(R.id.collectingDate);
        collectingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Activity_Add_Official_Student.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        collectDt,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        collectDt = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                collectingDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        studentName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        totalMoney = findViewById(R.id.totalMoney);
        exitBtn = findViewById(R.id.exit_btn);
        doneBtn = findViewById(R.id.done_btn);

        String studentId = getIntent().getStringExtra("studentID");
       // String classId = getIntent().getStringExtra("classID");

        if (!studentId.equals("")) {

            List<OfficialStudentDTO> student = OfficialStudentDAO.getInstance(
                    Activity_Add_Official_Student.this).SelectStudentVer2(
                            Activity_Add_Official_Student.this, "ID_STUDENT = ?" +
                            "AND STATUS = ?", new String[] {studentId, "0"});

            studentName.setText(student.get(0).getFullName().toString());
            phoneNumber.setText(student.get(0).getPhoneNumber());
            gender.setText("Nam");
            genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
            gender.setAdapter(genderAdapter);
            address.setText(student.get(0).getAddress());
            birthday.setText(student.get(0).getBirthday());
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Official_Student.this);
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
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (address.getText().toString().equals("")
                            || birthday.getText().toString().equals("")
                            || studentName.getText().toString().equals("")
                            || gender.getText().toString().equals("")
                            || phoneNumber.getText().toString().equals("")) {
                        Toast.makeText(Activity_Add_Official_Student.this, "Hãy nhập đầy " +
                                "đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Official_Student.this);
                        builder.setTitle("Xác nhận")
                                .setMessage("Bạn có chắc chắn muốn sửa thông tin của học viên không?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                OfficialStudentDTO studentUpdate = new OfficialStudentDTO(null, studentName.getText().toString(),
                                        address.getText().toString(),
                                        phoneNumber.getText().toString(), gender.getText().toString(),
                                        birthday.getText().toString(), 0);
                                try {
                                    int rowEffect = OfficialStudentDAO.getInstance(Activity_Add_Official_Student.this).updateOfficialStudent(
                                            Activity_Add_Official_Student.this, studentUpdate,
                                            "ID_STUDENT = ? AND STATUS = ?",
                                            new String[] {studentId, "0"});
                                    if (rowEffect > 0) {
                                        Toast.makeText(Activity_Add_Official_Student.this,
                                                "Sửa thông tin học viên thành công!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Activity_Add_Official_Student.this,
                                                "Sửa thông tin học viên thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.d("Update student information:", "success");
                                }
                            }
                        });
                        builder.setNegativeButton("Hủy", null);
                        builder.show();
                    }
                }
            });

        } else {
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Official_Student.this);
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

            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (address.getText().equals("")
                            || birthday.getText().equals("") || studentName.getText().equals("")
                            || gender.getText().equals("") || phoneNumber.getText().equals("")) {
                        Toast.makeText(Activity_Add_Official_Student.this, "Hãy nhập đầy " +
                                "đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {

                        List<OfficialStudentDTO> listStudent = OfficialStudentDAO.getInstance(
                                Activity_Add_Official_Student.this).SelectStudentVer2(
                                Activity_Add_Official_Student.this,
                                "FULLNAME = ? AND PHONE_NUMBER = ? AND BIRTHDAY = ?" +
                                        "AND GENDER = ? AND STATUS = ? AND ADDRESS = ?",
                                new String[] {studentName.getText().toString(), phoneNumber.getText().toString(),
                                        birthday.getText().toString(), gender.getText().toString(), "0",
                                        address.getText().toString()}
                        );
                        String idStudent = "";
                        if (listStudent.size() == 0) {
                            OfficialStudentDTO studentNew = new OfficialStudentDTO(null, studentName.getText().toString(),
                                    address.getText().toString(), phoneNumber.getText().toString(),
                                    gender.getText().toString(), birthday.getText().toString(), 0 );
                            try {
                                int rowEffect = OfficialStudentDAO.getInstance(Activity_Add_Official_Student.this)
                                        .insertOfficialStudent(Activity_Add_Official_Student.this, studentNew);
                                if (rowEffect > 0) {
                                    Log.d("Add new official student: ", "success");
                                } else {
                                    Log.d("Add new official student: ", "failed");
                                }
                            } catch (Exception e) {
                                Log.d("Add new official student error", e.getMessage());
                            }
                            List<OfficialStudentDTO> findStudent = OfficialStudentDAO.getInstance(
                                    Activity_Add_Official_Student.this).SelectStudentVer2(
                                    Activity_Add_Official_Student.this,
                                    "FULLNAME = ? AND PHONE_NUMBER = ? AND BIRTHDAY = ?" +
                                            "AND GENDER = ? AND STATUS = ? AND ADDRESS = ?",
                                    new String[] {studentName.getText().toString(), phoneNumber.getText().toString(),
                                            birthday.getText().toString(), gender.getText().toString(), "0",
                                            address.getText().toString()}
                            );
                            idStudent = findStudent.get(0).getIdStudent();
                        } else {
                            idStudent = listStudent.get(0).getIdStudent();
                        }

                        TeachingDTO teachingNew = new TeachingDTO(null, idStudent,
                                List_Adapter.idClassClick);
                        try {
                            int rowEffect = TeachingDAO.getInstance(Activity_Add_Official_Student.this)
                                    .InsertTeaching(Activity_Add_Official_Student.this, teachingNew);
                            if (rowEffect > 0) {
                                Toast.makeText(Activity_Add_Official_Student.this, "Thêm học viên mới " +
                                        "vào lớp thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity_Add_Official_Student.this, "Thêm học viên mới " +
                                        "vào lớp thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Add new teaching relationship: ", "failed");
                        }

                    }
                }
            });
        }

    }

    @Override

    protected void onStart(){
        super.onStart();

        String studentId = getIntent().getStringExtra("studentID");
        String classId = getIntent().getStringExtra("classID");

        Log.d("Push clas id to activity add student: ", classId + " ok");

        if (!studentId.equals("")) {

            List<OfficialStudentDTO> student = OfficialStudentDAO.getInstance(
                    Activity_Add_Official_Student.this).SelectStudentVer2(
                    Activity_Add_Official_Student.this, "ID_STUDENT = ?" +
                            "AND STATUS = ?", new String[] {studentId, "0"});

            studentName.setText(student.get(0).getFullName().toString());
            phoneNumber.setText(student.get(0).getPhoneNumber());
            gender.setText(student.get(0).getGender());
            genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
            gender.setAdapter(genderAdapter);
            address.setText(student.get(0).getAddress());
            birthday.setText(student.get(0).getBirthday());
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Official_Student.this);
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
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (address.equals("")
                            || birthday.equals("") || studentName.equals("")
                            || gender.getText().toString().equals("") || phoneNumber.equals("")) {
                        Toast.makeText(Activity_Add_Official_Student.this, "Hãy nhập đầy " +
                                "đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Official_Student.this);
                        builder.setTitle("Xác nhận")
                                .setMessage("Bạn có chắc chắn muốn sửa thông tin của học viên không?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                OfficialStudentDTO studentUpdate = new OfficialStudentDTO(null, studentName.getText().toString(),
                                        address.getText().toString(),
                                        phoneNumber.getText().toString(), gender.getText().toString(),
                                        birthday.getText().toString(), 0);
                                try {
                                    int rowEffect = OfficialStudentDAO.getInstance(Activity_Add_Official_Student.this).updateOfficialStudent(
                                            Activity_Add_Official_Student.this, studentUpdate,
                                            "ID_STUDENT = ? AND STATUS = ?",
                                            new String[] {studentId, "0"});
                                    if (rowEffect > 0) {
                                        Toast.makeText(Activity_Add_Official_Student.this,
                                                "Sửa thông tin học viên thành công!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Activity_Add_Official_Student.this,
                                                "Sửa thông tin học viên thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.d("Update student information:", "success");
                                }
                            }
                        });
                        builder.setNegativeButton("Hủy", null);
                        builder.show();
                    }
                }
            });

        } else {
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Official_Student.this);
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

            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (address.getText().equals("")
                            || birthday.getText().equals("") || studentName.getText().equals("")
                            || gender.getText().equals("") || phoneNumber.getText().equals("")) {
                        Toast.makeText(Activity_Add_Official_Student.this, "Hãy nhập đầy " +
                                "đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {

                        List<OfficialStudentDTO> listStudent = OfficialStudentDAO.getInstance(
                                Activity_Add_Official_Student.this).SelectStudentVer2(
                                        Activity_Add_Official_Student.this,
                                "FULLNAME = ? AND PHONE_NUMBER = ? AND BIRTHDAY = ?" +
                                        "AND GENDER = ? AND STATUS = ? AND ADDRESS = ?",
                                new String[] {studentName.getText().toString(), phoneNumber.getText().toString(),
                                        birthday.getText().toString(), gender.getText().toString(), "0",
                                        address.getText().toString()}
                                );
                        String idStudent = "";
                        if (listStudent.size() == 0) {
                            OfficialStudentDTO studentNew = new OfficialStudentDTO(null, studentName.getText().toString(),
                                    address.getText().toString(), phoneNumber.getText().toString(),
                                    gender.getText().toString(), birthday.getText().toString(), 0 );
                            try {
                                int rowEffect = OfficialStudentDAO.getInstance(Activity_Add_Official_Student.this)
                                        .insertOfficialStudent(Activity_Add_Official_Student.this, studentNew);
                                if (rowEffect > 0) {
                                    Log.d("Add new official student: ", "success");
                                } else {
                                    Log.d("Add new official student: ", "failed");
                                }
                            } catch (Exception e) {
                                Log.d("Add new official student error", e.getMessage());
                            }
                            List<OfficialStudentDTO> findStudent = OfficialStudentDAO.getInstance(
                                    Activity_Add_Official_Student.this).SelectStudentVer2(
                                    Activity_Add_Official_Student.this,
                                    "FULLNAME = ? AND PHONE_NUMBER = ? AND BIRTHDAY = ?" +
                                            "AND GENDER = ? AND STATUS = ? AND ADDRESS = ?",
                                    new String[] {studentName.getText().toString(), phoneNumber.getText().toString(),
                                            birthday.getText().toString(), gender.getText().toString(), "0",
                                            address.getText().toString()}
                            );
                            idStudent = findStudent.get(0).getIdStudent();
                        } else {
                            idStudent = listStudent.get(0).getIdStudent();
                        }

                        TeachingDTO teachingNew = new TeachingDTO(null, idStudent,
                                List_Adapter.idClassClick);
                        try {
                            int rowEffect = TeachingDAO.getInstance(Activity_Add_Official_Student.this)
                                    .InsertTeaching(Activity_Add_Official_Student.this, teachingNew);
                            if (rowEffect > 0) {
                                Toast.makeText(Activity_Add_Official_Student.this, "Thêm học viên mới " +
                                        "vào lớp thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity_Add_Official_Student.this, "Thêm học viên mới " +
                                        "vào lớp thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Add new teaching relationship: ", "failed");
                        }

                    }
                }
            });
        }
    }

}