package com.example.sbeta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TrialActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    ListView trialList;
    ArrayAdapter<Trial> trialArrayAdapter;
    ArrayList<Trial> trialDataList;
    private int trialNum;
    ArrayList<Trial> ignoreTrials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trial_list);

        Intent intent = getIntent();
        String expType = intent.getStringExtra("ExperimentType").toString();
        //String userName = intent.getStringExtra("chosenExperiment");


        //Intent intent = getIntent();
        //Intent intent = getIntent();
        String trialListTittle = intent.getStringExtra("chosenExperiment");


        trialList = findViewById(R.id.trial_list);
        trialDataList = new ArrayList<>();

        trialArrayAdapter = new TrialAdapter(this, trialDataList, ignoreTrials);
        trialList.setAdapter(trialArrayAdapter);


        Button operationButton = (Button) findViewById(R.id.operation_button);
        Button addButton = (Button) findViewById(R.id.add_trial_button);



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference= db.collection("experiments");
        final DocumentReference experiment = collectionReference.document(trialListTittle);
        final CollectionReference trials = experiment.collection("trials");


        trials.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                trialDataList.clear();

                for(QueryDocumentSnapshot doc: value) {
                    if (expType.equals("Binomial trials")) {
                        String userName = (String) doc.getData().get("user name");
                        boolean check = (boolean) doc.getData().get("result");
                        double result;
                        if (check==true) {result = 1;} else {result = 0;}
                        //String location =
                        String name = (String) "trial" + doc.getData().get("trial id");
                        trialDataList.add(new binomialTrial(result, userName, null, name));
                    }
                    else if (expType.equals("Count-based")) {
                        String userName = (String) doc.getData().get("user name");
                        double result;
                        result = (double) doc.getData().get("result");
                        String name = (String) "trial" + doc.getData().get("trial id");
                        trialDataList.add(new countBasedTrial(result, userName, null, name));
                    }
                    else if (expType.equals("Measurement trials")) {
                        String userName = (String) doc.getData().get("user name");
                        double result;
                        result = (double) doc.getData().get("result");
                        String name = (String) "trial" + doc.getData().get("trial id");
                        trialDataList.add(new countBasedTrial(result, userName, null, name));
                    }
                    else {
                        String userName = (String) doc.getData().get("user name");
                        double result;
                        result = (double) doc.getData().get("result");
                        String name = (String) "trial" + doc.getData().get("trial id");
                        trialDataList.add(new countBasedTrial(result, userName, null, name));
                    }
                    trialArrayAdapter.notifyDataSetChanged();

                    trialNum = trialDataList.size();

                }
            }
        });

        /*
        Collections.sort(trialDataList, new Comparator<Trial>() {
            @Override
            public int compare(Trial o1, Trial o2) {
                return Integer.compare(Integer.parseInt(o1.getTrialName()), Integer.parseInt(o2.getTrialName()));
            }
        });

         */



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
                                Toast.makeText(TrialActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                                switch (item.getItemId()) {
                                    case R.id.edit_trial:
                                        // do your code
                                        return true;
                                    case R.id.statistics:
                                        // do your code
                                        return true;
                                    case R.id.ignore:
                                        // do your code
                                        return true;
                                    case R.id.questions:
                                        Intent intent = new Intent(TrialActivity.this, showQuestion.class);
                                        intent.putExtra("chosenExperiment", trialListTittle);
                                        startActivity(intent);
                                        return true;
                                    case R.id.unpublish_exp:
                                        // do your code
                                        return true;
                                    case R.id.end_exp:
                                        // do your code
                                    case R.id.scan_barcode:
                                        //do your code
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
                                Toast.makeText(TrialActivity.this, "" + item.getTitle(), Toast.LENGTH_SHORT).show();

                                switch (item.getItemId()) {
                                    case R.id.manually_add:
                                        Intent intent;
                                        if (expType.equals("Binomial trials")) {
                                            intent = new Intent(TrialActivity.this, AddBinomialTrial.class);
                                            intent.putExtra("chosenExperiment", trialListTittle);
                                            intent.putExtra("trial number", trialNum);
                                            startActivity(intent);
                                        }
                                        else {
                                            intent = new Intent(TrialActivity.this, AddCountTrial.class);
                                            startActivity(intent);
                                        }


                                        return true;
                                    case R.id.scan_qr_code:
                                        // do your code
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

        operationButton.setOnClickListener(listener);
        addButton.setOnClickListener(listener);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
