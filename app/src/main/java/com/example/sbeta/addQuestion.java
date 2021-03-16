package com.example.sbeta;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class addQuestion extends AppCompatActivity {
    TextView addQuesTittle;
    EditText quesContent;
    Button quesCancel;
    Button quesConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_questions);
        final String TAG = "Sample";

        Intent intent = getIntent();
        String title = intent.getStringExtra("chosenExperiment");

        addQuesTittle = findViewById(R.id.newQues);
        quesContent = findViewById(R.id.quesText);
        quesCancel = findViewById(R.id.quesCancel);
        quesConfirm = findViewById(R.id.quesOk);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("experiments");

        quesCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        quesConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = quesContent.getText().toString();

                collectionReference.document("questions");


            }
        });

    }
}