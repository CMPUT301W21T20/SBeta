package com.example.sbeta;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExperimentListActivity extends AppCompatActivity implements AddNewExperimentFragment.OnFragmentInteractionListener{

    public ListView Experiment_list_view;
    public ArrayAdapter<Experiment> Experiment_ArrayAdapter;
    public ArrayList<Experiment> Experiment_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_list_dayu);


        Experiment_list_view = findViewById(R.id.experiment_list);
        Experiment_list = new ArrayList<>();

        // test
        Experiment test = new Experiment();
        test.description = "test";
        Experiment_list.add(test);
        // test

        Experiment_ArrayAdapter = new ExpArrayAdapter(this, Experiment_list);
        Experiment_list_view.setAdapter(Experiment_ArrayAdapter);

        final FloatingActionButton addExperimentButton = findViewById(R.id.add_experiment_button);
        addExperimentButton.setOnClickListener(v -> new AddNewExperimentFragment().show(getSupportFragmentManager(), "ADD_EXPERIMENT"));

    }

    public void onOkPressed(Experiment new_experiment){
        Experiment_ArrayAdapter.add(new_experiment);
    }
}