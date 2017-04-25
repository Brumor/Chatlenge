package com.brumor.chatlenge;

/**
 * Created by pbric on 03/03/2017.
 */

public class ChatlengeMessage {

    private String message_content;
    private String to_user_id;
    private String to_user_name;
    private String from_user_name;
    private String from_user_id;
    private long timeStamp;


    public ChatlengeMessage () {}

    public ChatlengeMessage(String MessageContent, String fromUserId, String fromUserName, String toUserId, String toUserName, long messageTime) {

        message_content = MessageContent;
        from_user_id = fromUserId;
        from_user_name = fromUserName;
        to_user_id = toUserId;
        to_user_name = toUserName;
        timeStamp = messageTime;



    }

    public String getMessage_content () {return message_content;}

    public String getFrom_user_id () {return  from_user_id;}
    public String getFrom_user_name () {return  from_user_name;}

    public String getTo_user_id () {return to_user_id;}
    public String getTo_user_name () {return to_user_name;}

    public long getTimeStamp () {return timeStamp;}
}
