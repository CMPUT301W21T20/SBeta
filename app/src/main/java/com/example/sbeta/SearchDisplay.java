package com.example.sbeta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchDisplay extends AppCompatActivity {

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        Intent intent = getIntent();
        String keyword = intent.getStringExtra("keyword");

        ListView resultsList = findViewById(R.id.search_list);
        TextView title = findViewById(R.id.search_title);

        User testUser = new User("123456");
        Experiment testExper3 = new Experiment(testUser, "GALAXY s31", "published", "Phone");
        Experiment testExper4 = new Experiment(testUser, "GALAXY note40", "end", "Samsung");
        Experiment []experiments = {testExper3, testExper4};
        ArrayList<Experiment> resultDataList = new ArrayList<>(Arrays.asList(experiments));
//        ArrayList<Experiment> resultDataList = new ArrayList<>();
//        resultDataList.add(testExper3);
//        resultDataList.add(testExper4);

        ArrayAdapter<Experiment> resultAdapter = new CustomSearchList(this, resultDataList);

        resultsList.setAdapter(resultAdapter);


        //resultAdapter.notifyDataSetChanged();




    }
}
