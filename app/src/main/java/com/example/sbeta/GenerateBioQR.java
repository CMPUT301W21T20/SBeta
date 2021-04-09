package com.example.sbeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Scanner;

public class GenerateBioQR extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.qrcode_binomial);
            CheckBox success = findViewById(R.id.success);
            CheckBox failure = findViewById(R.id.failure);
            Button generateButton = findViewById(R.id.generate_qr_bio);
            EditText location = findViewById(R.id.location);
            ImageView qrImage = findViewById(R.id.qr_bio_image);
            Button scanButton = findViewById(R.id.scan_image);
            String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";

            String userId = getIntent().getStringExtra("userID");
            String name = getIntent().getStringExtra("userName");
            String locationRequired = getIntent().getStringExtra("locationRequired");

            String title = getIntent().getStringExtra("chosenExperiment");
            int trialId = getIntent().getIntExtra("trial number", 0);

            this.setTitle("Binomial Experiment");

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


            generateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get result
                    String result;
                    if(success.isChecked()) {
                        result = "success";
                    }
                    else {
                        result = "failure";
                    }
                    //get location
                    String locationStr = location.getText().toString().trim();

                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode("result:" + result + "\nlocation:" + locationStr, BarcodeFormat.QR_CODE, 200, 200);
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