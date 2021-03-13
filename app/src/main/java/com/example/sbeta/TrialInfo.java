package com.example.sbeta;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TrialInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String currentUserName = getIntent().getStringExtra("UserName");
        String currentLocation = getIntent().getStringExtra("Location");
        String currentData = getIntent().getStringExtra("Data");

        TextView userName = findViewById(R.id.user_name);
        userName.setText(currentUserName);

        TextView data = findViewById(R.id.data);
        data.setText((currentData));

        TextView location = findViewById(R.id.location);
        location.setText(currentLocation);
    }
}
