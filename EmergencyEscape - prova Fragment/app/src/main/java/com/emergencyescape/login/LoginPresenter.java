package com.emergencyescape.login;
/**
 * Created by Valerio Mattioli on 10/05/2016.
 */

import com.emergencyescape.model.UserLog;

/**
 * com.emergencyescape.login
 * LoginPresenter
 */
public class LoginPresenter {

    private UserLog userLog;

    public void loggingIn(){
        userLog.setLoggedIn();
    }

    public boolean getLogStatus(){
        return userLog.getLoggedIn();
    }
}
