package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

/**
 * This is an activity that allow to add reply
 */
public class addReply extends AppCompatActivity {
    TextView addReplyTittle;
    EditText replyContent;
    Button replyCancel;
    Button replyConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reply);
        final String TAG = "Sample";

        Intent intent = getIntent();
        String experiment = intent.getStringExtra("chosenExperiment");
        String questionName = intent.getStringExtra("QuestionName");
        String questionContent = intent.getStringExtra("QuestionContent");
        String title = "Title: " + questionName + "\n\nBody: " + questionContent;

        addReplyTittle = findViewById(R.id.replyQues);
        addReplyTittle.setText(title);
        replyContent = findViewById(R.id.replyText);
        replyCancel = findViewById(R.id.replyCancel);
        replyConfirm = findViewById(R.id.replyOk);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("experiments");
        final DocumentReference chosenExperiment = collectionReference.document(experiment);
        final CollectionReference questions = chosenExperiment.collection("questions");
        final DocumentReference question = questions.document(questionName);
        final CollectionReference replies = question.collection("replies");

        replyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        replyConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (replyContent.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(addReply.this);
                    builder.setMessage("Cannot be empty!");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    builder.create().show();
                } else {
                    HashMap<String, Object> reply_to_add = new HashMap<>();
                    String content = replyContent.getText().toString();
                    reply_to_add.put("content", content);

                    replies
                            .add(reply_to_add)
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("false message", "reply cannot be added" + e.toString());
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("success message", "reply added successfully");
                                }
                            });
                    onBackPressed();
                    // String content = quesContent.getText().toString();
                }
            }
        });

    }
}
