package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class RegisterBarCodeCountBase extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scannerView;
    String information;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bar_code_count_base);
        EditText count = findViewById(R.id.data223);
        EditText location = findViewById(R.id.location224);

        scannerView = findViewById(R.id.scanner2);
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScanner.startPreview();
            }
        });
        codeScanner = new CodeScanner(this, scannerView);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        information = result.getText();
                        String result1;


                        String locationStr = location.getText().toString().trim();
                        String data = count.getText().toString().trim();


                    }
                });

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
}