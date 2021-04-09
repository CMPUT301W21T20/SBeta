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
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
/**
 * This activity enables adding count trials
 * including counts, non-negative integer counts,
 * and measurement trials
 * where the user chooses enters a number
 * If the corresponding experiment requires location,
 * it enforces the user to provide a location
 * otherwise, the location is optional
 */
public class AddCountTrial extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_count_trial);

        //Button selectLocation = findViewById(R.id.location_count);
        EditText data = findViewById(R.id.count_data);
        TextView expName = findViewById(R.id.display_exp_name);
        TextView userName = findViewById(R.id.user_name_count);
        Button confirmButton = findViewById(R.id.confirm_button_count);
        Button cancelButton = findViewById(R.id.cancel_button_count);

        String expType = getIntent().getStringExtra("ExperimentType");
        String userId = getIntent().getStringExtra("userID");
        String name = getIntent().getStringExtra("userName");
        String locationRequired = getIntent().getStringExtra("locationRequired");
        String title = getIntent().getStringExtra("chosenExperiment");
        int trialId = getIntent().getIntExtra("trial number", 0);
        expName.setText(title);

        userName.setText(name);

        if (expType.equals("Count-based")) {
            this.setTitle("Count-based Experiment");
        }
        else if (expType.equals("Non-negative integer counts")) {
            this.setTitle("Non-negative Integer experiment");
        }
        else {
            this.setTitle("Measurement experiment");
        }

        final String[] trialLat = {"null"};
        final String[] trialLng = {"null"};
        //selectLocation.setOnClickListener();
        String apiKey = getString(R.string.api_key);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        PlacesClient placesClient = Places.createClient(this);
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_count);
        if (locationRequired.equals("true")) {
            autocompleteFragment.setHint("Your location is required");
        } else {
            autocompleteFragment.setHint("Your location is not required");
        }
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i("Success", "Place: " + place.getName() + ", " + place.getId());
                trialLat[0] = String.valueOf(place.getLatLng().latitude);
                trialLng[0] = String.valueOf(place.getLatLng().longitude);
            }

            @Override
            public void onError(Status status) {
                Log.i("Failure!", "An error occurred: " + status);
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("experiments");
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
                String resultStr = data.getText().toString();
                Date date = Calendar.getInstance().getTime();

                if (!resultStr.equals("")) {

                    double result = Double.parseDouble(resultStr);
                    trial_to_add.put("user id", userId);
                    //trial_to_add.put("location", location);
                    trial_to_add.put("result", result);
                    trial_to_add.put("date", date);
                    trial_to_add.put("trial id", trialId);

                    String trialName = "trial " + trialId;
                    if (locationRequired.equals("true") && trialLat[0].equals("null") && trialLng[0].equals("null")) {
                        Toast.makeText(AddCountTrial.this, "Please select a location!", Toast.LENGTH_SHORT).show();
                    }
                    else if (expType.equals("Count-based") && (result != (int) result || result < 0)) {
                        Toast.makeText(AddCountTrial.this,"Count should be non-negative integer", Toast.LENGTH_SHORT).show();
                    }
                    else if (expType.equals("Non-negative integer counts") && (result != (int) result || result < 0)) {
                        Toast.makeText(AddCountTrial.this,"Non-negative integer Count should be a non-negative integer", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        trial_to_add.put("lat", trialLat[0]);
                        trial_to_add.put("lng", trialLng[0]);
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
                } else {
                Toast.makeText(AddCountTrial.this, "Please enter a result", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


}