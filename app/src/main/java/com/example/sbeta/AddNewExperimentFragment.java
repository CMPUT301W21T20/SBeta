package com.example.sbeta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddNewExperimentFragment extends DialogFragment {

    private OnFragmentInteractionListener listener;
    String type1 = "Count-based";
    String type2 = "Binomial trials";
    String type3 = "Non-negative integer counts";
    String type4 = "Measurement trials";
    String selectedType;
    ArrayList<String> ExprNameArray;
    private boolean isCorrect;
    private boolean nameRepeated;



    public AddNewExperimentFragment() {
    }

    /**
     * this is the interface for MainMenuActivity to add experiment
     */
    public interface OnFragmentInteractionListener {
        void onOkPressed(Experiment new_experiment);
    }

    /**
     * this will check if context is correct
     * @param context
     * this is the context provided by caller
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * this will create a builder to let user type in the information of a new experiment
     * @param savedInstanceState
     * @return
     * this will return a builder
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_experiment_fragment, null);
        isCorrect = true;
        nameRepeated = false;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference ExprReference = db.collection("experiments");

        ExprReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ExprNameArray.clear();
                for (QueryDocumentSnapshot doc : value){
                    ExprNameArray.add(doc.getId());
                }
            }
        });

        EditText description_view = view.findViewById(R.id.description_input);
        EditText minTrials_view = view.findViewById(R.id.minTrials);
        EditText region_view = view.findViewById(R.id.region_text);
        minTrials_view.setText("0");
        SwitchMaterial location_required_switch = view.findViewById(R.id.Location_required_switch);


        Spinner TypeSpinner = view.findViewById(R.id.type_spinner);
        TypeSpinner.setPrompt("select type");
        ArrayList<String> type_list = new ArrayList<>();
        type_list.add(type1);
        type_list.add(type2);
        type_list.add(type3);
        type_list.add(type4);

        ArrayAdapter<String> typeList_adapter = new ArrayAdapter<String>(requireActivity().getBaseContext(), android.R.layout.simple_spinner_item, type_list);
        TypeSpinner.setAdapter(typeList_adapter);
        TypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = type_list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("add new experiment")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String description = description_view.getText().toString();
                        String minTrialsString = minTrials_view.getText().toString();
                        String regionString = region_view.getText().toString();
                        boolean locationRequired = location_required_switch.isChecked();
                        String experimentType = selectedType;
                        if (minTrialsString.equals("")) {
                            isCorrect = false;
                        }
                        else if (description.equals("")) {
                            isCorrect = false;
                        }
                        else if (regionString.equals("")) {
                            isCorrect = false;
                        }

                        if(ExprNameArray.contains(ExprNameArray)){
                            nameRepeated = true;
                        }

                        if (isCorrect) {
                            String userName = MainMenuActivity.logInUserName;
                            Experiment new_experiment = new Experiment(
                                    description,
                                    false,
                                    true,
                                    Long.parseLong(minTrialsString),
                                    locationRequired,
                                    experimentType,
                                    description,
                                    userName);
                            Toast.makeText(getActivity(), "you selected " + selectedType, Toast.LENGTH_LONG).show();
                            listener.onOkPressed(new_experiment);
                        }
                        else if (nameRepeated == true){
                            Toast.makeText(getActivity(), "Experiment name exist, please use another name", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "Please fill all the blanks", Toast.LENGTH_LONG).show();
                        }
                    }
                }).create();
    }


}
