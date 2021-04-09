package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.Result;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * This class ennables scannig a barcode and add the
 * correspoding result to the database
 */
public class ScanBarCode extends AppCompatActivity {

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

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        final CollectionReference collectionReference = db.collection("barcode");
                        DocumentReference docRef = collectionReference.document(information);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {

                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Toast.makeText(ScanBarCode.this, "Success", Toast.LENGTH_LONG).show();


                                        String Lat = (String) document.getData().get("Lat");

                                        String Lng = (String) document.getData().get("Lng");
                                        String UserId = (String) document.getData().get("User");
                                        double data  = (Double) document.getData().get("data");
                                        Date date = Calendar.getInstance().getTime();
                                        String Exp = (String) document.getData().get("experiment");
                                        Random rand = new Random();
                                        int num = rand.nextInt(10000);






                                        DocumentReference experiment = db.collection("experiments").document(Exp);
                                        CollectionReference trials = experiment.collection("trials");
                                        HashMap<String, Object> trial_to_add = new HashMap<>();
                                        trial_to_add.put("lat", Lat);
                                        trial_to_add.put("lng", Lng);
                                        trial_to_add.put("user id", UserId);
                                        trial_to_add.put("result", data);
                                        trial_to_add.put("date", date);
                                        trial_to_add.put("trial id", (int) ( num));

                                        Toast.makeText(ScanBarCode.this, Exp, Toast.LENGTH_SHORT).show();


                                        trials.document("trial " + (int) (num)).set(trial_to_add);

                                        finish();





                                    }
                                    else{

                                    }
                                }
                            }
                        });
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