package com.emergencyescape.tap;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import android.database.Cursor;
import android.graphics.drawable.Drawable;

import com.emergencyescape.MyApplication;
import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.NodeDao;
import com.emergencyescape.greendao.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * com.emergencyescape.qr
 * QrPresenter
 */
public class MapPresenter extends CommonBehaviourPresenter<TapView> {

    private DaoSession daoSession = MyApplication.getSession();

    public Drawable getFloorImage(String floor){ // TODO: Andare a prendere immagini precedentemente caricate
        Drawable drawable = MyApplication.context.getResources().getDrawable(R.drawable.q145);
        if(floor.equalsIgnoreCase("Quota 145")) {
            drawable = MyApplication.context.getResources().getDrawable(R.drawable.q145);

        }else if(floor.equalsIgnoreCase("Quota 150")) {
            drawable = MyApplication.context.getResources().getDrawable(R.drawable.q150);

        }else if(floor.equalsIgnoreCase("Quota 155")) {
            drawable = MyApplication.context.getResources().getDrawable(R.drawable.q155);

        }
        return drawable;
    }
}
