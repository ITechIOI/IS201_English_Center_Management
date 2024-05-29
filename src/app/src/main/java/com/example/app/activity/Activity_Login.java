package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.AccountDAO;
import com.example.app.adapter.CertificateDAO;
import com.example.app.adapter.ClassDAO;
import com.example.app.adapter.ClassroomDAO;
import com.example.app.adapter.CollectionTuitionFeesDAO;
import com.example.app.adapter.DataProvider;
import com.example.app.adapter.ExamScoreDAO;
import com.example.app.adapter.ExaminationDAO;
import com.example.app.adapter.NotificationDAO;
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.adapter.PotentialStudentDAO;
import com.example.app.adapter.ProgramDAO;
import com.example.app.adapter.ScheduleDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.adapter.TeacherDAO;
import com.example.app.adapter.TeachingDAO;
import com.example.app.model.AccountDTO;
import com.example.app.model.CertificateDTO;
import com.example.app.model.ClassDTO;
import com.example.app.model.ClassroomDTO;
import com.example.app.model.CollectionTuitionFeesDTO;
import com.example.app.model.ExamScoreDTO;
import com.example.app.model.ExaminationDTO;
import com.example.app.model.NotificationDTO;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.PotentialStudentDTO;
import com.example.app.model.ProgramDTO;
import com.example.app.model.ScheduleDTO;
import com.example.app.model.StaffDTO;
import com.example.app.model.TeacherDTO;
import com.example.app.model.TeachingDTO;

public class Activity_Login extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    Button loginBtn;
    public static String idUser;
    public static String password;
    public static String username;
    public static String idAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);
        loginBtn = findViewById(R.id.login_btn);

        // Initialize database

        DataProvider.getInstance(Activity_Login.this).recreateDatabase(Activity_Login.this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // handle login event
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Activity_Login.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {

                    String whereClause = "USERNAME = ? AND PASSWORD = ?";
                    String[] whereArgs = new String[] {username, password};
                    Cursor cursor = AccountDAO.getInstance(Activity_Login.this).selectAccount(Activity_Login.this, whereClause, whereArgs);

                    if (cursor!= null && cursor.getCount() > 0) {
                        Intent intent = new Intent(Activity_Login.this, Activity_Main_Screen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Activity_Login.this, "Please enter correct username and password", Toast.LENGTH_SHORT).show();
                    }

                    if (cursor.moveToFirst()) {
                        do {
                            int idIndex = cursor.getColumnIndex("ID_USER");
                            if (idIndex!= -1) {
                                idUser = cursor.getString(idIndex);
                            }
                            int idAccIndex = cursor.getColumnIndex("ID_ACCOUNT");
                            if (idAccIndex!= -1) {
                                idAccount= cursor.getString(idAccIndex);
                            }

                        } while (cursor.moveToNext());
                    }

                    cursor.close();
                }

                // Log.d("I'm on create: ", username.toString() + "  " + password + "  ");

            }
        });
        ;

/*

        // Insert data in OFFICIAL_STUDENT

        OfficialStudentDTO student1 = new OfficialStudentDTO("STU1", "Nguyen Van A", "Binh Dinh", "034343434", "Nam", "22/2/2022", 0);
        OfficialStudentDAO.getInstance(Activity_Login.this).insertOfficialStudent(Activity_Login.this, student1);
        OfficialStudentDTO student2 = new OfficialStudentDTO("STU2", "Le Thi B", "Binh Duong","0232323222", "Nữ", "22/2/2022", 0 );
        OfficialStudentDAO.getInstance(Activity_Login.this).insertOfficialStudent(Activity_Login.this, student2);

        // Insert data in STAFF

        StaffDTO staff1 = new StaffDTO("STA1", "Nguyen Thi C", "TP HCM", "0343333333", "Nữ", "22/2/2022", 1, "1",1);
        StaffDAO.getInstance(Activity_Login.this).insertStaff(Activity_Login.this, staff1);
        StaffDTO staff2 = new StaffDTO("STA2", "Nguyen Thi D", "TP HCM", "03435555333", "Nữ","22/2/2022", 1, "2",1);
        StaffDAO.getInstance(Activity_Login.this).insertStaff(Activity_Login.this, staff2);
        StaffDTO staff3 = new StaffDTO("STA3", "Nguyen Thi E", "TP HCM", "03435555333", "Nữ","22/2/2022", 1, "3",1);
        StaffDAO.getInstance(Activity_Login.this).insertStaff(Activity_Login.this, staff3);

        // Insert data in TEACHERS

        TeacherDTO teacher1 = new TeacherDTO("TEA1", "Nguyen Thi E", "Binh Duong", "0346655566", "Nữ", "22/2/2022", 50000000);
        TeacherDAO.getInstance(Activity_Login.this).insertTeacher(Activity_Login.this, teacher1);
        TeacherDTO teacher2 = new TeacherDTO("TEA2", "Nguyen Thi G", "Binh Duong", "0346699966", "Nam", "23/2/2022", 50000000);
        TeacherDAO.getInstance(Activity_Login.this).insertTeacher(Activity_Login.this, teacher2);

        // Insert data ACCOUNT

        AccountDTO account1 = new AccountDTO("ACC1", "STA1", "nguyenthic", "thic123");
        AccountDTO account2 = new AccountDTO("ACC2", "STA2", "nguyenthid", "thid123");
        AccountDTO account5 = new AccountDTO("ACC5", "STU1", "nguyenthia", "thia123");
        AccountDTO account6 = new AccountDTO("ACC6", "STU2", "nguyenthib", "thib123");
        AccountDTO account7 = new AccountDTO("ACC6", "STA6", "nguyenthie", "thie123");
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account1);
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account2);
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account5);
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account6);
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account7);


        // Insert data in NOTIFICATION

        NotificationDTO notification1 =  new NotificationDTO("NOT1", "ACC1", "Thông báo nghi học", "Nghỉ học từ ngày 13/4/2024 đến hết ngày 30/5/2024");
        NotificationDAO.getInstance(Activity_Login.this).InsertNotification(Activity_Login.this, notification1);
        NotificationDTO notification2 =  new NotificationDTO("NOT2", "ACC2", "Thông báo học bù", "Học bù từ ngày 13/4/2024 đến hết ngày 30/5/2024");
        NotificationDAO.getInstance(Activity_Login.this).InsertNotification(Activity_Login.this, notification2);

        // Insert potential student

        PotentialStudentDTO pStudent1 = new PotentialStudentDTO("Nguyễn Văn H", "0343884343",
                "Nam", "Bình Dương", "0", "5.5-6", "10");
        PotentialStudentDTO pStudent2 = new PotentialStudentDTO("Nguyen Thị I", "0977748343",
                "Nữ", "Bình Dương", "0", "5.5-6", "10");
        PotentialStudentDAO.getInstance(Activity_Login.this).InsertPotentialStudent(Activity_Login.this, pStudent1);
        PotentialStudentDAO.getInstance(Activity_Login.this).InsertPotentialStudent(Activity_Login.this, pStudent2);

         // Insert program

        ProgramDTO program1 = new ProgramDTO("PRO1", "Chứng chỉ ielts từ 5.5-6.0",
              "5.0", "6.5", "Cải thiện khả năng nghe",
              "5.5", "6.5", "7.0", "7.0",
              5000000, "6 tháng", "CER1");
        ProgramDTO program2 = new ProgramDTO("PRO2", "Chứng chỉ ielts từ 5.5-6.0",
                "5.0", "6.5", "Cải thiện khả năng nói",
                "6.5", "6.5", "7.0", "7.0",
                10000000, "6 tháng", "CER1");
        ProgramDAO.getInstance(Activity_Login.this).InsertProgram(Activity_Login.this, program1);
        ProgramDAO.getInstance(Activity_Login.this).InsertProgram(Activity_Login.this, program2);

        // Insert data in CLASSROOM

        ClassroomDTO classroom1 = new ClassroomDTO("CLA1", "B1.11");
        ClassroomDTO classroom2 = new ClassroomDTO("CLA2", "B1.12");
        ClassroomDAO.getInstance(Activity_Login.this).InsertNewClassroom(Activity_Login.this, classroom1);
        ClassroomDAO.getInstance(Activity_Login.this).InsertNewClassroom(Activity_Login.this, classroom2);

        // Insert data in CLASS

        ClassDTO class1 = new ClassDTO("CLS1", "Lớp ielts từ 5.9-6.5 giúp cải thiện kĩ năng nghe",
                "22/05/2024", "22/12/2024", "PRO1", "TEA1", "STA1", "0");
        ClassDTO class2 = new ClassDTO("CLS1", "Lớp ielts từ 5.9-6.5 giúp cải thiện kĩ năng đọc",
                "22/05/2024", "22/12/2024", "PRO1", "TEA1", "STA1", "0");
        ClassDAO.getInstance(Activity_Login.this).InsertClass(Activity_Login.this, class2);
        ClassDAO.getInstance(Activity_Login.this).InsertClass(Activity_Login.this, class1);

        // Insert data in Schedule

        ScheduleDTO schedule1 = new ScheduleDTO("SCH1", "2", "14", "16",
                "CLS1", "CLA1");
        ScheduleDTO schedule2= new ScheduleDTO("SCH2", "3", "7", "9",
                "CLS1", "CLA2");
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule1);
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule2);

        // Insert data in Certificate

        CertificateDTO certificate1 = new CertificateDTO(null, "Ielts Academic",
                "Ielts Academic được công nhận rộng rãi như là yêu cầu ngôn ngữ đầu " +
                        "vào cho tất cả các khóa học Đại học và Sau Đại học");
        CertificateDTO certificate2 = new CertificateDTO(null, "Ielts General",
                "Ielts General hích hợp cho tất cả những ai chuẩn bị tới các nước nói " +
                        "tiếng Anh để hoàn tất chương trình trung học, các chương trình đào " +
                        "tạo hoặc với mục đích nhập cư.");
        CertificateDAO.getInstance(Activity_Login.this).InsertCertificate(Activity_Login.this,
                certificate1);
        CertificateDAO.getInstance(Activity_Login.this).InsertCertificate(Activity_Login.this,
                certificate2);

        // Insert data in Collecting_Tuition_Fees

        CollectionTuitionFeesDTO collectionTuitionFees1 = new CollectionTuitionFeesDTO(
                null, "STU1", "23/5/2024 15:23:22", "5000000");
        CollectionTuitionFeesDTO collectionTuitionFees2 = new CollectionTuitionFeesDTO(
                null, "STU2", "23/5/2024 15:23:22", "5000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees1);
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees2);

        // Insert data in Examination

        ExaminationDTO exam1 = new ExaminationDTO(null, "Kỳ thi tuyển sinh đầu vào",
                "Format Ielts", "23/05/2024", "CLS1", "CLA1");
        ExaminationDTO exam2 = new ExaminationDTO(null, "Kiểm tra đầu ra",
                "Format Ielts", "23/07/2024", "CLS1", "CLA1");
        ExaminationDAO.getInstance(Activity_Login.this).InsertExamination(Activity_Login.this,
                exam1);
        ExaminationDAO.getInstance(Activity_Login.this).InsertExamination(Activity_Login.this,
                exam2);

        // Insert data in ExamScore

        ExamScoreDTO examScore1 = new ExamScoreDTO(null, "EXA1", "STU1",
                "6", "7", "6", "6");
        ExamScoreDTO examScore2 = new ExamScoreDTO(null, "EXA1", "STU2",
                "6", "7", "6", "6");
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore1);
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore2);

        // Insert data in Teaching

        TeachingDTO teaching1 = new TeachingDTO(null, "STU1", "CLS1");
        TeachingDTO teaching2 = new TeachingDTO(null, "STU2", "CLS1");
        TeachingDTO teaching3 = new TeachingDTO(null, "STU2", "CLS2");
        TeachingDAO.getInstance(Activity_Login.this).InsertDate(Activity_Login.this,
                teaching1);
        TeachingDAO.getInstance(Activity_Login.this).InsertDate(Activity_Login.this,
                teaching2);
        TeachingDAO.getInstance(Activity_Login.this).InsertDate(Activity_Login.this,
                teaching3);
*/
        OfficialStudentDTO student1 = new OfficialStudentDTO("STU1", "Nguyen Van A", "Binh Dinh", "034343434", "Nam", "22/2/2022", 0);
        OfficialStudentDAO.getInstance(Activity_Login.this).insertOfficialStudent(Activity_Login.this, student1);
        OfficialStudentDTO student2 = new OfficialStudentDTO("STU2", "Le Thi B", "Binh Duong","0232323222", "Nữ", "22/2/2022", 0 );
        OfficialStudentDAO.getInstance(Activity_Login.this).insertOfficialStudent(Activity_Login.this, student2);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);
        loginBtn = findViewById(R.id.login_btn);

        // Initialize database

        DataProvider.getInstance(Activity_Login.this).recreateDatabase(Activity_Login.this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // handle login event
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Activity_Login.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {

                    String whereClause = "USERNAME = ? AND PASSWORD = ?";
                    String[] whereArgs = new String[]{username, password};
                    Cursor cursor = AccountDAO.getInstance(Activity_Login.this).selectAccount(Activity_Login.this, whereClause, whereArgs);

                    if (cursor != null && cursor.getCount() > 0) {
                        Intent intent = new Intent(Activity_Login.this, Activity_Main_Screen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Activity_Login.this, "Please enter correct username and password", Toast.LENGTH_SHORT).show();
                    }

                    if (cursor.moveToFirst()) {
                        do {
                            int idIndex = cursor.getColumnIndex("ID_USER");
                            if (idIndex != -1) {
                                idUser = cursor.getString(idIndex);
                            }
                            int idAccIndex = cursor.getColumnIndex("ID_ACCOUNT");
                            if (idAccIndex != -1) {
                                idAccount = cursor.getString(idAccIndex);
                            }

                        } while (cursor.moveToNext());
                    }

                    cursor.close();
                }
                // Log.d("I'm on start: ", username.toString() + "  " + password + "  ");

            }
        });
        
    }

}