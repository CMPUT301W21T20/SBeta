package com.example.sbeta;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TrialActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    ListView trialList;
    public static TrialAdapter trialAdapter;
    public static ArrayList<Trial> trialDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //String []dates = {};
        //String []descriptions = {};

        String []trials = {};

        trialList = findViewById(R.id.trial_list);
        trialDataList = new ArrayList<>();

        /*
        for (int i=0; i<trials.length;i++){
            trialDataList.add(new Trial());
        }
        */

        trialAdapter = new TrialAdapter(this, R.layout.trial_content, trialDataList);
        trialList.setAdapter(trialAdapter);

        Button operationButton = (Button) findViewById(R.id.operation_button);
        operationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(TrialActivity.this, v);
                popup.setOnMenuItemClickListener(TrialActivity.this);
                popup.inflate(R.menu.menu_trial);
                popup.show();
            }
        });

        Button addButton = (Button) findViewById(R.id.add_trial_button);
        operationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(TrialActivity.this, v);
                popup.setOnMenuItemClickListener(TrialActivity.this);
                popup.inflate(R.menu.add_option);
                popup.show();
            }
        });


        trialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trial trial = trialDataList.get(position);
                Intent intent = new Intent(TrialActivity.this, TrialInfo.class);
                intent.putExtra("userName", trialDataList.get(position).getUserName());
                intent.putExtra("Data", trialDataList.get(position).getData());
                intent.putExtra("Location", trialDataList.get(position).getLocation());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.edit_trial:
                // do your code
                return true;
            case R.id.statistics:
                // do your code
                return true;
            case R.id.ignore:
                // do your code
                return true;
            case R.id.questions:
                // do your code
                return true;
            case R.id.unpublish_exp:
                // do your code
                return true;
            case R.id.end_exp:
                // do your code
                return true;
            case R.id.scan_barcode:
                return true;
            default:
                return false;
        }
    }
}