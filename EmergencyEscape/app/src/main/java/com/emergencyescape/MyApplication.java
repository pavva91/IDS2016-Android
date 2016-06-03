package com.emergencyescape;

import android.app.Application;
import android.content.Context;

import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.server.ServerService;


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
        context = getApplicationContext();

        serverService = new ServerService(); // Collegamento Server

        daoSession = getSession(); // Collegamento DB

        server2Db = new Server2Db();

        server2Db.loadMaps();// TODO: Aggiungere controllo data

        server2Db.loadUser(); // TODO: Da eliminare una volta integrato login e registrazione corretti

        server2Db.loadNode();
    }

    public ServerService getServerService(){
        return serverService;
    }

    public DBHelper getDbHelper(){
        return _dbHelper;
    }


    public static DaoSession getNewSession() {

        return getInstance()._dbHelper.getSession(true);
    }

    public static DaoSession getSession() {
        return getInstance()._dbHelper.getSession(false);
    }


}
