package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.app.adapter.AccountDAO;
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.model.AccountDTO;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.StaffDTO;

import java.util.Calendar;

public class Activity_Change_Setting extends AppCompatActivity {
    String[] genderItem = {"Nam", "Nữ"};
    AutoCompleteTextView genderInp;
    ArrayAdapter<String> genderAdapter;
    private Button done;
    private EditText phoneInp, addrInp, passInp;
    TextView birthdayInp;
    DatePickerDialog.OnDateSetListener birthDt;

    TextView nameInp, position;
    private int flag;


    String fullName = "";
    String address = "";
    String phoneNumber = "";
    String gender = "";
    int salary;
    boolean isUpdate;
    int type;
    String birthday = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_setting);

        birthdayInp = findViewById(R.id.input_birthday);
        birthdayInp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Activity_Change_Setting.this,
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
                birthdayInp.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        genderInp = findViewById(R.id.input_gender);
        genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
        genderInp.setAdapter(genderAdapter);
        genderInp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        phoneInp = findViewById(R.id.input_phone);
        addrInp = findViewById(R.id.input_addr);
        passInp = findViewById(R.id.input_password);
        nameInp = findViewById(R.id.name);
        position = findViewById(R.id.position);


        done = findViewById(R.id.doneBtn);

        String idUser = Activity_Login.idUser;
        String titleIdAccount = idUser.substring(0, idUser.length() - 1) ;

         type = 0;
        String positionText = "";

        if (titleIdAccount.equals("STU")) {
            flag = 1;

            String whereClause = "ID_STUDENT = ?";
            String[] whereArgs = new String[] {idUser};
            Cursor cursor = OfficialStudentDAO.getInstance(Activity_Change_Setting.this).SelectStudent(Activity_Change_Setting.this, whereClause, whereArgs);

            if (cursor.moveToFirst()) {
                do {

                    int fullNameIndex = cursor.getColumnIndex("FULLNAME");
                    if (fullNameIndex!= -1) {
                        fullName = cursor.getString(fullNameIndex);
                    }
                    int birthdaysIndex = cursor.getColumnIndex("BIRTHDAY");
                    if (birthdaysIndex!= -1) {
                        birthday = cursor.getString(birthdaysIndex);
                    }
                    int addressIndex = cursor.getColumnIndex("ADDRESS");
                    if (addressIndex!= -1) {
                        address = cursor.getString(addressIndex);
                    }
                    int phoneNumberIndex = cursor.getColumnIndex("PHONE_NUMBER");
                    if (phoneNumberIndex != -1) {
                        phoneNumber = cursor.getString(phoneNumberIndex);
                    }
                    int genderIndex = cursor.getColumnIndex("GENDER");
                    if (genderIndex != -1) {
                        gender = cursor.getString(genderIndex);
                    }
                    positionText = "Học viên";
                    Log.d("Find Student", fullName + " " + address + " " + phoneNumber + " " + gender);

                } while (cursor.moveToNext());
            }

        }else {
            flag = 2;

            String whereClause = "ID_STAFF = ?";
            String[] whereArgs = new String[] {idUser};
            Cursor cursor = StaffDAO.getInstance(Activity_Change_Setting.this).SelectStaff(Activity_Change_Setting.this, whereClause, whereArgs);

            if (cursor.moveToFirst()) {
                do {
                    int fullNameIndex = cursor.getColumnIndex("FULLNAME");
                    if (fullNameIndex!= -1) {
                        fullName = cursor.getString(fullNameIndex);
                    }
                    int birthdayIndex = cursor.getColumnIndex("BIRTHDAY");
                    if (birthdayIndex!= -1) {
                        birthday = cursor.getString(birthdayIndex);
                    }
                    int addressIndex = cursor.getColumnIndex("ADDRESS");
                    if (addressIndex!= -1) {
                        address = cursor.getString(addressIndex);
                    }
                    int phoneNumberIndex = cursor.getColumnIndex("PHONE_NUMBER");
                    if (phoneNumberIndex != -1) {
                        phoneNumber = cursor.getString(phoneNumberIndex);
                    }
                    int genderIndex = cursor.getColumnIndex("GENDER");
                    if (genderIndex != -1) {
                        gender = cursor.getString(genderIndex);
                    }
                    int salaryIndex = cursor.getColumnIndex("SALARY");
                    if (salaryIndex != -1) {
                        salary = cursor.getInt(salaryIndex);
                    }
                    int typeIndex = cursor.getColumnIndex("TYPE");
                    if (typeIndex != -1) {
                        type = cursor.getInt(typeIndex);
                        if (type == 1) {
                            positionText = "Quản lý";
                        } else if (type == 2) {
                            positionText = "Nhân viên học vụ";
                        } else {
                            positionText = "Nhân viên điểm danh";
                        }
                    }
                } while (cursor.moveToNext());
            }
        }

        genderInp.setText(gender);
        phoneInp.setText(phoneNumber);
        addrInp.setText(address);
        passInp.setText(Activity_Login.password);
        nameInp.setText(fullName);
        birthdayInp.setText(birthday);
        position.setText(positionText);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (genderInp.getText().toString().equals("") || !phoneInp.getText().toString().equals("")
                        || !addrInp.getText().toString().equals("") || !passInp.getText().toString().equals("")
                        || !nameInp.getText().toString().equals("") || !birthday.isEmpty()) {
                    Toast.makeText(Activity_Change_Setting.this, "Nhập lại", Toast.LENGTH_SHORT).show();
                } else {

                    // Handle updating user information

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Change_Setting.this);
                    builder.setTitle("Bạn có chắc chắn muốn cập nhật dữ liệu không ?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String genderUpdate = genderInp.getText().toString();
                            String phoneNumberUpdate = phoneInp.getText().toString();
                            String addressUpdate = addrInp.getText().toString();
                            String birthdayUpdate = birthdayInp.getText().toString();

                            // Update password

                            AccountDTO updateAccount = new AccountDTO(Activity_Login.idAccount,
                                    Activity_Login.idUser, Activity_Login.username, passInp.getText().toString());
                            String whereClause = "ID_ACCOUNT = ?";
                            String[] whereArg =  new String[]{Activity_Login.idAccount};

                            Activity_Login.password = passInp.getText().toString();

                            int rowEffect = AccountDAO.getInstance(Activity_Change_Setting.this).updateAccount(Activity_Change_Setting.this,
                                    updateAccount, whereClause, whereArg);
                            Log.d("Change account: ", String.valueOf(rowEffect));
                            if (rowEffect > 0) {
                                isUpdate = true;
                            }

                            // Update individual information

                            if (flag == 1) {
                                OfficialStudentDTO student = new OfficialStudentDTO(idUser,
                                        fullName, addressUpdate, phoneNumberUpdate, genderUpdate, birthdayUpdate, 0);
                                String whereClauseUpdateInf = "ID_STUDENT = ?";
                                String[] whereArgUpdateInf =  new String[]{Activity_Login.idUser};

                                int rowEffectStudent = OfficialStudentDAO.getInstance(Activity_Change_Setting.this).updateOfficialStudent(Activity_Change_Setting.this,
                                        student, whereClauseUpdateInf, whereArgUpdateInf);
                                if (rowEffectStudent > 0) {
                                    isUpdate = true;
                                }
                            } else {
                                StaffDTO staff = new StaffDTO(idUser, fullName, addressUpdate, phoneNumberUpdate,
                                        genderUpdate, birthdayUpdate, salary,  String.valueOf(type), 0);
                                String whereClauseUpdateInf = "ID_STAFF = ?";
                                String[] whereArgUpdateInf =  new String[]{Activity_Login.idUser};

                                int rowEffectStaff = StaffDAO.getInstance(Activity_Change_Setting.this).updateStaff(Activity_Change_Setting.this,
                                        staff, whereClauseUpdateInf, whereArgUpdateInf);
                                if(rowEffectStaff > 0) {
                                    isUpdate = true;
                                }
                            }
                            Toast.makeText(Activity_Change_Setting.this, "Cập nhật dữ liệu thành công !", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                    builder.setNegativeButton("Hủy", null);

                    builder.show();

                }
            }

        });

    }
}