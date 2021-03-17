package com.example.sbeta;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchUserFragment extends Fragment {

    private EditText searchkeyText;
    private OnFragmentInteractionListener listener;
    private Button OkButton;

    public interface OnFragmentInteractionListener {
        void onKeyPassed(String searchkey);
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstance){
        View v=inflater.inflate(R.layout.search_user_frag,container,false);

        searchkeyText=v.findViewById(R.id.searchkey);
        OkButton=v.findViewById(R.id.okbutton);

        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence input=searchkeyText.getText();
                listener.onKeyPassed((String) input);
            }
        });

        return v;
    }

    public void onDetach(){
        super.onDetach();
        listener=null;
    }
}
