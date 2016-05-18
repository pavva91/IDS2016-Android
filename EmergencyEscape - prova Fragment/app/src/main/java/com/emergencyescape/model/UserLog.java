package com.emergencyescape.model;
/**
 * Created by Valerio Mattioli on 10/05/2016.
 */

/**
 * com.emergencyescape.model
 * UserLog
 */
public class UserLog {

    private boolean loggedIn=false;

    public void setLoggedIn(){
        loggedIn=true;
    }

    public boolean getLoggedIn(){
        return loggedIn;
    }
}
