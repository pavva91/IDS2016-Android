package com.emergencyescape.server;

/**
 * Created by Valerio Mattioli on 21/05/2016.
 */

import com.emergencyescape.server.model.MapResponse;
import com.emergencyescape.server.model.MapsResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * all the Service alls to use for the retrofit requests.
 * Forniscono risposte JSON "grezze"
 */
public interface ServerAPI {

    @GET("maps")
    Observable<MapsResponse> getMaps(@Query("token") String token);

    @GET("maps/{mapName}")
    Observable<MapResponse> getMap(@Path("mapName") String mapName, @Query("token") String token);

}
