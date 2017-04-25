package com.brumor.chatlenge;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pbric on 03/03/2017.
 */

public class ConversationAdapter extends ArrayAdapter<ChatlengeMessage> {

    String current_user_id = ChatListActivity.currentuser.getUid();
    String current_user_name = ChatListActivity.currentuser.getDisplayName();
    String message_sender_id;
    LinearLayout messageLinearLayout;
    TextView messageTextview;
    TextView usernameTextView;


    public ConversationAdapter(Context context, int resource, List<ChatlengeMessage> objects, String currentUser) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.chatlenge_message, parent, false);

        }

        final ChatlengeMessage currentMessage = getItem(position);


        convertView.findViewById(R.id.user_message_LinearLayout).setVisibility(View.INVISIBLE);

        message_sender_id = currentMessage.getFrom_user_id();



        /*if ( message_sender_id == current_user_id) {

            messageLinearLayout = (LinearLayout) convertView.findViewById(R.id.user_message_LinearLayout);
            messageLinearLayout.setVisibility(View.VISIBLE);
            messageTextview = (TextView) convertView.findViewById(R.id.user_message_TextView);
            usernameTextView = (TextView) convertView.findViewById(R.id.user_name_TextView);

            convertView.findViewById(R.id.other_message_LinearLayout).setVisibility(View.INVISIBLE);

        } else { */

            messageTextview = (TextView) convertView.findViewById(R.id.other_message_TextView);
            usernameTextView = (TextView) convertView.findViewById(R.id.other_name_TextView);

        //}

        messageTextview.setText(currentMessage.getMessage_content());
        usernameTextView.setText(currentMessage.getFrom_user_name());


        return convertView;

    }


}


