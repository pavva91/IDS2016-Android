package com.emergencyescape.retrofit;
/**
 * Created by Valerio Mattioli on 20/05/2016.
 */

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * com.emergencyescape
 * GetMapApi
 */
public interface GetMapApi { //TODO: Implementare in Retrofit RxJava
    @GET("/progetto/maps")
    vObservable<List<MapServer>> mapsList(
        @Query("token") String userToken,
        Callback<MapData> callback;
        );
}
