package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class QRScanner extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scannerView;
    String information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_qr_code);
        scannerView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, scannerView);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        information = result.getText();
                        String array1[]= information.split(" ");
                        String result1 = array1[0];
                        String Lat = array1[1];
                        String Lng = array1[2];
                        String Uid = array1[3];
                        String exp = array1[4];
                        double data = Double.parseDouble(result1);


                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        HashMap<String, Object> trial_to_add = new HashMap<>();
                        DocumentReference experiment = db.collection("experiments").document(exp);
                        CollectionReference trials = experiment.collection("trials");
                        trial_to_add.put("Lat", Lat);
                        trial_to_add.put("Lng", Lng);
                        trial_to_add.put("user id", Uid);
                        trial_to_add.put("result", data);
                        Date date = Calendar.getInstance().getTime();
                        trial_to_add.put("date", date);
                        trial_to_add.put("trial id", (int) (1000));
                        trials.document("trial " + (int) (1000)).set(trial_to_add);
                        finish();


                        Toast.makeText(QRScanner.this, exp, Toast.LENGTH_LONG).show();
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