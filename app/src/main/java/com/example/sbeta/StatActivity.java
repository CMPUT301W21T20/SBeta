package com.example.sbeta;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;


public class StatActivity extends AppCompatActivity {

    private BarChart histogram_chart;
    private Description histogram_desc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_page);

        histogram_chart = findViewById(R.id.histogram);
        ArrayList<BarEntry> valueList = new ArrayList<>();
        valueList.add(new BarEntry(1, 36));
        valueList.add(new BarEntry(2, 85));
        valueList.add(new BarEntry(3, 20));
        valueList.add(new BarEntry(4, 66));
        valueList.add(new BarEntry(5, 32));
        valueList.add(new BarEntry(6, 25));
        valueList.add(new BarEntry(7, 56));

        histogram_desc = new Description();
        histogram_desc.setText("this is a histogram");

        BarDataSet histogram_dataSet;
        histogram_dataSet = new BarDataSet(valueList, "some number");
        BarData histogram_data = new BarData(histogram_dataSet);
        histogram_chart.setData(histogram_data);
        histogram_chart.setDescription(histogram_desc);



    }
}
