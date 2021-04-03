package com.example.sbeta;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * this activity will show the histogram, statistic, dot chart of the experiment
 */

public class StatActivity extends AppCompatActivity {

    private ArrayList<ChartDot> chartDots;
    private ArrayList<Trial> trials;
    private BarChart histogram_chart;
    private LineChart plots_chart;
    private View statistic_page;
    private Description histogram_desc;
    private ArrayList<IBarDataSet> fullBarDataSet;
    private ArrayList<Double> statDataList;
    private TextView quartiles;
    private TextView median;
    private TextView mean;
    private TextView stdDev;

    String type1 = "Count-based";
    String type2 = "Binomial trials";
    String type3 = "Non-negative integer counts";
    String type4 = "Measurement trials";
    String selectedType;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_page);
        selectedType = getIntent().getStringExtra("ExperimentType");

        // directly use the arrayList from TrialActivity, maybe better to serialize and pass;
        Button statButton = findViewById(R.id.statistic_button);
        Button histogramButton = findViewById(R.id.histogram_button);
        Button plotsButton = findViewById(R.id.plots_button);

        histogram_chart = findViewById(R.id.histogram);
        statistic_page = findViewById(R.id.statistic_result_page);
        plots_chart = findViewById(R.id.plotsChart);
        quartiles = findViewById(R.id.quartile);
        median = findViewById(R.id.median);
        mean= findViewById(R.id.mean);
        stdDev = findViewById(R.id.std_dev);


        statButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statistic_page.setVisibility(View.VISIBLE);
                histogram_chart.setVisibility(View.GONE);
                plots_chart.setVisibility(View.GONE);
            }
        });

        histogramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statistic_page.setVisibility(View.GONE);
                histogram_chart.setVisibility(View.VISIBLE);
                plots_chart.setVisibility(View.GONE);
            }
        });

        plotsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statistic_page.setVisibility(View.GONE);
                histogram_chart.setVisibility(View.GONE);
                plots_chart.setVisibility(View.VISIBLE);
            }
        });

        trials = TrialActivity.trialDataList;

        // statistic part //---------------------------------------------------------------
        statDataList = new ArrayList<>();
        for (Trial trialA : trials) {
            statDataList.add(trialA.getResult());
        }
        statDataList.sort(Comparator.naturalOrder());
        int dSize = statDataList.size();
        double q1, q2, q3;

        if (dSize == 0) {
            quartiles.append("  -");
            median.append("  -");
            mean.append("  -");
            stdDev.append("  -");

        } else {
            if (dSize % 2 == 0) {
                q2 = (statDataList.get((dSize / 2) - 1) + statDataList.get(dSize / 2)) / 2.0;
                if ((dSize / 2) % 2 == 0) {
                    q1 = (statDataList.get((dSize / 4) - 1) + statDataList.get(dSize / 4)) / 2.0;
                    q3 = (statDataList.get((dSize / 4) - 1 + (dSize / 2)) + statDataList.get((dSize / 4) + (dSize / 2))) / 2.0;
                } else {
                    q1 = statDataList.get((((dSize / 2) + 1) / 2) - 1);
                    q3 = statDataList.get(((((dSize / 2) + 1) / 2) + (dSize / 2)) - 1);
                }
            } else {  // dSize % 2 == 1
                if (dSize == 1) {
                    q1 = statDataList.get(0);
                    q2 = statDataList.get(0);
                    q3 = statDataList.get(0);
                } else {  // dSize != 1
                    q2 = statDataList.get(((dSize + 1) / 2) - 1);
                    if (((dSize - 1) / 2) % 2 == 0 ) {
                        q1 = (statDataList.get(((dSize - 1) / 4) - 1) + statDataList.get((dSize - 1) / 4)) / 2.0;
                        q3 = (statDataList.get(((dSize - 1) / 4) - 1 + ((dSize + 1) / 2)) + statDataList.get((dSize - 1) / 4 + ((dSize + 1) / 2))) / 2.0;
                    } else {
                        // to be check...
                        q1 = statDataList.get(((dSize + 1) / 4) - 1);
                        q3 = statDataList.get((((dSize + 1) / 4) - 1) + ((dSize + 1) / 2));
                    }
                }
            }

            DecimalFormat df = new DecimalFormat("#0.0");
            // Quartiles
            quartiles.append("\n " + df.format(q1) + " - " + df.format(q2) + " - " + df.format(q3));
            // Median
            median.append(" " + df.format(q2));
            // Mean
            double total = 0;
            for (double each : statDataList) {
                total += each;
            }
            double meanValue = total / dSize;
            mean.append(" " + df.format(meanValue));
            // Standard Deviation
            double sumDiffSquare = 0;
            for (double a : statDataList) {
                sumDiffSquare += (Math.pow((a - meanValue), 2));
            }
            double stdDevValue = Math.sqrt(sumDiffSquare / dSize);
            stdDev.append(" " + df.format(stdDevValue));

        }
        //---------------------------------------------------------------


        // histogram part //---------------------------------------------------------------
        chartDots = new ArrayList<>();

        histogram_chart = findViewById(R.id.histogram);
        fullBarDataSet = new ArrayList<>(); // the final data set for the chart
        if (selectedType.equals(type1)) {
            CountHistogramCalculator();
        }
        else if (selectedType.equals(type2)) {
            BinomialHistogramCalculator();
        }
        else if (selectedType.equals(type3)) {
            CountHistogramCalculator();
        }
        else if (selectedType.equals(type4)) {
            CountHistogramCalculator();
        }
        else {
        }

        histogram_desc = new Description();
        histogram_desc.setText("");

        BarData histogram_data = new BarData(fullBarDataSet);
        histogram_chart.setData(histogram_data);
        histogram_chart.setDescription(histogram_desc);
        histogram_chart.getAxisLeft().setDrawAxisLine(false);

        //---------------------------------------------------------------
    }

    private void CountHistogramCalculator(){
        for (Trial singleTrial : trials) {
            // if the trial's isIgnored is true, just ignore it
            if (singleTrial.getIsIgnored()){
                continue;
            }

            boolean isContained = false;
            double value = singleTrial.getResult();
            for (ChartDot singleDot : chartDots) {
                if (singleDot.getX() == value) {
                    isContained = true;
                    singleDot.increamentY();
                    break;
                }
            }
            // add as a new dot if no dots in the list has the same value with the current trial
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

    private void BinomialHistogramCalculator(){
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
