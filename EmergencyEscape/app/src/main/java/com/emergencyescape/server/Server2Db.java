package com.emergencyescape.server;
/**
 * Created by Valerio Mattioli on 01/06/2016.
 */

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;

import com.emergencyescape.greendao.DaoMaster;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.server.model.MapResponse;
import com.emergencyescape.server.model.Node;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * com.emergencyescape.server
 * Server2Db
 */
public class Server2Db {
    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private ServerService service;
    private Subscription subscription;

    public Server2Db(ServerService service){
        this.service = service;
    }

    public void createDb(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "UNIVPMLabEscape.db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }


    public void loadMaps(){

        String mapName ="univpm"; //TODO: Collegare col model
        String token = "12m2t7oc43godndv767tkj9hue";

        Observable<MapResponse> mapResponseObservable = service.getAPI().getMap(mapName, token);
        Observable<Node> nodeObservable= service.getNodes(mapResponseObservable);


        subscription = nodeObservable
                .subscribe(new Observer<Node>() {
                    @Override
                    public void onCompleted() {
                        Log.v("onCompleted","END");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("errorServer2Db: ",e.toString());
                    }

                    @Override
                    public void onNext(Node response) {


                        Log.v("onNextIteration",response.getId().toString());
                    }
                });
    }
}
