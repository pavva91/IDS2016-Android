package com.emergencyescape.main;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import com.emergencyescape.MyApplication;
import com.emergencyescape.utils.Server2Db;
import com.emergencyescape.businesslogic.ServerConnection;
import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;

/**
 * com.emergencyescape.qr
 * QrPresenter
 */
public class MainPresenter extends CommonBehaviourPresenter<MainView> {

    Server2Db server2Db = MyApplication.getInstance().getServer2Db();
    ServerConnection serverConnection = ServerConnection.getInstance(MyApplication.context);

    public void loadServer2Db(){ // Metodi che prima giravano in MyApplication
        /*
        server2Db.setToken();

        server2Db.loadMaps();
        server2Db.loadNodes();
        server2Db.loadEdges();
        server2Db.loadImages();
        server2Db.dropMapsTable();
        serverConnection.getUsersList();
        */
        server2Db.setToken();
        serverConnection.getElencoMappe(server2Db.getToken());
        server2Db.initializeDb();
        //server2Db.dropMapsTable();
        serverConnection.getUsersList();
    }
}
