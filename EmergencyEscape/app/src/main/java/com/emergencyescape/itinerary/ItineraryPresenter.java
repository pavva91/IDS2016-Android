package com.emergencyescape.itinerary;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import android.util.Log;

import com.emergencyescape.MyApplication;
import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.NodeDao;
import com.emergencyescape.greendao.User;
import com.emergencyescape.greendao.UserDao;
import com.emergencyescape.server.ServerService;
import com.emergencyescape.server.model.MapResponse;
import com.emergencyescape.server.model.Node;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * com.emergencyescape.qr
 * QrPresenter
 */
public class ItineraryPresenter extends CommonBehaviourPresenter<ItineraryView> implements ItineraryPresenterInterface {

    DaoSession daoSession = MyApplication.getSession();
    NodeDao nodeDao = daoSession.getNodeDao();
    UserDao userDao = daoSession.getUserDao();

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
                Log.e("Errore", e.toString());
            }

            @Override
            public void onNext(Node response) {
                Log.v("onNextIteration",response.getId().toString());
                view.showMapsResults(response);

                // Salvo i nodi presi dal server nel DB
                com.emergencyescape.greendao.Node node = new com.emergencyescape.greendao.Node(response.getId());
                node.setCode(response.getCode());
                node.setDescription(response.getDescr());
                node.setQuote(response.getQuota());
                node.setX(response.getX());
                node.setY(response.getY());
                node.setWidth(response.getWidth());
                node.setType(response.getType());
                //node.setMapId(response.getMapName()) // TODO: Sistemare questa cosa (

                nodeDao.insert(node);


            }
        });
    }



    @Override
    public void rxUnSubscribe(){ // Avoid Memory leak
        if(subscription!=null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    @Override
    public String getDeparture() {
        String userDeparture = "";
        List<User> allUser = userDao.loadAll();
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase("vale")){
                userDeparture = singleUser.getDepartureToOneUser().getCode();
            }
        }
        return userDeparture;
    }

    @Override
    public String getDestination() {
        String userDestination = "";
        List<User> allUser = userDao.loadAll();
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase("vale")){
                userDestination = singleUser.getDestinationToOneUser().getCode();
            }
        }
        return userDestination;
    }
}
