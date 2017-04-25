package com.brumor.chatlenge;

/**
 * Created by pbric on 01/03/2017.
 */

public class Chatlenge {

    public String username;

    public String last_chatlenge;

    public Chatlenge(String Username, String Chatlenge ){

        username = Username;

        last_chatlenge = Chatlenge;

    }

    public String getUsername() {return username;}

    public String getLast_chatlenge () {return last_chatlenge;}
}
