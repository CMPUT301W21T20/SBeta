package com.example.sbeta;

// This is an activity that show the search results

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This is an activity that show the search results
 */
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
        String userID = intent.getStringExtra("userID");
        String loginUser = intent.getStringExtra("userName");

        resultsList = findViewById(R.id.search_list);
        title = findViewById(R.id.search_title);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("experiments");
        
        resultDataList = new ArrayList<>();
        resultAdapter = new CustomSearchList(this, resultDataList);
        resultsList.setAdapter(resultAdapter);
        /*
        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchDisplay.this, TrialActivity.class);
                startActivity(intent);
            }
        });

         */

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
                    Boolean isEnd = doc.getBoolean("isEnded");
                    Boolean isPublished = doc.getBoolean("isPublished");
                    Boolean locationRequired = doc.getBoolean("locationRequired");
                    //Integer minTrials = (Integer) doc.get("minTrials");
                    //Integer minTrials = 1;
                    long minTrials = (long) doc.getData().get("minTrials");
                    String userName = (String) doc.getData().get("userID");

                    if (name.toLowerCase().contains(keyword.toLowerCase()) ||
                            Objects.requireNonNull(userName).toLowerCase().contains(keyword.toLowerCase()) ||
                            Objects.requireNonNull(type).toLowerCase().contains(keyword.toLowerCase()) ||
                            Objects.requireNonNull(description).toLowerCase().contains(keyword.toLowerCase())) {
                        if (isPublished == Boolean.TRUE) {
                            resultDataList.add(new Experiment(description, isEnd, isPublished, minTrials, locationRequired, type, name, userName));
                        }
                    }

                    //resultDataList.add(new Experiment(description, isEnd, isPublished, minTrials, locationRequired, type, name, userId));
                }
                //dataList.add(new Experiment("description", true, true, 1, false, "type", "namde", "userId"));
                resultAdapter.notifyDataSetChanged();
            }
        });

        //enter trial list
        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = resultDataList.get(position).getName();
                Intent intent = new Intent(SearchDisplay.this, TrialActivity.class);
                intent.putExtra("ExperimentType", resultDataList.get(position).getExperimentType());
                intent.putExtra("userID", userID);
                intent.putExtra("chosenExperiment", name);
                intent.putExtra("isEnd", resultDataList.get(position).getEnded().toString());
                intent.putExtra("userName", loginUser);
                intent.putExtra("locationRequired", resultDataList.get(position).getLocationRequired().toString());
                DocumentReference userDocReference = db.collection("users").document(resultDataList.get(position).getUserId());
                int minTrials = (int) resultDataList.get(position).getMinTrials();
                intent.putExtra("minTrials", Integer.toString(minTrials));

                //get experiment owner name
                userDocReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                String owner = (String) document.get("userName");
                                intent.putExtra("owner", owner);
                                startActivity(intent);}
                        }
                    }
                });


                

            }
        });
    }
}
