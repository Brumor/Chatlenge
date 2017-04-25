package com.brumor.chatlenge;

/**
 * Created by pbric on 14/04/2017.
 */

public class Conversation {

    private String speaker1_id;
    private String speaker1_name;
    private String speaker2_id;
    private String speaker2_name;
    private String last_message;

    public  Conversation () {}

    public Conversation(String speaker1Id, String speaker1Name, String speaker2Id, String speaker2Name, String lastMessage) {

        speaker1_id = speaker1Id;
        speaker1_name = speaker1Name;
        speaker2_id = speaker2Id;
        speaker2_name = speaker2Name;
        last_message = lastMessage;

    }

    public String getSpeaker1_id() {return speaker1_id;}
    public String getSpeaker1_name() {return speaker1_name;}
    public String getSpeaker2_id() {return speaker2_id;}
    public String getSpeaker2_name() {return speaker2_name;}
    public String getLast_message () {return last_message;}

}

