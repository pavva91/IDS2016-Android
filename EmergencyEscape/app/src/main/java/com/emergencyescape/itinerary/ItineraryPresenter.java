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

        String mapName ="univpm"; //TODO: Collegare col model
        String token = "12m2t7oc43godndv767tkj9hue";

        Observable<MapResponse> mapResponseObservable = service.getAPI().getMap(mapName, token);
        Observable<Node> nodeObservable= service.getNodes(mapResponseObservable); // Deserializzo la risposta


        subscription = nodeObservable
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
                view.showMapsResults(response); //TODO: Salvare nel DB

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
