package com.brumor.chatlenge;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pbric on 01/03/2017.
 */

public class ChatlengeListAdapter extends ArrayAdapter<Conversation>{


    public ChatlengeListAdapter(Context context, int resource, List<Conversation> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.chat, parent, false);
        }

        Conversation currentChat = getItem(position);

        TextView username = (TextView) convertView.findViewById(R.id.chatlenger_username);
        TextView lastmessage = (TextView) convertView.findViewById(R.id.last_chatlenge);

        String lastMessage = currentChat.getLast_message();

        if (lastMessage.length() > 40) {

            StringBuilder SB = new StringBuilder(currentChat.getLast_message());
            SB.setLength(45);
            lastMessage = SB.toString() + "...";

        }

        username.setText(currentChat.getSpeaker1_name());
        lastmessage.setText(lastMessage);

        return convertView;

    }
}
