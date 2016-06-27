package com.emergencyescape.commonbehaviour;
/**
 * Created by Valerio Mattioli on 18/05/2016.
 */
import android.content.Context;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * com.emergencyescape
 * CommonBehaviourPresenter
 */
public abstract class CommonBehaviourPresenter<V extends MvpView> extends MvpBasePresenter<V>{

    public boolean logout(Context AppContext){
        return true;
    }
}
