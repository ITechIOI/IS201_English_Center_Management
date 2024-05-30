package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

import com.example.app.R;
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

public class Activity_Total_Revenue extends AppCompatActivity {
    LineChart lineChart;
    List<String> xValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_revenue);

        lineChart = findViewById(R.id.chart);

        Description description = new Description();
        description.setPosition(100f, 15f);
        description.setText("Chục triệu đồng");
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        xValues = Arrays.asList("", "Quý 1", "Quý 2", "Quý 3", "Quý 4");

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setLabelCount(5);
        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMaximum(10f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisLineWidth(1f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0,0f));
        entries.add(new Entry(1,1f));
        entries.add(new Entry(2,2f));
        entries.add(new Entry(3,3f));
        entries.add(new Entry(4,2f));

        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setColor(Color.RED);

        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);

        lineChart.invalidate();
    }
}