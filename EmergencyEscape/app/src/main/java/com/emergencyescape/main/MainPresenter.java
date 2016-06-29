package com.emergencyescape.main;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import com.emergencyescape.MyApplication;
import com.emergencyescape.Server2Db;
import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;

/**
 * com.emergencyescape.qr
 * QrPresenter
 */
public class MainPresenter extends CommonBehaviourPresenter<MainView> {

    Server2Db server2Db = MyApplication.getInstance().getServer2Db();

    public void loadServer2Db(){ // Metodi che prima giravano in MyApplication
        server2Db.setToken();
        server2Db.loadMaps();
        server2Db.loadNodes();
        server2Db.loadEdges();
        server2Db.loadImages();
    }
}
