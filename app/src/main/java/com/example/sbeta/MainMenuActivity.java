package com.example.sbeta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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

/**
 * this is the main page that shows the experiments , user profile and searching box
 */
public class MainMenuActivity extends AppCompatActivity implements AddNewExperimentFragment.OnFragmentInteractionListener{

    ListView experList;
    ArrayAdapter<Experiment> experAdapter;
    ArrayList<Experiment> dataList;
    Button searchButton;
    EditText searchWord;
    ImageButton userProfile;
    static String logInUserName;
    String userID;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        final String TAG = "Sample";
        FirebaseFirestore db;
        logInUserName = getIntent().getStringExtra("userName");
        userID = getIntent().getStringExtra("userID");

        experList = findViewById(R.id.exper_list);
        searchButton = findViewById(R.id.search_button);
        searchWord = findViewById(R.id.searchKeyWord);
        userProfile = findViewById(R.id.user_profile);
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("experiments");

        dataList = new ArrayList<>();

        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUserProfile = new Intent(MainMenuActivity.this, UserProfileActivity.class);
                intentUserProfile.putExtra("userID", userID);
                startActivity(intentUserProfile);
            }
        });

        //enter trial list
        experList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = dataList.get(position).getName();
                Intent intent = new Intent(MainMenuActivity.this, TrialActivity.class);
                intent.putExtra("chosenExperiment", name);
                startActivity(intent);

            }
        });

//        User testUser = new User("123456");
//        Experiment testExper1 = new Experiment(testUser, "this is test", "published", "Experiment A");
//        Experiment testExper2 = new Experiment(testUser, "URUS", "published", "Car");
//        Experiment testExper3 = new Experiment(testUser, "GALAXY s31", "published", "Phone");
//        Experiment testExper4 = new Experiment(testUser, "GALAXY note40", "end", "Samsung");
//        Experiment []experiments = {testExper1, testExper2, testExper3, testExper4};
//        dataList.addAll(Arrays.asList(experiments));

        experAdapter = new CustomMainList(this, dataList);
        experList.setAdapter(experAdapter);

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                dataList.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                    Log.d(TAG, String.valueOf(doc.getData().get("description")));
                    String name = doc.getId();
                    String description = (String) doc.getData().get("description");
                    Boolean isEnd = (Boolean) doc.getBoolean("isEnded");
                    Boolean isPublished = (Boolean) doc.getBoolean("isPublished");
                    //Integer minTrials = (Integer) doc.get("minTrials");
                    //Integer minTrials = 1;
                    long minTrials = (long) doc.getData().get("minTrials");
                    Boolean locationRequired = (Boolean) doc.getBoolean("locationRequired");
                    String type = (String) doc.getData().get("experimentType");
                    String userId = (String) doc.getData().get("userName");

                    dataList.add(new Experiment(description, isEnd, isPublished, minTrials, locationRequired, type, name, userId));
                }
                //dataList.add(new Experiment("description", true, true, 1, false, "type", "name", "userId"));
                experAdapter.notifyDataSetChanged();
            }
        });

        // search
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchWord.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
                    builder.setMessage("Cannot search empty string!");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    builder.create().show();
                } else {
                    Intent intent = new Intent(MainMenuActivity.this, SearchDisplay.class);
                    intent.putExtra("keyword", searchWord.getText().toString());
                    searchWord.setText("");
                    startActivity(intent);
                }
            }
        });

        // this addExperimentButton will call a fragment that used to add new experiment with required information
        final FloatingActionButton addExperimentButton = findViewById(R.id.add_experiment_button);
        addExperimentButton.setOnClickListener(v -> new AddNewExperimentFragment().show(getSupportFragmentManager(), "ADD_EXPERIMENT"));


        /**
         * just a easy function to delete experiment, will be deleted in later version
         */
        experList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String description = dataList.get(position).description;
                DocumentReference an_experimentReference = collectionReference.document(description);

                an_experimentReference.delete();
                experAdapter.notifyDataSetChanged();
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

        HashMap<String, Object> experiment_to_add = new HashMap<>();
        experiment_to_add.put("description", new_experiment.description);
        experiment_to_add.put("experimentType", new_experiment.experimentType);
        experiment_to_add.put("isEnded", false);
        experiment_to_add.put("isPublished", true);
        experiment_to_add.put("locationRequired", new_experiment.locationRequired);
        experiment_to_add.put("minTrials", new_experiment.minTrials);
        experiment_to_add.put("userName", new_experiment.getUserName());
        experiment_to_add.put("userID", userID);


        collectionReference
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
}
