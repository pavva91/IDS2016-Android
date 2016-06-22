package com.emergencyescape.server;

import android.app.Application;


/**
 * Created by cteegarden on 1/26/16.
 *
 * A SINGLETON to decouple from UI thread
 */
public class RxApplication extends Application {

    private ServerService serverService;

    @Override
    public void onCreate() {
        super.onCreate();
        serverService = new ServerService();
    }

    public ServerService getServerService(){
        return serverService;
    }


}
