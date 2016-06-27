package com.emergencyescape.commonbehaviour;
/**
 * Created by Valerio Mattioli on 18/05/2016.
 */

import android.content.Context;
import android.util.Log;

import com.emergencyescape.businesslogic.SessionClass;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * com.emergencyescape
 * CommonBehaviourPresenter
 */
public abstract class CommonBehaviourPresenter<V extends MvpView> extends MvpBasePresenter<V>{

    public boolean logout(Context AppContext)
    {
        try
        {
            SessionClass sc = SessionClass.getInstance();
            //pulisce le variabili globali
            sc.clearSessionKey(AppContext);
            sc.clearUser(AppContext);
            sc.clearServerKey(AppContext);
            sc.clearServerKey(AppContext);
            sc.clearDownloadFlag(AppContext);

            return true;
        }
        catch (Exception e)
        {
            Log.e("LogoutError", e.toString());
            return false;

        }
    }


}
