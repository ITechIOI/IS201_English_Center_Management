package com.example.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.app.R;
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.adapter.TeachingDAO;
import com.example.app.model.ExamScoreDTO;
import com.example.app.model.List_Adapter;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.ProgramDTO;
import com.example.app.model.TeacherDTO;
import com.example.app.model.TeachingDTO;

import java.util.ArrayList;
import java.util.List;

public class Activity_Notifications_ToolBars_Second_Layer extends AppCompatActivity {
    private String message1, message2;
    Toolbar toolbar;
    private List_Adapter listAdapter;
    private ListView listView;
    private ArrayList<Object> dataArrayList;
    private ImageButton returnBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_tool_bars_second_layer);

        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.notification_listview);
        returnBtn = findViewById(R.id.return_btn);
        /*message1 = getIntent().getStringExtra("classID");
        message2 = getIntent().getStringExtra("idCertificate");*/
        dataArrayList = new ArrayList<>();

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setTitle("Học viên");
        String message = getIntent().getStringExtra("classID");
        List<TeachingDTO> listTeaching = new ArrayList<>();

        try {
            listTeaching = TeachingDAO.getInstance(
                    Activity_Notifications_ToolBars_Second_Layer.this).SelectTeaching(
                            Activity_Notifications_ToolBars_Second_Layer.this,
                    "ID_CLASS = ? AND STATUS = ?", new String[] {message, "0"});
        } catch (Exception e) {
            Log.d("Get list student error", e.getMessage());
        }

        List<OfficialStudentDTO> listStudent = new ArrayList<>();
        for (int i = 0; i < listTeaching.size(); i++) {
            try {
                List<OfficialStudentDTO> student = OfficialStudentDAO.getInstance(
                        Activity_Notifications_ToolBars_Second_Layer.this).SelectStudentVer2(
                                Activity_Notifications_ToolBars_Second_Layer.this,
                        "ID_STUDENT = ? AND STATUS = ?",
                        new String[] {listTeaching.get(i).getIdStudent(), "0"});
                listStudent.add(student.get(0));
            } catch (Exception e) {
                Log.d("Get list student in each class", e.getMessage());
            }
        }

        for(int i = 0; i < listStudent.size(); i++) {
            dataArrayList.add(listStudent.get(i));
        }

        dataArrayList.add(new OfficialStudentDTO("1","1","1","1","1","1",1));
        listAdapter = new List_Adapter(Activity_Notifications_ToolBars_Second_Layer.this, R.layout.list_offfical_student_item, dataArrayList);

        /*if (!message1.equals("")) {
            //toolbar.setTitle("Chi tiết lớp học");
            dataArrayList.add(new ExamScoreDTO("1","1","1","1","1","1","1"));
            listAdapter = new List_Adapter(Activity_Notifications_ToolBars_Second_Layer.this, R.layout.list_score_manage_item, dataArrayList);
        }*/

        /*if (!message2.equals("")) {
            dataArrayList.add(new ProgramDTO("1", "1"
                    , "1", "1", "1"
                    , "1", "1", "1"
                    , "1", 10, "1", "1"));
            listAdapter = new List_Adapter(Activity_Notifications_ToolBars_Second_Layer.this, R.layout.list_education_program_item, dataArrayList);
        }*/

        listView.setAdapter(listAdapter);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent addOffical = new Intent(Activity_Notifications_ToolBars_Second_Layer.this, Activity_Add_Exam_Score.class);
        addOffical.putExtra("studentID", "");
        startActivity(addOffical);
        /*if (!message1.equals("")) {
            Intent addOffical = new Intent(Activity_Notifications_ToolBars_Second_Layer.this, Activity_Add_Exam_Score.class);
            addOffical.putExtra("studentID", "");
            startActivity(addOffical);
        }
        if (!message2.equals("")) {
            Intent addProgram = new Intent(Activity_Notifications_ToolBars_Second_Layer.this, Activity_Add_Program.class);
            addProgram.putExtra("idProgram", "");
            startActivity(addProgram);
        }*/
        return super.onOptionsItemSelected(item);
    }
}