package com.example.sbeta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SearchDisplay extends AppCompatActivity {
    ArrayList<Experiment> resultDataList;
    ListView resultsList;
    TextView title;
    ArrayAdapter<Experiment> resultAdapter;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        final String TAG = "Sample";

        Intent intent = getIntent();
        String keyword = intent.getStringExtra("keyword");

        resultsList = findViewById(R.id.search_list);
        title = findViewById(R.id.search_title);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("experiments");
        
        resultDataList = new ArrayList<>();
        resultAdapter = new CustomSearchList(this, resultDataList);
        resultsList.setAdapter(resultAdapter);

//        User testUser = new User("123456");
//        Experiment testExper3 = new Experiment(testUser, "GALAXY s31", "published", "Phone");
//        Experiment testExper4 = new Experiment(testUser, "GALAXY note40", "end", "Samsung");
//        Experiment []experiments = {testExper3, testExper4};
//        ArrayList<Experiment> resultDataList = new ArrayList<>(Arrays.asList(experiments));

//        db.collection("experiments")
//                .startAt(keyword)
//                .endAt(keyword+"\uf8ff")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                resultDataList.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                    Log.d(TAG, String.valueOf(doc.getData().get("description")));
                    String name = doc.getId();
                    String description = (String) doc.getData().get("description");
                    String type = (String) doc.getData().get("experimentType");
                    Boolean isEnd = (Boolean) doc.getBoolean("isEnded");
                    Boolean isPublished = (Boolean) doc.getBoolean("isPublished");
                    Boolean locationRequired = (Boolean) doc.getBoolean("locationRequired");
                    //Integer minTrials = (Integer) doc.get("minTrials");
                    Integer minTrials = 1;
                    String userId = (String) doc.getData().get("userName");

                    if (name.toLowerCase().contains(keyword.toLowerCase()) ||
                            Objects.requireNonNull(userId).toLowerCase().contains(keyword.toLowerCase()) ||
                            Objects.requireNonNull(type).toLowerCase().contains(keyword.toLowerCase()) ||
                            Objects.requireNonNull(description).toLowerCase().contains(keyword.toLowerCase())) {
                        resultDataList.add(new Experiment(description, isEnd, isPublished, minTrials, locationRequired, type, name, userId));
                    }

                    //resultDataList.add(new Experiment(description, isEnd, isPublished, minTrials, locationRequired, type, name, userId));
                }
                //dataList.add(new Experiment("description", true, true, 1, false, "type", "namde", "userId"));
                resultAdapter.notifyDataSetChanged();
            }
        });
    }
}
