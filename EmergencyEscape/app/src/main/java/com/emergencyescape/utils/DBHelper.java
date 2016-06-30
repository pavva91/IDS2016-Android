package com.emergencyescape.utils;
/**
 * Created by Valerio Mattioli on 01/06/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.emergencyescape.MyApplication;
import com.emergencyescape.greendao.DaoMaster;
import com.emergencyescape.greendao.DaoSession;

/**
 * com.emergencyescape
 * DBHelper - crea DB
 */
public class DBHelper {
    private Context context;
    private String DB_NAME = "EmergencyEscape";
    private String TAG = this.getClass().getName();
    private SQLiteDatabase _db = null;
    private DaoSession _session = null;

    private Context getApplicationContext(){
        return MyApplication.context;
    }

    public SQLiteDatabase getDb(){
        return _db;
    }

    private DaoMaster getMaster() {
        if (_db == null) {
            context = getApplicationContext();
            _db = getDatabase(DB_NAME, false, context);
        }
        return new DaoMaster(_db);
    }

    public DaoSession getSession(boolean newSession) {
        if (newSession) {
            return getMaster().newSession();
        }
        if (_session == null) {
            _session = getMaster().newSession();
        }
        return _session;
    }

    private synchronized SQLiteDatabase getDatabase(String name, boolean readOnly, Context context) {
        String s = "getDB(" + name + ",readonly=" + (readOnly ? "true" : "false") + ")";
        try {
            readOnly = false;
            Log.i(TAG, s);
            SQLiteOpenHelper helper = new MyOpenHelper(context, name, null);
            if (readOnly) {
                return helper.getReadableDatabase();
            } else {
                return helper.getWritableDatabase();
            }
        } catch (Exception ex) {
            Log.e(TAG, s, ex);
            return null;
        } catch (Error err) {
            Log.e(TAG, s, err);
            return null;
        }
    }

    private class MyOpenHelper extends DaoMaster.OpenHelper {
        public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG, "Create DB-Schema (version "+Integer.toString(DaoMaster.SCHEMA_VERSION)+")");
            super.onCreate(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "Update DB-Schema to version: "+Integer.toString(oldVersion)+"->"+Integer.toString(newVersion));
        }
    }
}
