package com.emergencyescape.retrofit;
/**
 * Created by Valerio Mattioli on 20/05/2016.
 */


import com.emergencyescape.MapResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * com.emergencyescape
 * GetMapApi
 */
public interface GetMapApi { //TODO: Implementare in Retrofit RxJava
    @GET("/progetto/maps")
    Observable<MapResponse> getMaps(); //Ottieni elenco mappe disponibili
}
