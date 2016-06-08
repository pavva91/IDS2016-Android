package com.emergencyescape;
/**
 * Created by Valerio Mattioli on 01/06/2016.
 */

import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.EdgeDao;
import com.emergencyescape.greendao.Maps;
import com.emergencyescape.greendao.MapsDao;
import com.emergencyescape.greendao.NodeDao;
import com.emergencyescape.greendao.UserDao;
import com.emergencyescape.server.ServerService;
import com.emergencyescape.server.model.Edge;
import com.emergencyescape.server.model.MapResponse;
import com.emergencyescape.server.model.Node;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

import static java.lang.String.format;

/**
 * com.emergencyescape
 * Server2Db
 * Classe che cura la sincronizzazione del DB con i dati del server
 */
public class Server2Db {

    private String LOG = this.toString();
    private Subscription subscription;
    private ServerService service = MyApplication.getInstance().getServerService();
    private DaoSession daoSession = MyApplication.getSession();
    private NodeDao nodeDao = daoSession.getNodeDao();
    private EdgeDao edgeDao = daoSession.getEdgeDao();
    private MapsDao mapsDao = daoSession.getMapsDao();
    private UserDao userDao = daoSession.getUserDao();
    private DBHelper dbHelper = MyApplication.getInstance().getDbHelper();
    private SQLiteDatabase db = null;
    private Cursor cursor;


    public void loadNodes(){

        setDb();
        NodeDao.dropTable(db,true);
        NodeDao.createTable(db,false);


        String mapName ="univpm"; //TODO: Collegare col model
        String token = "12m2t7oc43godndv767tkj9hue";



        Observable<MapResponse> mapResponseObservable = service.getAPI().getMap(mapName,token);
        Observable<Node> nodeObservable= service.getNodes(mapResponseObservable); // Deserializzo la risposta


        subscription = nodeObservable
                .subscribe(new Observer<Node>() {
                    @Override
                    public void onCompleted() {
                        Log.v("onCompleted","END");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG, e.toString());
                    }

                    @Override
                    public void onNext(Node response) {
                        Log.v("onNextIterationNodeId",response.getId().toString());


                        // Salvo i nodi presi dal server nel DB
                        com.emergencyescape.greendao.Node node = new com.emergencyescape.greendao.Node(response.getId());
                        node.setCode(response.getCode());
                        node.setDescription(response.getDescr());
                        node.setQuote(response.getQuota());
                        node.setX(response.getX());
                        node.setY(response.getY());
                        node.setWidth(response.getWidth());
                        node.setType(response.getType());
                        //node.setMapId(response.getMapName()) // TODO: Sistemare questa cosa

                        nodeDao.insert(node);


                    }
                });
    }

    public void loadEdges(){

        setDb();
        EdgeDao.dropTable(db,true);
        EdgeDao.createTable(db,false);


        String mapName ="univpm"; //TODO: Collegare col model
        String token = "12m2t7oc43godndv767tkj9hue";



        Observable<MapResponse> mapResponseObservable = service.getAPI().getMap(mapName,token);
        Observable<Edge> edgeObservable= service.getEdges(mapResponseObservable); // Deserializzo la risposta


        subscription = edgeObservable
                .subscribe(new Observer<Edge>() {
                    @Override
                    public void onCompleted() {
                        Log.v("onCompleted","END");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG, e.toString());
                    }

                    @Override
                    public void onNext(Edge response) {
                        Log.v("onNextIterationEdgeFrom",response.getFrom());
                        Log.v("onNextIterationEdgeTo",response.getTo());

                        List<com.emergencyescape.greendao.Node> nodeDeparture = nodeDao.queryBuilder().where(NodeDao.Properties.Code.eq(response.getFrom())).list();
                        List<com.emergencyescape.greendao.Node> nodeDestination = nodeDao.queryBuilder().where(NodeDao.Properties.Code.eq(response.getTo())).list();

                        com.emergencyescape.greendao.Edge edge = new com.emergencyescape.greendao.Edge();
                        edge.setDepartureToOne(nodeDeparture.get(0));
                        edge.setDestinationToOne(nodeDestination.get(0));
                        edge.setV(response.getV());
                        edge.setI(response.getI());
                        edge.setC(response.getC());
                        edge.setLos(response.getLos());
                        edge.setLength(response.getLength());
                        edge.setSurface(response.getArea());

                        edgeDao.insert(edge);


                    }
                });
    }

    public void loadMaps(){

        setDb();
        MapsDao.dropTable(db,true);
        MapsDao.createTable(db,false); // Bypassa l'errore, altrimenti andrebbe ad aggiungere gli stessi campi
        // duplicando gli id, così io ogni volta cancello e ricreo la tabella
        // TODO: Sostituire con un controllo per vedere se andare a popolare la tabella

        // TODO: Collegare col Model
        String token = "12m2t7oc43godndv767tkj9hue";

        Observable<List<MapResponse>> mapsResponseObservable = service.getAPI().getMaps(token);
        Observable<List<MapResponse>> mapsObservable = service.getMaps(mapsResponseObservable);

        subscription = mapsObservable
                .subscribe(new Observer<List<MapResponse>>() {

                    @Override
                    public void onCompleted() {
                        Log.v("onCompleted", "END");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG, e.toString());
                    }

                    @Override
                    public void onNext(List<MapResponse> vectorMapResponse) {
                        for (MapResponse singleMapResponse : vectorMapResponse) { // ciclo la risposta JSON perchè è un vettore
                            Log.v("onNextIteration", singleMapResponse.getName().toString());

                            // Salvo le mappe prese dal server nel DB
                            com.emergencyescape.greendao.Maps maps = new com.emergencyescape.greendao.Maps();
                            maps.setName(singleMapResponse.getName());

                            mapsDao.insert(maps); // TODO: Sarebbe meglio creare un vettore e inserire nel DB tutto il vettore (performance)
                        }
                    }
                });

    }

    public void loadUser(){
        setDb();
        UserDao.dropTable(db,true);
        UserDao.createTable(db,false); // Bypassa l'errore, altrimenti andrebbe ad aggiungere gli stessi campi
        // duplicando gli id, così io ogni volta cancello e ricreo la tabella
        // TODO: Sostituire con un controllo per vedere se andare a popolare la tabella

        com.emergencyescape.greendao.User user = new com.emergencyescape.greendao.User();
        user.setName("vale");
        user.setPassword("123");
        user.setToken("12m2t7oc43godndv767tkj9hue");

        userDao.insert(user);
    }

   /* private boolean mapChanged(MapResponse map){
        boolean mapChanged = false;
        setDb();
        Maps maps = mapsDao.queryBuilder()
                .where(MapsDao.Properties.FirstName.eq("Joe"))
                .orderAsc(MapsDao.Properties.LastName)
                .list();; // select *


        return mapChanged;

    }*/

   /* private int getMapToArrayXml(int index) {
        TypedArray mapId = MyApplication.context.getResources().getStringArray(R.array.map_id);

        WriteFi

        List<Maps> maps = mapsDao.loadAll(); // Prendo la lista di mappe e la voglio stampare in array.xml



        int id = fileNames.getResourceId(index, -1); //-1 is default if nothing is found (we don't care)
        fileNames.recycle();
        return id;
    }*/

    private void setDb(){
        this.db = dbHelper.getDb();
    }



}
