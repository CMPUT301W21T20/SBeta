package com.example.sbeta;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

/**
 * this will show the histogram, statistic, dot chart of the experiment
 */

public class StatActivity extends AppCompatActivity {

    private ArrayList<ChartDot> chartDots;
    private ArrayList<Trial> trials;
    private BarChart histogram_chart;
    private Description histogram_desc;
    private ArrayList<IBarDataSet> fullBarDataSet;
    String type1 = "Count-based";
    String type2 = "Binomial trials";
    String type3 = "Non-negative integer counts";
    String type4 = "Measurement trials";
    String selectedType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_page);
        selectedType = getIntent().getStringExtra("ExperimentType");

        // directly use the arrayList from TrialActivity, maybe better to serialize and pass;
        trials = TrialActivity.trialDataList;
        chartDots = new ArrayList<>();


        histogram_chart = findViewById(R.id.histogram);
        fullBarDataSet = new ArrayList<>(); // the final data set for the chart
        if (selectedType.equals(type1)) {
            Type134Calculator();
        }
        else if (selectedType.equals(type2)) {
            Type2Calculator();
        }
        else if (selectedType.equals(type3)) {
            Type134Calculator();
        }
        else if (selectedType.equals(type4)) {
            Type134Calculator();

        }
        else {
        }



        histogram_desc = new Description();
        histogram_desc.setText("");


        BarData histogram_data = new BarData(fullBarDataSet);
        histogram_chart.setData(histogram_data);
        histogram_chart.setDescription(histogram_desc);
        histogram_chart.getAxisLeft().setDrawAxisLine(false);
    }

    private void Type134Calculator(){
        for (Trial singleTrial : trials) {
            boolean isContained = false;
            double value = singleTrial.getResult();
            for (ChartDot singleDot : chartDots) {
                if (singleDot.getX() == value) {
                    isContained = true;
                    singleDot.increamentY();
                    break;
                }
            }
            if (!isContained) {
                chartDots.add(new ChartDot(value, 1));
            }
        }

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.BLACK);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);

        int index = 0;
        int colorCounter = 0;
        for (ChartDot singleDot : chartDots) {
            Double x = singleDot.getX();
            Double y = singleDot.getY();
            ArrayList<BarEntry> valueList = new ArrayList<>();
            valueList.add(new BarEntry(index, y.floatValue()));
            BarDataSet histogram_dataSet = new BarDataSet(valueList, "Result: " + x);
            histogram_dataSet.setColor(colors.get(colorCounter));
            fullBarDataSet.add(histogram_dataSet);
            index++;
            colorCounter = (colorCounter + 1) % 5;

        }
    }

    private void Type2Calculator(){
        chartDots.add(new ChartDot(0, 0));
        chartDots.add(new ChartDot(1, 0));
        for (Trial singleTrial : trials) {
            double value = singleTrial.getResult();
            if (value == 0){
                chartDots.get(0).increamentY();
            }
            else {
                chartDots.get(1).increamentY();
            }
        }

        int index = 0;
        for (ChartDot singleDot : chartDots) {
            Double x = singleDot.getX();
            Double y = singleDot.getY();
            ArrayList<BarEntry> valueList = new ArrayList<>();
            valueList.add(new BarEntry(index, y.floatValue()));

            BarDataSet histogram_dataSet = new BarDataSet(valueList, "");
            String bool;
            if (x == 0) {
                bool = "false";
                histogram_dataSet.setColor(Color.RED);
            }
            else {
                bool = "true";
                histogram_dataSet.setColor(Color.BLUE);
            }
            histogram_dataSet.setLabel(bool);
            fullBarDataSet.add(histogram_dataSet);
            index++;
        }

    }

}
