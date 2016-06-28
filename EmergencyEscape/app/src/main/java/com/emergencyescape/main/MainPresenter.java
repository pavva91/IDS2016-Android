package com.emergencyescape.main;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import com.emergencyescape.MyApplication;
import com.emergencyescape.Server2Db;
import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;
import com.emergencyescape.qr.QrActivity;

/**
 * com.emergencyescape.qr
 * QrPresenter
 */
public class MainPresenter extends CommonBehaviourPresenter<MainView> {

    Server2Db server2Db = MyApplication.getInstance().getServer2Db();

    public void loadServer2Db(){ // Metodi che prima giravano in MyApplication
        server2Db.setToken();
        server2Db.loadMaps();// TODO: Aggiungere controllo data
        server2Db.loadUser(); // TODO: Da eliminare una volta integrato login e registrazione corretti
        server2Db.loadNodes();
        server2Db.loadEdges();
        server2Db.loadImages(); // se attivo downloadFloorImages() non carica imagesDao
        //server2Db.downloadFloorImages();
    }
}
