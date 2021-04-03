package com.example.sbeta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class showStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String ExpName;
        TextView welcome;
        Intent intent;

        setContentView(R.layout.show_stat_activity);

        intent=getIntent();
        ExpName=intent.getStringExtra("chosenExperiment");
        welcome=findViewById(R.id.welcome_to_stats);
        welcome.setText("welcome to the diagrams");
    }

}
