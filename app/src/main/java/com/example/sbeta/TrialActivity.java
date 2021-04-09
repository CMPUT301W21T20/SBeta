package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

/**
 * This activity represents a page of all trials of an experiment
 * and a list of option to be taken
 */
public class TrialActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    ListView trialList;
    ArrayAdapter<Trial> trialArrayAdapter;
    static ArrayList<Trial> trialDataList;
    private int trialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trial_list);

        Intent intent = getIntent();
        String expType = intent.getStringExtra("ExperimentType");
        String currentUser = intent.getStringExtra("userID");
        String name = intent.getStringExtra("userName");
        String locationRequired = intent.getStringExtra("locationRequired");
        String isEnd = intent.getStringExtra("isEnd");
        int minTrials = Integer.parseInt(intent.getStringExtra("minTrials"));

        String trialListTittle = intent.getStringExtra("chosenExperiment");
        String userID = intent.getStringExtra("userID");
        String owner = intent.getStringExtra("owner");

        this.setTitle(trialListTittle + " ("+ owner + ")");

        trialList = findViewById(R.id.trial_list);
        trialDataList = new ArrayList<>();

        trialArrayAdapter = new TrialAdapter(this, trialDataList);
        trialList.setAdapter(trialArrayAdapter);

        Button operationButton = findViewById(R.id.operation_button);
        Button addButton = findViewById(R.id.add_trial_button);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference= db.collection("experiments");
        final DocumentReference experiment = collectionReference.document(trialListTittle);
        final CollectionReference trials = experiment.collection("trials");

        //min trials
        if (trialNum >= minTrials) {
            Toast.makeText(TrialActivity.this, "Minimum number of trials is has been sastified", Toast.LENGTH_SHORT).show();
        }

        //check trial info
        trialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TrialActivity.this, TrialInfo.class);
                intent.putExtra("date", trialDataList.get(position).getCreatedTime().toString());
                intent.putExtra("chosenTrial", trialDataList.get(position).getTrialName());
                intent.putExtra("correspondingExp", trialListTittle);
                intent.putExtra("owner", owner);
                intent.putExtra("user", name);
                intent.putExtra("data", trialDataList.get(position).getResult());
                intent.putExtra("expType", expType);
                startActivity(intent);
            }
        });


        trials.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                trialDataList.clear();

                for(QueryDocumentSnapshot doc: value) {
                    if (expType.equals("Binomial trials")) {
                        String userName = (String) doc.getData().get("user id");
                        double result = (double) doc.getData().get("result");
                        //String location =
                        int trialNum = (int) (long) doc.getData().get("trial id");
                        String name = (String) "trial " + trialNum;


                        binomialTrial theTrial = new binomialTrial(result, userName, null, name, trialNum);
                        if (doc.getData().get("date") != null){
                            Date createdDate = ((com.google.firebase.Timestamp) doc.getData().get("date")).toDate();
                            Timestamp createdTime = new Timestamp(createdDate.getTime());
                            theTrial.setCreatedTime(createdTime);
                        }
                        trialDataList.add(theTrial);
                    }
                    else if (expType.equals("Count-based")) {
                        String userName = (String) doc.getData().get("user id");
                        double result = (double) doc.getData().get("result");
                        int trialNum = (int) (long) doc.getData().get("trial id");
                        String name = (String) "trial " + trialNum;

                        countBasedTrial theTrial = new countBasedTrial(result, userName, null, name, trialNum);

                        if (doc.getData().get("date") != null){
                            Date createdDate = ((com.google.firebase.Timestamp) doc.getData().get("date")).toDate();
                            Timestamp createdTime = new Timestamp(createdDate.getTime());
                            theTrial.setCreatedTime(createdTime);
                        }
                        trialDataList.add(theTrial);
                    }
                    else if (expType.equals("Measurement trials")) {
                        String userName = (String) doc.getData().get("user id");
                        double result;
                        result = (double) doc.getData().get("result");
                        int trialNum = (int) (long) doc.getData().get("trial id");
                        String name = (String) "trial " + trialNum;

                        measurementTrial theTrial = new measurementTrial(result, userName, null, name, trialNum);

                        if (doc.getData().get("date") != null){
                            Date createdDate = ((com.google.firebase.Timestamp) doc.getData().get("date")).toDate();
                            Timestamp createdTime = new Timestamp(createdDate.getTime());
                            theTrial.setCreatedTime(createdTime);
                        }
                        trialDataList.add(theTrial);
                    }
                    else {
                        String userName = (String) doc.getData().get("user id").toString();
                        double result = (double) doc.getData().get("result");
                        int trialNum = (int) (long) doc.getData().get("trial id");
                        String name = (String) "trial " + trialNum;

                        nonNegativeCount theTrial = new nonNegativeCount(result, userName, null, name, trialNum);

                        if (doc.getData().get("date") != null){
                            Date createdDate = ((com.google.firebase.Timestamp) doc.getData().get("date")).toDate();
                            Timestamp createdTime = new Timestamp(createdDate.getTime());
                            theTrial.setCreatedTime(createdTime);
                        }
                        trialDataList.add(theTrial);
                    }

                    Collections.sort(trialDataList, new Comparator<Trial>() {
                        @Override
                        public int compare(Trial o1, Trial o2) {
                            return Integer.compare(o1.getTrialNum(), o2.getTrialNum());
                        }
                    });

                    trialArrayAdapter.notifyDataSetChanged();

                    trialNum = trialDataList.size();

                    if (trialNum >= minTrials) {
                        Toast.makeText(TrialActivity.this, "Minimum number of trials is has been sastified", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // place your code here
                switch (v.getId()) {
                    case R.id.operation_button:
                        PopupMenu popupMenu1 = new PopupMenu(TrialActivity.this, v);
                        popupMenu1.getMenuInflater().inflate(R.menu.operation_menu, popupMenu1.getMenu());
                        popupMenu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                //Toast.makeText(TrialActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                switch (item.getItemId()) {
                                    case R.id.statistics:

                                        Intent StatIntent = new Intent(TrialActivity.this, StatActivity.class);
                                        StatIntent.putExtra("ExperimentType", expType);
                                        startActivity(StatIntent);
                                        return true;
                                    case R.id.ignore:

                                        // do your code
                                        if (expType.equals("Binomial trials")){
                                            Intent Lintent = new Intent(TrialActivity.this, RegisterBarCode.class);
                                            Lintent.putExtra("ExperimentType", expType);
                                            Lintent.putExtra("chosenExperiment", trialListTittle);
                                            Lintent.putExtra("trial number", trialNum);
                                            Lintent.putExtra("userID", currentUser);
                                            Lintent.putExtra("userName", name);
                                            Lintent.putExtra("locationRequired", locationRequired);
                                            startActivity(Lintent);
                                            return true;
                                        }

                                        Intent Lintent = new Intent(TrialActivity.this, RegisterBarCodeCountBase.class);

                                        Lintent.putExtra("ExperimentType", expType);
                                        Lintent.putExtra("chosenExperiment", trialListTittle);
                                        Lintent.putExtra("trial number", trialNum);
                                        Lintent.putExtra("userID", currentUser);
                                        Lintent.putExtra("userName", name);
                                        Lintent.putExtra("locationRequired", locationRequired);
                                        startActivity(Lintent);

                                        return true;


                                    case R.id.questions:
                                        Intent intent = new Intent(TrialActivity.this, showQuestion.class);
                                        intent.putExtra("chosenExperiment", trialListTittle);
                                        startActivity(intent);
                                        return true;
                                    case R.id.unpublish_exp:
                                        DocumentReference docRefPub = db.collection("experiments").document(trialListTittle);
                                        docRefPub.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()){
                                                        String ownerID = (String) document.getData().get("userID");
                                                        Log.i("ownerID", ownerID);
                                                        Log.i("currentUser", currentUser);
                                                        if (ownerID.equals(currentUser)) {
                                                            Log.i("Changing", "YES");
                                                            if ((Boolean) document.getData().get("isPublished")) {
                                                                docRefPub.update("isPublished", false);
                                                                Toast.makeText(TrialActivity.this, "This experiment is unpublished!", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                docRefPub.update("isPublished", true);
                                                                Toast.makeText(TrialActivity.this, "This experiment is published!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(TrialActivity.this, "Only owner can make this operation!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            }
                                        });

                                        // do your code
                                        return true;
                                    case R.id.end_exp:
                                        Log.i("task", "end!");
                                        DocumentReference docRefEnd = db.collection("experiments").document(trialListTittle);
                                        docRefEnd.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                        String ownerID = (String) document.getData().get("userID");
                                                        if (ownerID.equals(currentUser)) {
                                                            if ((Boolean) document.getData().get("isEnded")) {
                                                                Toast.makeText(TrialActivity.this, "This experiment has already been ended!", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                docRefEnd.update("isEnded", true);
                                                                Toast.makeText(TrialActivity.this, "This experiment is ended!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(TrialActivity.this, "Only owner can make this operation!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                        return true;
                                    case R.id.scan_barcode:
                                        Log.i("Task", "scan!");
                                        Intent Sintent = new Intent(TrialActivity.this, ScanBarCode.class);
                                        startActivity(Sintent);
                                        return true;

                                        //do your code
                                    case R.id.subscribe:
                                        Log.i("Task", "subsribe!");
                                        CollectionReference subscribedExps = db.collection("users").document(userID).collection("subscribedExp");
                                        DocumentReference docRefSub = db.collection("users").document(userID).collection("subscribedExp").document(trialListTittle);
                                        docRefSub.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                        docRefSub.delete();
                                                        Toast.makeText(TrialActivity.this, "This experiment is unsubsribed!", Toast.LENGTH_SHORT).show();
                                                        return;
                                                    } else {
                                                        HashMap<String, Object> newSub = new HashMap<>();
                                                        newSub.put("exp", trialListTittle);
                                                        subscribedExps.document(trialListTittle)
                                                                .set(newSub);
                                                        Toast.makeText(TrialActivity.this, "This experiment is subsribed!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                        });
                                        return true;
                                    case R.id.maps:
                                        Intent mapIntent = new Intent(TrialActivity.this, MapsMarkerActivity.class);
                                        mapIntent.putExtra("chosenExperiment", trialListTittle);
                                        Log.d("From trial activity", "chosenExperiment" + trialListTittle);
                                        startActivity(mapIntent);
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                        popupMenu1.show();
                        break;

                    case R.id.add_trial_button:
                        // place your code here
                        PopupMenu popupMenu2 = new PopupMenu(TrialActivity.this, v);
                        popupMenu2.getMenuInflater().inflate(R.menu.add_option, popupMenu2.getMenu());
                        popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                switch (item.getItemId()) {
                                    case R.id.manually_add:
                                        DocumentReference endDocReference = db.collection("experiments").document(trialListTittle);
                                        endDocReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document != null) {
                                                        boolean isEnded = (boolean) document.get("isEnded");
                                                        if (isEnded == false) {
                                                            Intent intent;
                                                            if (expType.equals("Binomial trials")) {
                                                                intent = new Intent(TrialActivity.this, AddBinomialTrial.class);
                                                                intent.putExtra("chosenExperiment", trialListTittle);
                                                                intent.putExtra("trial number", trialNum);
                                                                intent.putExtra("userID", currentUser);
                                                                intent.putExtra("userName", name);
                                                                intent.putExtra("locationRequired", locationRequired);
                                                                startActivity(intent);
                                                            } else {
                                                                intent = new Intent(TrialActivity.this, AddCountTrial.class);
                                                                intent.putExtra("ExperimentType", expType);
                                                                intent.putExtra("chosenExperiment", trialListTittle);
                                                                intent.putExtra("trial number", trialNum);
                                                                intent.putExtra("userID", currentUser);
                                                                intent.putExtra("userName", name);
                                                                intent.putExtra("locationRequired", locationRequired);
                                                                startActivity(intent);
                                                            }
                                                        } else {
                                                            Toast.makeText(TrialActivity.this, "This experiment has been ended", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                        return true;
                                    case R.id.scan_qr_code:
                                        Intent intentToGenerate;
                                        if (expType.equals("Binomial trials")) {
                                            intentToGenerate = new Intent(TrialActivity.this, GenerateBioQR.class);
                                            intentToGenerate.putExtra("chosenExperiment", trialListTittle);
                                            intentToGenerate.putExtra("trial number", trialNum);
                                            intentToGenerate.putExtra("userID", currentUser);
                                            intentToGenerate.putExtra("userName", name);
                                            intentToGenerate.putExtra("locationRequired", locationRequired);
                                            startActivity(intentToGenerate);
                                        }
                                        else {
                                            intentToGenerate = new Intent(TrialActivity.this, GenerateCountQR.class);
                                            intentToGenerate.putExtra("chosenExperiment", trialListTittle);
                                            intentToGenerate.putExtra("trial number", trialNum);
                                            intentToGenerate.putExtra("userID", currentUser);
                                            intentToGenerate.putExtra("userName", name);
                                            intentToGenerate.putExtra("locationRequired", locationRequired);
                                            startActivity(intentToGenerate);
                                        }
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                        popupMenu2.show();
                        break;
                }
            }
        };
        trialArrayAdapter.notifyDataSetChanged();
        operationButton.setOnClickListener(listener);
        addButton.setOnClickListener(listener);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        trialArrayAdapter.notifyDataSetChanged();
        return false;
    }
}
