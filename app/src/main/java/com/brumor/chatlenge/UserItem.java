package com.brumor.chatlenge;

/**
 * Created by pbric on 14/03/2017.
 */

public class UserItem {

    private String user_name;
    private String user_id;
    private String user_mail;

    public UserItem () {}

    public UserItem(String userId, String userName, String userMail ) {
        user_name = userName;
        user_id = userId;
        user_mail = userMail;
    }

    public String getUser_name () {return user_name;}
    public String getUser_id () {return user_id;}
    public  String getUser_mail(){return user_mail;}
}
