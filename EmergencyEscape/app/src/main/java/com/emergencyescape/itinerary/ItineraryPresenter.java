package com.emergencyescape.itinerary;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;
import com.emergencyescape.qr.QrActivity;
import com.emergencyescape.rxretrofit.FriendResponse;
import com.emergencyescape.rxretrofit.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * com.emergencyescape.qr
 * QrPresenter
 */
public class ItineraryPresenter extends CommonBehaviourPresenter<ItineraryView> implements PresenterInterface {

    private ItineraryActivity view;
    private NetworkService service; // Retrofit
    private Subscription subscription; // RxJava

    public ItineraryPresenter(ItineraryActivity view, NetworkService service){
        this.view = view;
        this.service = service;
    }

    @Override
    public void loadRxData(){
        // TODO: Capire questo passaggio:
        Observable<?> preparedObservable = service.getAPI().getFriendsObservable();
        Observable<FriendResponse> friendResponseObservable = (Observable<FriendResponse>) // type bind
                service.getPreparedObservable(preparedObservable, FriendResponse.class, false, false);
        subscription = friendResponseObservable.subscribe(new Observer<FriendResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.showRxFailure(e);
            }

            @Override
            public void onNext(FriendResponse response) {
                view.showRxResults(response);
            }
        });
    }



    @Override
    public void rxUnSubscribe(){ // Avoid Memory leak
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

}
