package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AddBinomialTrial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_binomial_trial);
        Button selectLocation = findViewById(R.id.location);
        CheckBox success = findViewById(R.id.success);
        CheckBox failure = findViewById(R.id.failure);
        TextView userName = findViewById(R.id.user_name_binomial);
        Button confirmButton = findViewById(R.id.confirm_button);
        Button cancelButton = findViewById(R.id.cancel_button);
        String userId = getIntent().getStringExtra("userID");
        String name = getIntent().getStringExtra("userName");
        String locationRequired = getIntent().getStringExtra("locationRequired");

        userName.setText(name);

        //selectLocation.setOnClickListener();

        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CompoundButton) v).isChecked()) {
                    failure.setChecked(false);
                }
                else {
                    failure.setChecked(true);
                }
            }
            });

        failure.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (((CompoundButton) v).isChecked()) {
                                               success.setChecked(false);

                                           } else {
                                               success.setChecked(true);
                                           }
                                       }
                                   });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("experiments");
        String title = getIntent().getStringExtra("chosenExperiment");
        int trialId = getIntent().getIntExtra("trial number", 0);
        final DocumentReference experiment = collectionReference.document(title);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference trials = experiment.collection("trials");
                HashMap<String, Object> trial_to_add = new HashMap<>();
                Date date = Calendar.getInstance().getTime();
                Location location = null;

                double result;
                if (success.isChecked()) {
                    result = 1;
                }
                else {
                    result = 0;
                }

                if (locationRequired.equals("true") && location == null) {
                    Toast.makeText(AddBinomialTrial.this, "Location is required", Toast.LENGTH_SHORT).show();
                }
                else {
                    trial_to_add.put("user id", userId);
                    //trial_to_add.put("location", location);
                    trial_to_add.put("result", result);
                    trial_to_add.put("date", date);
                    trial_to_add.put("trial id", trialId);

                    String trialName = "trial " + trialId;

                    trials
                            .document(trialName)
                            .set(trial_to_add)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("false message", "trial cannot be added" + e.toString());
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("success message", "trial added successfully");
                                }
                            });
                    onBackPressed();
                }
            }
        });

    }
}