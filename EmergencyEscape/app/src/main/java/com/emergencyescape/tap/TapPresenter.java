package com.emergencyescape.tap;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;

import com.emergencyescape.MyApplication;
import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.NodeDao;
import com.emergencyescape.greendao.UserDao;
import com.emergencyescape.main.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * com.emergencyescape.qr
 * QrPresenter
 */
public class TapPresenter extends CommonBehaviourPresenter<TapView> {

    private DaoSession daoSession = MyApplication.getSession();
    private static final String SQL_DISTINCT_ENAME = "SELECT DISTINCT "+NodeDao.Properties.Quote.columnName+" FROM "+NodeDao.TABLENAME+";";

    /**
     * Funzione che ritorna la lista di piani presenti nella mappa
     * @return
     */
    public List<String> getFloorList(){
        ArrayList<String> result = new ArrayList<>();
        Cursor c = daoSession.getDatabase().rawQuery(SQL_DISTINCT_ENAME, null);
        try{
            if (c.moveToFirst()) {
                do {
                    result.add(c.getString(0));
                } while (c.moveToNext());
            }
        } finally {
            c.close();
        }
        return result;
    }
}
