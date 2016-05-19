package com.emergencyescape.commonbehaviour;
/**
 * Created by Valerio Mattioli on 18/05/2016.
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.emergencyescape.SessionClass;
import com.emergencyescape.UtenteTable;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * com.emergencyescape
 * CommonBehaviourPresenter
 */
public class CommonBehaviourPresenter extends MvpBasePresenter<MvpView>{

    public boolean logout(Context AppContext){
        try {
            // TODO:Spostare in un task asincrono?
            SessionClass sc = SessionClass.getInstance();
            //canella dal db la session key
            UtenteTable ut = new UtenteTable(AppContext);
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
