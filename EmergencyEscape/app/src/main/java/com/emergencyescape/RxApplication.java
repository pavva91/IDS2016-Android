package com.emergencyescape;

import android.app.Application;

import com.emergencyescape.server.Server2Db;
import com.emergencyescape.server.ServerService;


/**
 * Created by Valerio Mattioli on 12/05/16.
 *
 * A SINGLETON to decouple from UI thread
 * the Application runs first
 */
public class RxApplication extends Application {
    // TODO: All'accensione fare il polling al server per vedere il lastUpdate della mappa

    private ServerService serverService;

    private Server2Db server2Db;

    @Override
    public void onCreate() {
        super.onCreate();
        serverService = new ServerService();
        server2Db = new Server2Db(getServerService());
    }

    public ServerService getServerService(){
        return serverService;
    }


}
