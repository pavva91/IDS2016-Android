package com.emergencyescape.rxretrofit;

/**
 * Created by Valerio Mattioli on 21/05/2016.
 */

import retrofit2.http.GET;
import rx.Observable;

/**
 * all the Service alls to use for the retrofit requests.
 */
public interface NetworkAPI {

    @GET("FriendLocations.json") //real endpoint
    Observable<FriendResponse> getFriendsObservable();

}
