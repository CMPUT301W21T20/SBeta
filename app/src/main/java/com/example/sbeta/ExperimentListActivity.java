package com.example.sbeta;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ExperimentListActivity extends AppCompatActivity implements AddNewExperimentFragment.OnFragmentInteractionListener{

    public ListView Experiment_list_view;
    public ArrayAdapter<Experiment> Experiment_ArrayAdapter;
    public ArrayList<Experiment> Experiment_list;
    FirebaseFirestore database;
    CollectionReference experimentsReference;
    private int ID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_list_dayu);

        ID = 1000;

        //access the database
        database = FirebaseFirestore.getInstance();
        experimentsReference = database.collection("experiments");

        Experiment_list_view = findViewById(R.id.experiment_list);
        Experiment_list = new ArrayList<>();

        Experiment_ArrayAdapter = new ExpArrayAdapter(this, Experiment_list);
        Experiment_list_view.setAdapter(Experiment_ArrayAdapter);

        // this addExperimentButton will call a fragment that used to add new experiment with required information
        final FloatingActionButton addExperimentButton = findViewById(R.id.add_experiment_button);
        addExperimentButton.setOnClickListener(v -> new AddNewExperimentFragment().show(getSupportFragmentManager(), "ADD_EXPERIMENT"));

        // this part will let the list keeps the newest state
        experimentsReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Experiment_list.clear();
                for (QueryDocumentSnapshot doc: value){
                    Log.d("testing", String.valueOf(doc.getId()));
                    Experiment_list.add(
                            generateExperiment(
                                    (String) doc.getData().get("description"),
                                    (String) doc.getData().get("experimentType"),
                                    (boolean) doc.getData().get("isEnded"),
                                    (boolean) doc.getData().get("isPublished"),
                                    (boolean) doc.getData().get("locationRequired"),
                                    (String) doc.getData().get("userId"),
                                    (long) doc.getData().get("minTrials")
                            )
                    );
                    Experiment_ArrayAdapter.notifyDataSetChanged();
                }
            }
        });

        // delete the experiment by longClick just for developer, will be deleted
        Experiment_list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String description = Experiment_list.get(position).description;
                DocumentReference an_experimentReference = experimentsReference.document(description);

                an_experimentReference.delete();
                Experiment_ArrayAdapter.notifyDataSetChanged();
                return false;
            }
        });

    }

    /**
     * this will generate a new document of experiment on the database
     * @param new_experiment
     * this is the experiment that is going to be added to the database
     */
    public void onOkPressed(Experiment new_experiment){
        ID += 1;

        HashMap<String, Object> experiment_to_add = new HashMap<>();
        experiment_to_add.put("description", new_experiment.description);
        experiment_to_add.put("experimentType", new_experiment.experimentType);
        experiment_to_add.put("isEnded", false);
        experiment_to_add.put("isPublished", true);
        experiment_to_add.put("locationRequired", new_experiment.locationRequired);
        experiment_to_add.put("minTrials", new_experiment.minTrials);
        experiment_to_add.put("userID", Integer.toString(ID));

        experimentsReference
                .document(new_experiment.description)
                .set(experiment_to_add)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("false message", "data cannot be added" + e.toString());
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("success message", "data added successfully");
                    }
                });


    }

    /**
     * a generator of new experiment, will be deleted after Experiment Class completed
     * @param description
     * @param experimentType
     * @param isEnded
     * @param isPublished
     * @param locationRequired
     * @param id
     * @param minTrials
     * @return
     */
    public Experiment generateExperiment(String description, String experimentType, boolean isEnded, boolean isPublished, boolean locationRequired ,String id, long minTrials){
        Experiment newExperiment = new Experiment();
        newExperiment.description = description;
        newExperiment.experimentType = experimentType;
        newExperiment.isEnded = isEnded;
        newExperiment.isPublished = isPublished;
        newExperiment.locationRequired = locationRequired;
        newExperiment.userId = id;
        newExperiment.minTrials = minTrials;

        return newExperiment;
    }
}