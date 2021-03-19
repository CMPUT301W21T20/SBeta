package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddCountTrial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_count_trial);

        Button selectLocation = findViewById(R.id.location_count);
        EditText data = findViewById(R.id.count_data);
        TextView userName = findViewById(R.id.user_name_count);
        Button confirmButton = findViewById(R.id.confirm_button_count);
        Button cancelButton = findViewById(R.id.cancel_button_count);
        String userId = getIntent().getStringExtra("userID");
        String name = getIntent().getStringExtra("userName");

        userName.setText(name);
        /*

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

                boolean result;
                if (success.isChecked()) {
                    result = true;
                }
                else {
                    result = false;
                }

                trial_to_add.put("user id", userId);
                //trial_to_add.put("location", location);
                trial_to_add.put("result", result);
                //trial_to_add.put("date", date);
                trial_to_add.put("trial id", trialId);

                String trialName = "trial "+ trialId;

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
        });

         */

    }


}