package com.example.sbeta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class showQuestion extends AppCompatActivity {
    ArrayList<String> questionDataList;
    ListView questionList;
    TextView questionListTittle;
    ArrayAdapter<String> questionAdapter;
    FloatingActionButton addQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_list);
        final String TAG = "Sample";

        Intent intent = getIntent();
        String title = intent.getStringExtra("chosenExperiment");

        questionListTittle = findViewById(R.id.ques_list_tittle);
        questionListTittle.setText(title);
        questionList = findViewById(R.id.question_list);
        addQuestion = findViewById(R.id.add_question_button);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("experiments");
        final DocumentReference experiment = collectionReference.document(title);
        final CollectionReference questions = experiment.collection("questions");

        questionDataList = new ArrayList<>();
        questionAdapter = new CustomQuestionList(this, questionDataList);
        questionList.setAdapter(questionAdapter);

        // get data from firebase
        questions.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                questionDataList.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                    Log.d(TAG, String.valueOf(doc.getData().get("content")));
                    String name = doc.getId();
                    questionDataList.add(name);
                }
                questionAdapter.notifyDataSetChanged();
            }
        });

        // add question
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showQuestion.this, addQuestion.class);
                intent.putExtra("chosenExperiment", title);
                startActivity(intent);
            }
        });


    }
}