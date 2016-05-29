package com.emergencyescape.itinerary;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import android.util.Log;

import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;
import com.emergencyescape.server.ServerService;
import com.emergencyescape.server.model.Maps;
import com.emergencyescape.server.model.MapsResponse;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

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


        Observable<MapsResponse> mapsResponseObservable = service.getAPI().getMaps();
        Observable<Maps> mapsObservable= service.getMap(mapsResponseObservable);


        subscription = mapsObservable.subscribe(new Observer<Maps>() { //Qua faccio il filtro dell'Observables
            @Override
            public void onCompleted() {
                Log.v("onCompleted","END");
            }

            @Override
            public void onError(Throwable e) {
                view.showRxFailure(e);
            }

            @Override
            public void onNext(Maps response) {
                view.showMapsResults(response);

                Log.v("onNextIteration",response.getName());
            }
        });
    }



    @Override
    public void rxUnSubscribe(){ // Avoid Memory leak
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

}
