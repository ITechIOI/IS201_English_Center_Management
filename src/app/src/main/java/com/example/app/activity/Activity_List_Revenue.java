package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.app.R;
import com.example.app.model.ClassCollectingFees;
import com.example.app.model.List_Adapter;

import java.util.ArrayList;

public class Activity_List_Revenue extends AppCompatActivity {
    private List_Adapter listAdapter;
    private ListView listView;
    private ArrayList<Object> dataArrayList;
    private ImageButton returnBtn;
    String[] yearItem = {"2020", "2021", "2022","2023","2024"};
    String[] monthItem = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    AutoCompleteTextView year, month;
    ArrayAdapter<String> yearAdapter, monthAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_revenue);

        listView = findViewById(R.id.notification_listview);
        returnBtn = findViewById(R.id.return_to_frag_btn);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        year = findViewById(R.id.year);
        yearAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, yearItem);
        year.setAdapter(yearAdapter);
        year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        month = findViewById(R.id.month);
        monthAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, monthItem);
        month.setAdapter(monthAdapter);
        month.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        dataArrayList = new ArrayList<>();
        dataArrayList.add(new ClassCollectingFees("1","1","1","1"));
        listAdapter = new List_Adapter(Activity_List_Revenue.this, R.layout.list_collecting_fees_item, dataArrayList);
        listView.setAdapter(listAdapter);

    }
}