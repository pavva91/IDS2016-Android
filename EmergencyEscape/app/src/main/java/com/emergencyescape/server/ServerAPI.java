package com.emergencyescape.server;

/**
 * Created by Valerio Mattioli on 21/05/2016.
 */

import com.emergencyescape.server.model.MapsResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * all the Service alls to use for the retrofit requests.
 * Forniscono risposte JSON "grezze"
 */
public interface ServerAPI {

    @GET("Maps.json")
    Observable<MapsResponse> getMaps();

}
