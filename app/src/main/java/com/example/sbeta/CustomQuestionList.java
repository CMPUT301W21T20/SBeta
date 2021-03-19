package com.example.sbeta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * ArrayAdapter for displaying questions in the forum for an experiment
 */
public class CustomQuestionList extends ArrayAdapter<String> {

    private ArrayList<String> questions;
    private Context context;

    public CustomQuestionList(Context context, ArrayList<String> questions) {
        super(context, 0, questions);
        this.questions = questions;
        this.context = context;
    }

    @SuppressLint("DefaultLocate")
    public View getView(int position, @Nullable View convertView, @Nonnull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.question_info,parent, false);
        }

        String question = questions.get(position);
        TextView questionInfo = view.findViewById(R.id.question_name);
        questionInfo.setText(question);

        return view;
    }
}
