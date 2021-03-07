package com.example.sbeta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class AddNewExperimentFragment extends DialogFragment {

    private OnFragmentInteractionListener listener;


    public AddNewExperimentFragment() {

    }

    public interface OnFragmentInteractionListener {
        void onOkPressed(Experiment new_experiment);
    }

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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_experiment_fragment, null);

        EditText description_view = view.findViewById(R.id.description_input);
        EditText minTrials_view = view.findViewById(R.id.minTrials);
        minTrials_view.setText("0");
        SwitchMaterial location_required_switch = view.findViewById(R.id.Location_required_switch);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("add new experiment")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String description = description_view.getText().toString();
                        String minTrials = minTrials_view.getText().toString();
                        Experiment new_experiment = new Experiment();
                        new_experiment.description = description;
                        new_experiment.minTrials = Integer.parseInt(minTrials);
                        if (location_required_switch.isChecked()){
                            Toast.makeText(getActivity(),"is checked",Toast.LENGTH_SHORT).show();
                        }


                        listener.onOkPressed(new_experiment);
                    }
                }).create();
    }


}
