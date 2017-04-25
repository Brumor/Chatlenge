package com.brumor.chatlenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.brumor.chatlenge.R.layout.chat;

public class ChatListActivity extends AppCompatActivity {

    ListView MainChatListView;
    public static final int RC_SIGN_IN = 1;

    public FirebaseAuth ChatAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static FirebaseUser currentuser;
    public static DatabaseReference UserDbRef;
    public static DatabaseReference ChatlistDbRef;
    public static FirebaseDatabase ChatFbDb;
    ChildEventListener ChatListEventListener;
    UserItem user = new UserItem(null, null, null);

    ChatlengeListAdapter ChatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        ChatFbDb = FirebaseDatabase.getInstance();
        ChatAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentuser = firebaseAuth.getCurrentUser();


                if (currentuser != null) {

                    UserDbRef = ChatFbDb.getReference().child("users/");
                    ChatlistDbRef = ChatFbDb.getReference().child("users/" + currentuser.getUid() + "/forChatList" );
                    user = new UserItem(currentuser.getUid(), currentuser.getDisplayName(), currentuser.getEmail());
                    onSignedInInitialize(user);


                } else {

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(true)
                                    .build(),
                            RC_SIGN_IN);
                }

            }
        };


        MainChatListView = (ListView) findViewById(R.id.mainChatListView);

        List<Conversation>

        conversationsArrayList = new ArrayList<>();

        ChatListAdapter = new ChatlengeListAdapter(this, chat, conversationsArrayList);

        MainChatListView.setAdapter(ChatListAdapter);

        final Intent startConversation = new Intent(this,ConversationActivity.class);

        MainChatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Conversation currentUser = ChatListAdapter.getItem(position);
                startConversation.putExtra("currentTalkingUserId" , currentUser.getSpeaker1_id());
                startConversation.putExtra("currentTalkingUserName", currentUser.getSpeaker1_name());
                startActivity(startConversation);


            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            ChatAuth.removeAuthStateListener(mAuthListener);
        }
        ChatListAdapter.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChatAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthListener != null) {
            ChatAuth.removeAuthStateListener(mAuthListener);
        }
        ChatListAdapter.clear();
        detachDatabaseReadListener();
    }

    public void LogOff(View view) {
        ChatAuth.getInstance().signOut();
        finish();
    }

    public void startConversation(View view) {

        Intent i = new Intent(this, SearchUserActivity.class);
        startActivity(i);
    }

    private void onSignedInInitialize(UserItem user) {
        onAttachDbReadListener(user);
    }

    private void onAttachDbReadListener(UserItem user) {

        if (UserDbRef.child(currentuser.getUid()) == null) {
            UserDbRef.child(currentuser.getUid()).setValue(user);
        }

        if (ChatListEventListener == null) {
            ChatListEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Conversation conversation = dataSnapshot.getValue(Conversation.class);
                    ChatListAdapter.add(conversation);
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

            ChatlistDbRef.addChildEventListener(ChatListEventListener);
        }
    }

    private void detachDatabaseReadListener() {
        if ( ChatListEventListener != null) {
            ChatlistDbRef.removeEventListener(ChatListEventListener);
            ChatListEventListener = null;
        }
    }
}
