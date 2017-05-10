package com.brumor.chatlenge;

/**
 * Created by pbric on 14/03/2017.
 */

public class UserItem {

    private String user_name;
    private String user_id;
    private String user_mail;
    private int user_score;

    public UserItem () {}

    public UserItem(String userId, String userName, String userMail, int userScore ) {
        user_name = userName;
        user_id = userId;
        user_mail = userMail;
        userScore = userScore;
    }

    public String getUser_name () {return user_name;}
    public String getUser_id () {return user_id;}
    public String getUser_mail(){return user_mail;}
    public int getUser_score () {return user_score;}
}
