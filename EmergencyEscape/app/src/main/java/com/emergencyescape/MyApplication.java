package com.emergencyescape;

import android.app.Application;
import android.content.Context;

import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.server.ServerService;
import com.facebook.stetho.Stetho;


/**
 * Created by Valerio Mattioli on 12/05/16.
 *
 * A SINGLETON to decouple from UI thread
 * the Application runs first
 *  - Connect DB
 *  - Connect Server
 *  - Synchronize DB with Server
 */
public class MyApplication extends Application {
    // TODO: All'accensione fare il polling al server per vedere il lastUpdate della mappa

    private Server2Db server2Db;
    private ServerService serverService;
    private DaoSession daoSession;
    private DBHelper _dbHelper = new DBHelper();
    private static MyApplication _INSTANCE;
    public static Context context;

    public static MyApplication getInstance() {
        return _INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _INSTANCE = this;

        Stetho.initializeWithDefaults(this);
        context = getApplicationContext();

        serverService = new ServerService(); // Collegamento Server

        daoSession = getSession(); // Collegamento DB

        server2Db = new Server2Db();

    }


    public ServerService getServerService(){
        return serverService;
    }

    public DBHelper getDbHelper(){
        return _dbHelper;
    }

    public Server2Db getServer2Db(){
        return server2Db;
    }


    public static DaoSession getNewSession() {

        return getInstance()._dbHelper.getSession(true);
    }

    public static DaoSession getSession() {
        return getInstance()._dbHelper.getSession(false);
    }


}
