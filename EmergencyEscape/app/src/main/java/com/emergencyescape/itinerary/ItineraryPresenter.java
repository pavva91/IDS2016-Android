package com.emergencyescape.itinerary;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import android.util.Log;

import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;
import com.emergencyescape.server.ServerService;
import com.emergencyescape.server.model.MapResponse;
import com.emergencyescape.server.model.MapsResponse;
import com.emergencyescape.server.model.Node;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * com.emergencyescape.qr
 * QrPresenter
 */
public class ItineraryPresenter extends CommonBehaviourPresenter<ItineraryView> implements PresenterInterface {

    private ItineraryActivity view;
    private ServerService service; // Retrofit
    private Subscription subscription; // RxJava

    public ItineraryPresenter(ItineraryActivity view, ServerService service){
        this.view = view;
        this.service = service;
    }

    @Override
    public void loadMaps(){//TODO: Quando ne avrai capito il workflow attivare caching

       /* Observable<FriendResponse> friendResponseObservable = (Observable<FriendResponse>) // type bind
                service.getPreparedObservable(preparedObservable, FriendResponse.class, false, false); // Vado a creare i due thread e ritorno l'Observable (prima verifico cache)
*/
        String mapName ="univpm";
        String token = "12m2t7oc43godndv767tkj9hue";
        Observable<MapResponse> mapResponseObservable = service.getAPI().getMap(mapName, token);
        Observable<Node> nodeObservable= service.getNodes(mapResponseObservable); // Deserializzo la risposta


        subscription = nodeObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter((map) -> map.getQuota()== 155) //Qua faccio il filtro dell'Observables
                .subscribe(new Observer<Node>() {
            @Override
            public void onCompleted() {
                Log.v("onCompleted","END");
            }

            @Override
            public void onError(Throwable e) {
                view.showRxFailure(e);
            }

            @Override
            public void onNext(Node response) {
                view.showMapsResults(response);

                Log.v("onNextIteration",response.getId().toString());
            }
        });
    }



    @Override
    public void rxUnSubscribe(){ // Avoid Memory leak
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

}
