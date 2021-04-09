package com.example.sbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Arrays;
import java.util.Scanner;

public class GenerateBioQR extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.qrcode_binomial);
            CheckBox success = findViewById(R.id.success);
            CheckBox failure = findViewById(R.id.failure);
            Button generateButton = findViewById(R.id.generate_qr_bio);

            ImageView qrImage = findViewById(R.id.qr_bio_image);
            Button scanButton = findViewById(R.id.scan_image);
            String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";

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
            String locationRequired = getIntent().getStringExtra("locationRequired");
            final String[] trialLat = {"null"};
            final String[] trialLng = {"null"};
            String userId = getIntent().getStringExtra("userID");
            String expName = getIntent().getStringExtra("chosenExperiment");

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


            generateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get result
                    double result;
                    if(success.isChecked()) {
                        result = 1;
                    }
                    else {
                        result = 0;
                    }
                    //get location

                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(""+result+" "+trialLat[0] + " " + trialLng[0]+ " "+ userId+ " "+ expName , BarcodeFormat.QR_CODE, 200, 200);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        qrImage.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                }
            });

            scanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GenerateBioQR.this, QRScanner.class);
                    startActivity(intent);
                }
            });

    }
}