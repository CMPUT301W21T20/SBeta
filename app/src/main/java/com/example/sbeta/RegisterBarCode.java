package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

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
        EditText location = findViewById(R.id.location223);

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
                        String result1;
                        if(success.isChecked()) {
                            result1 = "success";
                        }
                        else {
                            result1 = "failure";
                        }

                        String locationStr = location.getText().toString().trim();


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