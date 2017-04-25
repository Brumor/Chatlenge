package com.brumor.chatlenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;

import static com.brumor.chatlenge.ChatListActivity.UserDbRef;

public class SearchUserActivity extends AppCompatActivity {

    SearchView userSearchView;
    ListView userListView;
    String currentEnteredQuery = null;
    FirebaseListAdapter<UserItem> firebaseListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        userSearchView = (SearchView) findViewById(R.id.user_searchview);
        userListView = ( ListView) findViewById(R.id.listview_search);
        final Intent startConversation = new Intent(this,ConversationActivity.class);

        userSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String inputQuery) {

                Query query = UserDbRef.orderByChild("user_name").equalTo(inputQuery);

                firebaseListAdapter = new FirebaseListAdapter<UserItem>(SearchUserActivity.this, UserItem.class, R.layout.user_item, query) {

                    @Override
                    protected void populateView(View v, UserItem model, int position) {

                        ((TextView) v.findViewById(R.id.user_textview)).setText(model.getUser_name());


                    }
                };

                userListView.setAdapter(firebaseListAdapter);

                currentEnteredQuery = inputQuery;

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //Start a new conversation
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UserItem currentUser = firebaseListAdapter.getItem(position);
                startConversation.putExtra("currentTalkingUserId" , currentUser.getUser_id());
                startConversation.putExtra("currentTalkingUserName", currentUser.getUser_name());
                startActivity(startConversation);

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_user, menu);
        return true;
    }


}
