package com.example.sbeta;

// custom reply list

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
 * ArrayAdapter for displaying replys in the forum for a question
 */
public class CustomReplyList extends ArrayAdapter<String> {

    private final ArrayList<String> replies;
    private final Context context;

    public CustomReplyList(Context context, ArrayList<String> replies) {
        super(context, 0, replies);
        this.replies = replies;
        this.context = context;
    }

    @SuppressLint("DefaultLocate")
    public View getView(int position, @Nullable View convertView, @Nonnull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.reply_info,parent, false);
        }

        String reply = replies.get(position);
        TextView replyInfo = view.findViewById(R.id.reply_content);
        replyInfo.setText(reply);

        return view;
    }
}

