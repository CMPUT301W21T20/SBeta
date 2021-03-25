package com.example.sbeta;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class StatActivity extends AppCompatActivity {

    private GraphView histogram;
    private BarGraphSeries<DataPoint> histogram_series;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        histogram = findViewById(R.id.histogram);
        histogram_series = new BarGraphSeries<>();
        histogram_series.appendData(new DataPoint(0, 10), false, 100);
        histogram_series.appendData(new DataPoint(1, 5), false, 100);
        histogram_series.appendData(new DataPoint(2, 5), false, 100);
        histogram_series.appendData(new DataPoint(3, 7), false, 100);
        histogram_series.appendData(new DataPoint(4, 6), true, 100);


        histogram.addSeries(histogram_series);
        histogram_series.setSpacing(50);
    }
}
