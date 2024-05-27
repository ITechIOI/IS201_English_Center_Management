package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Activity_Change_Setting extends AppCompatActivity {
    private TextView birthdayErr, genderErr, phoneNumErr, addressErr, passErr;     //Hiển thị thông báo nhập sai dữ liệu
    private Button done;
    private EditText genderInp, phoneInp, birthdayInp, addrInp, passInp;
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

        birthdayErr = findViewById(R.id.wrong_status);
        genderErr = findViewById(R.id.wrong_gender);
        phoneNumErr = findViewById(R.id.wrong_number);
        addressErr = findViewById(R.id.wrong_address);
        passErr = findViewById(R.id.wrong_password);

        birthdayErr.setVisibility(View.GONE);
        genderErr.setVisibility(View.GONE);
        phoneNumErr.setVisibility(View.GONE);
        addressErr.setVisibility(View.GONE);
        passErr.setVisibility(View.GONE);

        birthdayInp = findViewById(R.id.input_birthday);
        genderInp = findViewById(R.id.input_gender);
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
                boolean acceptSwitch = true;    //Đúng thì mới trả về Fragment_Setting

                if (!birthday.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/([0-2][0-9]{3})$")) {
                    acceptSwitch = false; // Lưu ý: 'acceptSwitch' nên là một biến boolean, không phải kiểu dữ liệu khác
                    birthdayErr.setVisibility(View.VISIBLE);
                } else {
                    birthdayErr.setVisibility(View.GONE);
                }


                String gender = genderInp.getText().toString();
                if (!gender.equals("Nam") && !gender.equals("Nữ")) {
                    acceptSwitch = false;
                    genderErr.setVisibility(View.VISIBLE);
                } else genderErr.setVisibility(View.GONE);

                String regexPhone = "^(\\+[0-9]{1,3}[- ]?)?([0-9]{10,12})$";
                String phone = phoneInp.getText().toString();
                if (!phone.matches(regexPhone)) {
                    acceptSwitch = false;
                    phoneNumErr.setVisibility(View.VISIBLE);
                } else phoneNumErr.setVisibility(View.GONE);

                String address = addrInp.getText().toString();
                if (address.equals("")) {
                    acceptSwitch = false;
                    addressErr.setVisibility(View.VISIBLE);
                } else addressErr.setVisibility(View.GONE);

                String pass = passInp.getText().toString();
                if (pass.length() < 6) {
                    acceptSwitch = false;
                    passErr.setVisibility(View.VISIBLE);
                } else passErr.setVisibility(View.GONE);

                if (acceptSwitch) {

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
                else Toast.makeText(Activity_Change_Setting.this, "Nhập lại", Toast.LENGTH_SHORT).show();
            }

        });

    }
}