package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class OwnPage extends AppCompatActivity {
    ArrayAdapter<Experiment> experAdapter;
    ArrayList<Experiment> dataList;
    ArrayList<String> expName = new ArrayList<>() ;
    ListView experList;
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        experAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_page);
        dataList = new ArrayList<>();

        experList = findViewById(R.id.exper_list2);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        experAdapter = new CustomSearchList(this, dataList);
        experList.setAdapter(experAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userID);
        CollectionReference Sub = docRef.collection("ownedExp");
        experList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = dataList.get(position).getName();
                Intent intent = new Intent(OwnPage.this, TrialActivity.class);
                intent.putExtra("ExperimentType", dataList.get(position).getExperimentType());
                intent.putExtra("userID",userID);
                intent.putExtra("chosenExperiment", name);
                intent.putExtra("locationRequired", dataList.get(position).getLocationRequired().toString());

                intent.putExtra("isEnd", dataList.get(position).getEnded().toString());
                int minTrials = (int) dataList.get(position).getMinTrials();
                intent.putExtra("minTrials", Integer.toString(minTrials));
                DocumentReference userDocReference = db.collection("users").document(dataList.get(position).getUserId());

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
        Sub.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                expName.clear();

                dataList.clear();

                for (QueryDocumentSnapshot doc : value) {
                    String oneExp = (String) doc.getData().get("exp");
                    expName.add(oneExp);

                }
                System.out.println(expName);

                for (String name: expName){
                    DocumentReference specific = db.collection("experiments").document(name);
                    specific.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {


                                    String name = document.getId();
                                    String description = (String) document.getData().get("description");
                                    String type = (String) document.getData().get("experimentType");
                                    Boolean isEnd = document.getBoolean("isEnded");
                                    Boolean isPublished = document.getBoolean("isPublished");
                                    Boolean locationRequired = document.getBoolean("locationRequired");
                                    long minTrials = (long) document.getData().get("minTrials");
                                    String userName = (String) document.getData().get("userID");
                                    dataList.add(new Experiment(description, isEnd, true, minTrials, locationRequired, type, name, userName));
                                    System.out.println(type);
                                    experAdapter.notifyDataSetChanged();

                                }
                            }
                        }


                    } );




                }







            }
        });




    }

}