package com.example.sbeta;

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

public class SubscribedExpActivity extends AppCompatActivity {

    String userID;
    String userName;
    String expName;
    FirebaseFirestore db;
    ListView subscribedListView;
    ArrayAdapter<Experiment> SubscribedAdapter;
    final String TAG = "exp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent INtent = getIntent();
        userID = INtent.getStringExtra("userID");
        ArrayList<Experiment> SubscribedList = new ArrayList<Experiment>();

        setContentView(R.layout.subscribed_experiment);
        subscribedListView = (ListView) findViewById(R.id.display_subscribed);

        db = FirebaseFirestore.getInstance();
        final CollectionReference reference = db.collection("users");
        final CollectionReference expReference=db.collection("experiments");

        DocumentReference UserReference=reference.document(userID);
        CollectionReference SubscribedExpReference=UserReference.collection("subscribedExp");

        UserReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getId());
                        userName=(String)document.getData().get("userName");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        SubscribedExpReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                SubscribedList.clear();
                String docName=new String();

                for(QueryDocumentSnapshot doc:snapshots) {
                    Log.d(TAG, String.valueOf(doc.getId()));
                    docName = (String) doc.getId();
                    final String[] ExperimentType = {new String()};
                    // if (docName.length()>0) throw new IllegalArgumentException(docName);
                    DocumentReference ExpDoc = expReference.document(docName);
                    String DocName = docName;
                    ExpDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists() && (boolean) document.getData().get("isPublished")) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getId());
                                    ExperimentType[0] = (String) document.getData().get("experimentType");
                                    // if (ExperimentType.length()>0) throw new IllegalArgumentException(ExperimentType);
                                    SubscribedList.add(new Experiment(DocName, ExperimentType[0]));
                                    // if (SubscribedList.size()!=0) throw new IllegalArgumentException(String.valueOf(OwnedList.size()));
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                }
            }
        });

        SubscribedAdapter=new DisplayPersonalAdapter(this,SubscribedList);
        subscribedListView.setAdapter(SubscribedAdapter);


        subscribedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Experiment exp = SubscribedList.get(position);

                Intent intent=new Intent(SubscribedExpActivity.this,TrialActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userName",userName);
                intent.putExtra("ExperimentType",exp.getExperimentType());
                intent.putExtra("chosenExperiment",exp.getName());

                startActivity(intent);
            }
        });
    }
}
