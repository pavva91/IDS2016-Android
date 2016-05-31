package com.emergencyescape.commonbehaviour;
/**
 * Created by Valerio Mattioli on 18/05/2016.
 */

import android.content.Context;
import android.util.Log;

import com.emergencyescape.SessionClass;
import com.emergencyescape.mymodel.table.User;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * com.emergencyescape
 * CommonBehaviourPresenter
 */
public abstract class CommonBehaviourPresenter<V extends MvpView> extends MvpBasePresenter<V>{

    public boolean logout(Context AppContext){
        try {
            // TODO:Spostare in un task asincrono?
            SessionClass sc = SessionClass.getInstance();
            //canella dal db la session key
            User ut = new User(AppContext);
            ut.destroySession(sc.getSessionvalue());
            //pulisce le variabili globali
            sc.clearSessionvalue();
            sc.clearSessionvalue();
            return true;
        }
        catch(Exception e){
            Log.e("LogoutError", e.toString());
            return false;
        }
    }


}
