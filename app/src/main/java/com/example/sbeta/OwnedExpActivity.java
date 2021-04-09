package com.example.sbeta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class OwnedExpActivity extends AppCompatActivity {

    String userID;
    String ExperimentType;
    FirebaseFirestore db;
    String userName=new String();
    ListView OwnedListView;
    ArrayAdapter<Experiment> OwnedAdapter;
    final String TAG = "exp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent INtent = getIntent();
        userID = INtent.getStringExtra("userID");

        ArrayList<Experiment> OwnedList = new ArrayList<Experiment>();

        setContentView(R.layout.owned_experiment);
        OwnedListView = (ListView) findViewById(R.id.display_personal);

        db = FirebaseFirestore.getInstance();
        Log.e(TAG,"reference");
        final CollectionReference reference = db.collection("users");
        final CollectionReference expReference=db.collection("experiments");

        DocumentReference UserReference=reference.document(userID);
        CollectionReference OwnedExpReference=UserReference.collection("ownedExp");
        // no bugs so far

        reference.addSnapshotListener(new EventListener<QuerySnapshot>(){
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                Log.e(TAG,"got here");
                if (snapshots.size()==0) throw new IllegalArgumentException("wrong database");
                for (QueryDocumentSnapshot doc : snapshots){
                    if (userID.equals(doc.getId())){
                        Log.d(TAG, String.valueOf(doc.getId()));
                        userName = (String) doc.getData().get("userName");
                        // throw new IllegalArgumentException("got here");
                        break;
                    }
                }
            }
        });

        // if (userName.length()==0) throw new IllegalArgumentException(userName);


        OwnedExpReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                String docName=new String();

                for(QueryDocumentSnapshot doc:snapshots){
                    Log.d(TAG, String.valueOf(doc.getId()));
                    docName =(String)doc.getId();
                    // if (docName.length()>0) throw new IllegalArgumentException(docName);
                    DocumentReference ExpDoc=expReference.document(docName);
                    String DocName = docName;
                    ExpDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                           if (task.isSuccessful()) {
                               DocumentSnapshot document = task.getResult();
                               if (document.exists()) {
                                   Log.d(TAG, "DocumentSnapshot data: " + document.getId());
                                   ExperimentType =(String)document.getData().get("experimentType");
                                   // if (ExperimentType.length()>0) throw new IllegalArgumentException(ExperimentType);
                                   OwnedList.add(new Experiment(DocName, ExperimentType));
                                   // if (OwnedList.size()!=0) throw new IllegalArgumentException(String.valueOf(OwnedList.size()));
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

        // if (OwnedList.size()==0) throw new IllegalArgumentException("0"); // wrong
        OwnedList.clear();
        OwnedList.add(new Experiment("exp1","binomial"));
        OwnedAdapter=new DisplayPersonalAdapter(this,OwnedList);
        OwnedListView.setAdapter(OwnedAdapter);

        OwnedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position<0) throw new IllegalArgumentException("no");
                Experiment exp = OwnedList.get(position);

                Intent intent=new Intent(OwnedExpActivity.this,TrialActivity.class);
                if (userID.length()<=0 || userID==null) throw new IllegalArgumentException("no userID");
                intent.putExtra("userID",userID);
                if (userName.length()<=0 || userName==null) throw new IllegalArgumentException("no userName");
                intent.putExtra("userName", userName);
                intent.putExtra("ExperimentType",exp.getExperimentType());
                intent.putExtra("chosenExperiment",exp.getName());

                startActivity(intent);
            }
        });
    }
}
