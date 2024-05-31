package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapter.CollectionTuitionFeesDAO;
import com.example.app.model.CollectionTuitionFeesDTO;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Activity_Total_Revenue extends AppCompatActivity {
    LineChart lineChart;
    List<String> xValues;
    Button detailBtn, printBtn;
    ImageButton returnFrag;
    String[] yearItem = {"2020", "2021", "2022","2023","2024"};
    AutoCompleteTextView year;
    ArrayAdapter<String> yearAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_revenue);

        returnFrag = findViewById(R.id.return_to_frag_btn);
        returnFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        lineChart = findViewById(R.id.chart);

        Description description = new Description();
        description.setPosition(150f, 15f);
        description.setText("Chục triệu đồng");
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        xValues = Arrays.asList("","1","2","3","4","5","6","7","8","9","10","11","12");

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setLabelCount(13);
        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMaximum(10f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisLineWidth(0.2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0,0f));
/*       entries.add(new Entry(1,1f));
        entries.add(new Entry(2,2f));
        entries.add(new Entry(3,3f));
        entries.add(new Entry(4,0f));
        entries.add(new Entry(5,1f));
        entries.add(new Entry(6,2f));
        entries.add(new Entry(7,3f));
        entries.add(new Entry(8,0f));
        entries.add(new Entry(9,1f));
        entries.add(new Entry(10,2f));
        entries.add(new Entry(11,3f));
        entries.add(new Entry(12,3f));*/

        Map<Integer, Integer> collectingTuition = CollectionTuitionFeesDAO
                .getInstance(Activity_Total_Revenue.this)
                .SelectCollectionTuitionFeesByYear(Activity_Total_Revenue.this, "2024");
        Log.d("List collecting revenue: ", collectingTuition.toString());

        for (Integer value : collectingTuition.keySet()) {
            entries.add(new Entry(value, (collectingTuition.get(value) / 1000000000)/1.0f));
        }

        year = findViewById(R.id.year);
        year.setText("2024");
        yearAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, yearItem);
        year.setAdapter(yearAdapter);
        year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                Map<Integer, Integer> collectingTuition = CollectionTuitionFeesDAO
                        .getInstance(Activity_Total_Revenue.this)
                        .SelectCollectionTuitionFeesByYear(Activity_Total_Revenue.this, item);
                Log.d("List collecting revenue: ", collectingTuition.toString());

                for (Integer value : collectingTuition.keySet()) {
                    entries.add (new Entry(value, (collectingTuition.get(value) / 100000000)/1.0f));
                }
                LineDataSet dataSet = new LineDataSet(entries, "");
                dataSet.setColor(Color.RED);
            }
        });

        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setColor(Color.RED);

        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);

        lineChart.invalidate();

        detailBtn = findViewById(R.id.detailBtn);
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Total_Revenue.this, Activity_List_Revenue.class);
                intent.putExtra("message", "Thống kê doanh thu");
                startActivity(intent);
            }
        });

        printBtn = findViewById(R.id.printBtn);
    }
}