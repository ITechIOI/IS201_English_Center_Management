package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.app.R;
import com.example.app.model.ExamScoreDTO;
import com.example.app.model.List_Adapter;
import com.example.app.model.ProgramDTO;

import java.util.ArrayList;

public class Activity_Notifications_Second_Layer extends AppCompatActivity {
    private List_Adapter listAdapter;
    private ListView listView;
    private ArrayList<Object> dataArrayList;
    private ImageButton returnBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_second_layer);

        String message1 = getIntent().getStringExtra("classID");
        String message2 = getIntent().getStringExtra("idCertificate");

        listView = findViewById(R.id.notification_listview);
        returnBtn = findViewById(R.id.return_to_frag_btn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dataArrayList = new ArrayList<>();

        if (!message1.equals("")) {
            dataArrayList.add(new ExamScoreDTO("1","1","1","1","1","1","1"));
            listAdapter = new List_Adapter(Activity_Notifications_Second_Layer.this, R.layout.list_score_manage_item, dataArrayList);
        }

        if (!message2.equals("")) {
            dataArrayList.add(new ProgramDTO("1", "1"
                    , "1", "1", "1"
                    , "1", "1", "1"
                    , "1", 10, "1", "1"));
            listAdapter = new List_Adapter(Activity_Notifications_Second_Layer.this, R.layout.list_education_program_item, dataArrayList);
        }

        listView.setAdapter(listAdapter);
    }
}