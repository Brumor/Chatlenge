package com.brumor.chatlenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.brumor.chatlenge.ChatListActivity.ChatFbDb;
import static com.brumor.chatlenge.ChatListActivity.ChatlistDbRef;
import static com.brumor.chatlenge.ChatListActivity.UserDbRef;
import static com.brumor.chatlenge.ChatListActivity.currentuser;

public class ConversationActivity extends AppCompatActivity {

    ListView conversationListView;
    Button sendMessageButton;
    String talkingUserId;
    String talkingUserName;
    DatabaseReference ConversationDbRef;
    DatabaseReference OtherConversationDbRef;
    DatabaseReference OtherChatlistDbRef;
    EditText messageEditText;
    ChildEventListener convChildEventListener;
    ConversationAdapter conversationAdapter;

    final UserItem currentUser = ChatListActivity.currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        messageEditText = (EditText) findViewById(R.id.messenger_edit_text);

        ChatFbDb = FirebaseDatabase.getInstance();

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        talkingUserId = i.getStringExtra("currentTalkingUserId");
        talkingUserName = i.getStringExtra("currentTalkingUserName");


        ab.setTitle(talkingUserName);
        ConversationDbRef = UserDbRef.child(currentUser.getUser_id() + "/conversations/" + talkingUserId );
        OtherConversationDbRef = UserDbRef.child(talkingUserId + "/conversations/" + currentUser.getUser_id());
        OtherChatlistDbRef = UserDbRef.child(talkingUserId + "/forChatList/" + currentUser.getUser_id());

        conversationListView = (ListView) findViewById(R.id.conversation_listview);

        sendMessageButton = (Button) findViewById(R.id.messenger_send_button);

        final List<ChatlengeMessage> chatlengeMessageList = new ArrayList<>();

        conversationAdapter = new ConversationAdapter(this, R.layout.chatlenge_message, chatlengeMessageList, currentUser.getUser_id());

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });
        conversationListView.setAdapter(conversationAdapter);
        onAttachDbReadListener();
    }

    public void sendMessage(View view) {

        ChatlengeMessage Message = new ChatlengeMessage(messageEditText.getText().toString(), currentUser.getUser_id(), currentUser.getUser_name(), talkingUserId, talkingUserName, System.currentTimeMillis());
        Conversation conversation = new Conversation(talkingUserId, talkingUserName, currentUser.getUser_id(), currentUser.getUser_name(), messageEditText.getText().toString());
        Conversation otherConversation = new Conversation( currentUser.getUser_id(), currentUser.getUser_name(), talkingUserId, talkingUserName, messageEditText.getText().toString());


        ConversationDbRef.push().setValue(Message);
        OtherConversationDbRef.push().setValue(Message);

        ChatlistDbRef.child(talkingUserId).setValue(conversation);
        OtherChatlistDbRef.setValue(otherConversation);

        int i = 1;

        UserDbRef.child(currentuser.getUid()).child("user_score").setValue(i);

        messageEditText.setText("");
    }

    private void onAttachDbReadListener() {

        if (convChildEventListener == null) {

            convChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    ChatlengeMessage chatlengeMessage = dataSnapshot.getValue(ChatlengeMessage.class);
                    conversationAdapter.add(chatlengeMessage);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            ConversationDbRef.addChildEventListener(convChildEventListener);
        }
    }

    @Override
    protected void onStop() {


        super.onStop();
        detachDatabaseReadListener();


    }

    @Override
    protected void onPause() {
        super.onPause();
        detachDatabaseReadListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachDatabaseReadListener();
    }

    private void detachDatabaseReadListener () {
        if (convChildEventListener != null) {
            ConversationDbRef.removeEventListener(convChildEventListener);
            convChildEventListener = null;
        }
    }
}
