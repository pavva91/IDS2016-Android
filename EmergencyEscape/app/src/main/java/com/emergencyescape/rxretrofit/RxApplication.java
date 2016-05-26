package com.emergencyescape.rxretrofit;

import android.app.Application;


/**
 * Created by cteegarden on 1/26/16.
 *
 * A SINGLETON to decouple from UI thread
 */
public class RxApplication extends Application {

    private NetworkService networkService;

    @Override
    public void onCreate() {
        super.onCreate();
        networkService = new NetworkService();
    }

    public NetworkService getNetworkService(){
        return networkService;
    }


}
