package com.example.sbeta;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;

public class SearchUserFrag extends Fragment {

    private EditText getKeyword;
    private Button SearchButton;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onKeyObtained(String username);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchuserfrag, container, false);

        getKeyword = view.findViewById(R.id.UserSearchKey);
        SearchButton = view.findViewById(R.id.searchButton);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = getKeyword.getText().toString();
                listener.onKeyObtained(keyword);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement the SearchUserFrag Listener");
        }
    }
}

