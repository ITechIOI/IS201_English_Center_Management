package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.app.R;
import com.example.app.adapter.ClassroomDAO;
import com.example.app.adapter.CollectionTuitionFeesDAO;
import com.example.app.adapter.ScheduleDAO;
import com.example.app.model.ClassroomDTO;
import com.example.app.model.CollectionTuitionFeesDTO;
import com.example.app.model.List_Adapter;
import com.example.app.model.ScheduleDTO;

import java.util.ArrayList;
import java.util.List;

public class Activity_List_Room extends AppCompatActivity {
    private List_Adapter listAdapter;
    private ListView listView;
    private ArrayList<Object> dataArrayList;
    private ImageButton returnBtn;
    String[] roomItem = {"Đau ẻ tàn bạo", "Đau ẻ thảm khốc", "Chào Loan nhé", "=)))"};
    AutoCompleteTextView room;
    ArrayAdapter<String> roomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room);

        listView = findViewById(R.id.notification_listview);
        returnBtn = findViewById(R.id.return_to_frag_btn);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*List<ClassroomDTO> listClassroom = ClassroomDAO.getInstance(Activity_List_Room.this)
                .SelectClassroom(Activity_List_Room.this, "STATUS = ?",
                        new String[] {"0"});
        for (int i = 0; i < listClassroom.size(); i++) {
            roomItem[i] = listClassroom.get(i).getName();
            Log.d("List room in english center: ", roomItem[i]);
        }

        List<ScheduleDTO> schedule = ScheduleDAO.getInstance(Activity_List_Room.this)
                .SelectSchedule(Activity_List_Room.this, "STATUS = 0",
                        new String[] {"0"});
        for (int i = 0; i < schedule.size(); i++) {

        }*/

        dataArrayList = new ArrayList<>();
        dataArrayList.add(new ScheduleDTO("1","1","1","1","1","1"));
        listAdapter = new List_Adapter(Activity_List_Room.this, R.layout.list_schedule_for_manager_item, dataArrayList);
        listView.setAdapter(listAdapter);

        room = findViewById(R.id.room);
        roomAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, roomItem);
        room.setAdapter(roomAdapter);
        room.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });
    }
}