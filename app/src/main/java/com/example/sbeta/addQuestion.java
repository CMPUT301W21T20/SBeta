package com.example.sbeta;

// This is an activity that allow to add question

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

/**
 * This is an activity that allow to add question
 */
public class addQuestion extends AppCompatActivity {
    TextView addQuesTittle;
    EditText questionName;
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
        questionName = findViewById(R.id.questionName_context);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("experiments");
        final DocumentReference experiment = collectionReference.document(title);

        quesCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        quesConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quesContent.getText().toString().equals("") || questionName.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(addQuestion.this);
                    builder.setMessage("Cannot be empty!");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    builder.create().show();
                } else {
                    CollectionReference experiments = experiment.collection("questions");
                    HashMap<String, Object> question_to_add = new HashMap<>();
                    String name = questionName.getText().toString();
                    String content = quesContent.getText().toString();

                    question_to_add.put("content", content);

                    experiments
                            .document(name)
                            .set(question_to_add)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("false message", "question cannot be added" + e.toString());
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("success message", "question added successfully");
                                }
                            });
                    onBackPressed();
                    // String content = quesContent.getText().toString();
                }
            }
        });

    }
}
