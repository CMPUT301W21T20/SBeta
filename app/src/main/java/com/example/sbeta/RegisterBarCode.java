package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class RegisterBarCode extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView scannerView;
    String information;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bar_code);

        CheckBox success = findViewById(R.id.success223);
        CheckBox failure = findViewById(R.id.failure223);
        String expType = getIntent().getStringExtra("ExperimentType");
        String userId = getIntent().getStringExtra("userID");
        String expName = getIntent().getStringExtra("chosenExperiment");
        String locationRequired = getIntent().getStringExtra("locationRequired");
        final String[] trialLat = {"null"};
        final String[] trialLng = {"null"};

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

        scannerView = findViewById(R.id.scanner1);
        codeScanner = new CodeScanner(this, scannerView);

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

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        information = result.getText();
                        Date date = Calendar.getInstance().getTime();
                        double result1;
                        if(success.isChecked()) {
                            result1 = 1;
                        }
                        else {
                            result1 = 0;
                        }

                        if (locationRequired.equals("true") && trialLat[0].equals("null") && trialLng[0].equals("null")) {
                            Log.e("Location", "Location not provided but required");
                            Log.e("Location", "Required: " + locationRequired);
                            Toast.makeText(getApplicationContext(), "Location empty!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            double num = 0 ;

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            final CollectionReference collectionReference = db.collection("barcode");
                            HashMap<String, Object> to_add = new HashMap<>();
                            to_add.put("data",result1);
                            to_add.put("experiment", expName);
                            to_add.put("date", date);
                            to_add.put ("User", userId);
                            to_add.put("Lat", trialLat[0]);
                            to_add.put("Lng", trialLng[0]);
                            to_add.put("num", num);
                            Toast.makeText(RegisterBarCode.this, "SUCCESS", Toast.LENGTH_LONG).show();
                            collectionReference.document(information).set(to_add);
                            finish();}




                    }
                });

            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
}