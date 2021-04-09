package com.example.sbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * This activity shows the details of a trial
 */
public class TrialInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_info);
        Intent intent = getIntent();

        TextView date = findViewById(R.id.date);
        TextView trialName = findViewById(R.id.trial_num);
        TextView expName = findViewById(R.id.description);
        TextView expOwner = findViewById(R.id.experiment_owner);
        TextView currentUser = findViewById(R.id.trial_provider);
        TextView result = findViewById(R.id.data);

        date.setText(intent.getStringExtra("date"));
        trialName.setText(intent.getStringExtra("chosenTrial"));
        expName.setText(intent.getStringExtra("correspondingExp"));
        expOwner.setText(intent.getStringExtra("owner"));
        currentUser.setText(intent.getStringExtra("user"));
        Double data = intent.getDoubleExtra("data", 0);
        String expType = intent.getStringExtra("expType");
        if (expType.equals("Binomial trials")) {
            if (data == 0) {result.setText("Failure");}
            else {result.setText("Success");}
        }
        else {result.setText(data.toString());}
    }
}