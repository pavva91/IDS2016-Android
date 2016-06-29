package com.emergencyescape;
/**
 * Created by Valerio Mattioli on 01/06/2016.
 */

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.emergencyescape.businesslogic.SessionClass;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.EdgeDao;
import com.emergencyescape.greendao.ImageDao;
import com.emergencyescape.greendao.Maps;
import com.emergencyescape.greendao.MapsDao;
import com.emergencyescape.greendao.NodeDao;
import com.emergencyescape.greendao.User;
import com.emergencyescape.greendao.UserDao;
import com.emergencyescape.server.ServerService;
import com.emergencyescape.server.model.Edge;
import com.emergencyescape.server.model.Image;
import com.emergencyescape.server.model.MapResponse;
import com.emergencyescape.server.model.Node;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import de.greenrobot.dao.query.QueryBuilder;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

import static java.lang.String.format;

/**
 * com.emergencyescape
 * Server2Db
 * Classe che cura la sincronizzazione del DB con i dati del server
 * TODO: Rendere il tutto asincrono
 */
public class Server2Db {

    private String LOG = this.toString();
    private Subscription subscription;
    private Subscription subscriptionMaps;
    private ServerService service = MyApplication.getInstance().getServerService();
    private DaoSession daoSession = MyApplication.getSession();
    private NodeDao nodeDao = daoSession.getNodeDao();
    private EdgeDao edgeDao = daoSession.getEdgeDao();
    private MapsDao mapsDao = daoSession.getMapsDao();
    private UserDao userDao = daoSession.getUserDao();
    private ImageDao imageDao = daoSession.getImageDao();
    private DBHelper dbHelper = MyApplication.getInstance().getDbHelper();
    private SQLiteDatabase db = null;

    private String mapName ="univpm"; //TODO: Collegare col model
    private String token = "";

    private void networkConnectionNode(Observable<Node> mapResponseObservable){
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(MyApplication.context); // TODO: Usare shared Betta
        Subscription subscriptionTest;
        subscriptionTest = mapResponseObservable
                .subscribe(new Observer<Node>() {
                    SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(MyApplication.context);
                    @Override
                    public void onCompleted() {
                         // TODO: Usare shared Betta
                        SharedPreferences.Editor editor = wmbPreference.edit();
                        editor.putBoolean("NETWORK_CONNECTION", true);
                        editor.commit();
                    }

                    @Override
                    public void onError(Throwable e) {
                            // TODO: Usare shared Betta
                            SharedPreferences.Editor editor = wmbPreference.edit();
                            editor.putBoolean("NETWORK_CONNECTION", false);
                            editor.commit();
                    }

                    @Override
                    public void onNext(Node response) {
                        SharedPreferences.Editor editor = wmbPreference.edit();
                        editor.putBoolean("NETWORK_CONNECTION", true);
                        editor.commit();
                    }
                });
    }
    private boolean networkConnection(Observable<MapResponse> mapResponseObservable){
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(MyApplication.context); // TODO: Usare shared Betta
        Subscription subscriptionTest;
        subscriptionTest = mapResponseObservable
                .subscribe(new Observer<MapResponse>() {
                    SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(MyApplication.context);
                    @Override
                    public void onCompleted() {
                        // TODO: Usare shared Betta
                        SharedPreferences.Editor editor = wmbPreference.edit();
                        editor.putBoolean("NETWORK_CONNECTION", true);
                        editor.commit();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: Usare shared Betta
                        SharedPreferences.Editor editor = wmbPreference.edit();
                        editor.putBoolean("NETWORK_CONNECTION", false);
                        editor.commit();
                    }

                    @Override
                    public void onNext(MapResponse response) {
                    }
                });
        boolean networkConnection = wmbPreference.getBoolean("NETWORK_CONNECTION", true);
        return networkConnection;
    }

    private boolean networkConnectionMaps(Observable<List<MapResponse>> mapsResponseObservable){
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(MyApplication.context); // TODO: Usare shared Betta
        Subscription subscriptionTest;
        subscriptionTest = mapsResponseObservable
                .subscribe(new Observer<List<MapResponse>>() {

                    @Override
                    public void onCompleted() {
                        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(MyApplication.context); // TODO: Usare shared Betta
                        SharedPreferences.Editor editor = wmbPreference.edit();
                        editor.putBoolean("NETWORK_CONNECTION", true);
                        editor.commit();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof java.net.ConnectException){
                            SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(MyApplication.context); // TODO: Usare shared Betta
                            SharedPreferences.Editor editor = wmbPreference.edit();
                            editor.putBoolean("NETWORK_CONNECTION", false);
                            editor.commit();
                        }
                    }

                    @Override
                    public void onNext(List<MapResponse> vectorMapResponse) {
                        for (MapResponse singleMapResponse : vectorMapResponse) {

                        }
                    }
                });
        boolean networkConnection = wmbPreference.getBoolean("NETWORK_CONNECTION", true);
        return networkConnection;
    }

    public void setToken(){
        SessionClass sessionClass = SessionClass.getInstance();
        token = sessionClass.getServerKey(MyApplication.context);
    }

    public void downloadFromUrl(String DownloadUrl, String fileName) { // TODO: Sistemare query DB

        try {
            File root = Environment.getExternalStorageDirectory();

            File dir = new File (root.getAbsolutePath() + "/floor");
            if(dir.exists()==false) {
                dir.mkdirs();
            }

            URL url = new URL(DownloadUrl); //you can write here any link
            File file = new File(dir, fileName);

            long startTime = System.currentTimeMillis();
            Log.v("DownloadManager", "download begining");
            Log.v("DownloadManager", "download url:" + url);
            Log.v("DownloadManager", "downloaded file name:" + fileName);

           /* Open a connection to that URL. */
            URLConnection ucon = url.openConnection(); // Ok, fin qui funziona

           /*
            * Define InputStreams to read from the URLConnection.
            */
            InputStream is = ucon.getInputStream(); // TODO: Qui non funziona, sistemare
            BufferedInputStream bis = new BufferedInputStream(is);

           /*
            * Read bytes to the Buffer until there is nothing more to read(-1).
            */
            ByteArrayOutputStream baf = new ByteArrayOutputStream();
            byte[]data = new byte[5000];
            int current = 0;
            while ((current = bis.read(data,0,data.length)) != -1) {
                baf.write(data,0,current);
            }


           /* Convert the Bytes read to a String. */
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baf.toByteArray());
            fos.flush();
            fos.close();
            Log.d("DownloadManager", "download ready in" + ((System.currentTimeMillis() - startTime) / 1000) + " sec");

        } catch (IOException e) {
            Log.d("DownloadManager", "Error: " + e);
        }

    }

    public void downloadFloorImages(){
        List<com.emergencyescape.greendao.Image> imageList;
        imageList = imageDao.loadAll(); // loadAll() = 0;

        for(com.emergencyescape.greendao.Image imageSingle : imageList){
            Integer quote = imageSingle.getQuote();
            this.downloadFromUrl(imageSingle.getUrl(), quote.toString() + ".png");
        }
    }

    public void loadNodes(){

        Observable<MapResponse> mapResponseObservable = service.getAPI().getMap(mapName,token);
        Observable<Node> nodeObservable= service.getNodes(mapResponseObservable);
            /*setDb();
            NodeDao.dropTable(db,true);
            NodeDao.createTable(db,false);*/

             // Deserializzo la risposta
            subscription = nodeObservable
                    .subscribe(new Observer<Node>() {
                        @Override
                        public void onCompleted() {
                            Log.v("onCompleted","END");
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (e instanceof java.net.ConnectException){

                            }
                            Log.e(LOG, "Node: " + e.toString());
                        }

                        @Override
                        public void onNext(Node response) {
                            Log.v("onNextIterationNodeId",response.getId().toString());

                            List<com.emergencyescape.greendao.Maps> mapsList = mapsDao.queryBuilder()
                                    .where(MapsDao.Properties.Name.eq(response.getMapName()))
                                    .list();

                            // Salvo i nodi presi dal server nel DB
                            com.emergencyescape.greendao.Node node = new com.emergencyescape.greendao.Node(response.getId());
                            node.setCode(response.getCode());
                            node.setDescription(response.getDescr());
                            node.setQuote(response.getQuota());
                            node.setX(response.getX());
                            node.setY(response.getY());
                            node.setWidth(response.getWidth());
                            node.setType(response.getType());
                            node.setMapId(mapsList
                                    .get(0)
                                    .getId()); // TODO: Sistemare questa cosa

                            loadNodeTable();

                            boolean insertData = true;

                            List<com.emergencyescape.greendao.Node> nodeList = nodeDao.loadAll();
                            for (com.emergencyescape.greendao.Node node1 : nodeList){
                                if (node1.getCode().equals(node.getCode())){
                                    insertData = false;
                                    break;
                                }
                            }

                            if(insertData){
                                nodeDao.insert(node);
                            }

                        }
                    });

    }

    public void confrontDate(){
        Observable<MapResponse> mapResponseObservable = service.getAPI().getMap(mapName,token);
        subscription = mapResponseObservable
                .subscribe(new Observer<MapResponse>() {
                    SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(MyApplication.context); // TODO: Usare shared Betta

                    @Override
                    public void onCompleted() {
                        Log.v("onCompleted", "END");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG + " Edge: ", e.toString());
                    }

                    @Override
                    public void onNext(MapResponse response) {
                        String JSONDate = response.getLastUpdateMap();
                        SharedPreferences.Editor editor = wmbPreference.edit();
                        editor.putString("JSON_DATE", JSONDate);
                        editor.commit();

                    }
                });
    }

    public void loadEdges(){
        Observable<MapResponse> mapResponseObservable = service.getAPI().getMap(mapName,token);
        /*
            setDb();
            EdgeDao.dropTable(db,true);
            EdgeDao.createTable(db,false);
            */
            Observable<Edge> edgeObservable = service.getEdges(mapResponseObservable); // Deserializzo la risposta


            subscription = edgeObservable
                    .subscribe(new Observer<Edge>() {
                        @Override
                        public void onCompleted() {
                            Log.v("onCompleted", "END");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(LOG + " Edge: ", e.toString());
                        }

                        @Override
                        public void onNext(Edge response) {
                            Log.v("onNextIterationEdgeFrom", response.getFrom());
                            Log.v("onNextIterationEdgeTo", response.getTo());

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

                            edge.setEm_cost(response.getEmgcost());
                            edge.setNo_em_cost(response.getLength());


                            loadEdgeTable();

                            boolean insertData = true;

                            List<com.emergencyescape.greendao.Edge> edgeList = edgeDao.loadAll();
                            for (com.emergencyescape.greendao.Edge edge1 : edgeList){
                                if (edge1.getDepartureId()==edge.getDepartureId() && edge1.getDestinationId()==edge.getDestinationId()){
                                    insertData = false;
                                    break;
                                }
                            }

                            if(insertData){
                                edgeDao.insert(edge);
                            }
                        }
                    });

    }

    public void loadMaps(){
        Observable<List<MapResponse>> mapsResponseObservable = service.getAPI().getMaps(token);

            Observable<List<MapResponse>> mapsObservable = service.getMaps(mapsResponseObservable);

            subscriptionMaps = mapsObservable
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
                            setDb();
                            MapsDao.dropTable(db,true);
                            MapsDao.createTable(db,false);
                            for (MapResponse singleMapResponse : vectorMapResponse) { // ciclo la risposta JSON perchè è un vettore
                                Log.v("onNextIteration", singleMapResponse.getName().toString());

                                // Salvo le mappe prese dal server nel DB
                                com.emergencyescape.greendao.Maps maps = new Maps();
                                maps.setName(singleMapResponse.getName());
                                // Trasformo String Data JSON in util.Date Data greenDAO
                                try {
                                    maps.setLastUpdate(ISO8601DateParser.parse(singleMapResponse.getLastUpdateMap()));
                                } catch (java.text.ParseException e) {
                                    System.out.println(e.toString());
                                }
                                mapsDao.insert(maps); // TODO: Sarebbe meglio creare un vettore e inserire nel DB tutto il vettore (performance)
                            }
                        }
                    });

    }

    public void loadUserTable(){
        setDb();
        UserDao.createTable(db,true);
    }

    public void dropUserTable(){
        setDb();
        UserDao.dropTable(db,true);
    }

    public void loadNodeTable(){
        setDb();
        NodeDao.createTable(db,true);
    }

    public void dropNodeTable(){
        setDb();
        NodeDao.dropTable(db,true);
    }

    public void loadEdgeTable(){
        setDb();
        EdgeDao.createTable(db,true);
    }

    public void dropEdgeTable(){
        setDb();
        EdgeDao.dropTable(db,true);
    }

    public void loadImageTable(){
        setDb();
        ImageDao.createTable(db,true);
    }

    public void dropImageTable(){
        setDb();
        ImageDao.dropTable(db,true);
    }

    public void loadMapsTable(){
        setDb();
        MapsDao.createTable(db,true);
    }

    public void dropMapsTable(){
        setDb();
        MapsDao.dropTable(db,true);
    }

    public void addUser(String name, String password, String salt){
        loadUserTable();
        com.emergencyescape.greendao.User user = new com.emergencyescape.greendao.User();
        user.setName(name);
        user.setPassword(password);
        user.setSalt(salt);
        Log.v("UserName: ", user.getName());
        Log.v("Password: ", user.getPassword());
        Log.v("Salt: ", user.getSalt());


        boolean insertData = true;

        List<User> userList = userDao.loadAll();
        for (User user1 : userList){
            if (user1.getPassword().equals(user.getPassword()) && user1.getName().equals(user.getName())){
                insertData = false;
                break;
            }
        }

        if(insertData){
            userDao.insert(user);
        }
    }

    public void loadImages(){
        Observable<MapResponse> mapResponseObservable = service.getAPI().getMap(mapName,token);
        /*
            setDb();
            ImageDao.dropTable(db, true);
            ImageDao.createTable(db, false);
            */
            Observable<Image> imageObservable = service.getImages(mapResponseObservable); // Deserializzo la risposta


            subscription = imageObservable
                    .subscribe(new Observer<Image>() {
                        @Override
                        public void onCompleted() {
                            Log.v("onCompleted", "END");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(LOG, "Image: " + e.toString());

                        }

                        @Override
                        public void onNext(Image response) {
                            Log.v("onNextIterFloorImage", response.getQuota() + " image url: " + response.getUrl());

                            List<com.emergencyescape.greendao.Maps> mapsList = mapsDao.queryBuilder()
                                    .where(MapsDao.Properties.Name.eq(response.getMap()))
                                    .list();

                            // Salvo i nodi presi dal server nel DB
                            com.emergencyescape.greendao.Image image = new com.emergencyescape.greendao.Image();

                            image.setUrl(response.getUrl());
                            image.setQuote(response.getQuota());
                            image.setMapId(mapsList
                                    .get(0)
                                    .getId());

                            loadImageTable();

                            boolean insertData = true;

                            List<com.emergencyescape.greendao.Image> imageList = imageDao.loadAll();
                            for (com.emergencyescape.greendao.Image image1 : imageList){
                                if (image1.getQuote()==image.getQuote()){
                                    insertData = false;
                                    break;
                                }
                            }

                            if(insertData){
                                imageDao.insert(image);
                            }



                            // downloadFromUrl(image.getUrl(), Integer.toString(image.getQuote()) + ".png"); //TODO: Sistemare Download
                        }
                    });
    }

    public void initializeDb(){
        Observable<MapResponse> mapResponseObservableRaw = service.getAPI().getMap(mapName,token);
        Observable<MapResponse> mapResponseObservable = service.getMap(mapResponseObservableRaw);

        subscription = mapResponseObservable
                .subscribe(new Observer<MapResponse>() {

                    @Override
                    public void onCompleted() {
                        Log.v("onCompleted", "END");
                        SessionClass sessionClass = SessionClass.getInstance();
                        sessionClass.setDownloadMapFlag(MyApplication.context);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG, e.toString());
                    }

                    @Override
                    public void onNext(MapResponse mapResponse) {
                            List<Node> nodes = mapResponse.getNodes();
                            List<Edge> edges = mapResponse.getEdges();
                            List<Image> images = mapResponse.getImages();
                            List<Maps> mapsList = mapsDao.loadAll();

                            loadNodeTable();
                            loadEdgeTable();
                            loadImageTable();

                        ArrayList<com.emergencyescape.greendao.Node> nodeArrayList = new ArrayList<>();
                                for (Node node : nodes){
                                    Log.v("onNextIterationNodeId",node.getId().toString());
                                    List<Maps> mapses = mapsDao.queryBuilder()
                                            .where(MapsDao.Properties.Name.eq(node.getMapName()))
                                            .list();

                                    // Salvo i nodi presi dal server nel DB
                                    com.emergencyescape.greendao.Node nodeOrm = new com.emergencyescape.greendao.Node(node.getId());
                                    nodeOrm.setCode(node.getCode());
                                    nodeOrm.setDescription(node.getDescr());
                                    nodeOrm.setQuote(node.getQuota());
                                    nodeOrm.setX(node.getX());
                                    nodeOrm.setY(node.getY());
                                    nodeOrm.setWidth(node.getWidth());
                                    nodeOrm.setType(node.getType());
                                    nodeOrm.setMapId(mapses
                                            .get(0)
                                            .getId()); // TODO: Sistemare questa cosa



                                    boolean insertData = true;

                                    List<com.emergencyescape.greendao.Node> nodeList = nodeDao.loadAll();
                                    for (com.emergencyescape.greendao.Node node1 : nodeList){
                                        if (node1.getCode().equals(nodeOrm.getCode())){
                                            insertData = false;
                                            break;
                                        }
                                    }

                                    if(insertData){
                                        nodeArrayList.add(nodeOrm);
                                    }


                                }
                        nodeDao.insertInTx(nodeArrayList);
                        ArrayList<com.emergencyescape.greendao.Edge> edgeArrayList = new ArrayList<>();
                                for (Edge edge : edges){
                                    loadEdgeTable();
                                    Log.v("onNextIterationEdgeFrom", edge.getFrom());
                                    Log.v("onNextIterationEdgeTo", edge.getTo());

                                    List<com.emergencyescape.greendao.Node> nodeDeparture = nodeDao.queryBuilder().where(NodeDao.Properties.Code.eq(edge.getFrom())).list();
                                    List<com.emergencyescape.greendao.Node> nodeDestination = nodeDao.queryBuilder().where(NodeDao.Properties.Code.eq(edge.getTo())).list();

                                    com.emergencyescape.greendao.Edge edgeOrm = new com.emergencyescape.greendao.Edge();
                                    edgeOrm.setDepartureToOne(nodeDeparture.get(0));
                                    edgeOrm.setDestinationToOne(nodeDestination.get(0));
                                    edgeOrm.setV(edge.getV());
                                    edgeOrm.setI(edge.getI());
                                    edgeOrm.setC(edge.getC());
                                    edgeOrm.setLos(edge.getLos());
                                    edgeOrm.setLength(edge.getLength());
                                    edgeOrm.setSurface(edge.getArea());

                                    edgeOrm.setEm_cost(edge.getEmgcost());
                                    edgeOrm.setNo_em_cost(edge.getLength());


                                    boolean insertData = true;

                                    List<com.emergencyescape.greendao.Edge> edgeList = edgeDao.loadAll();
                                    for (com.emergencyescape.greendao.Edge edge1 : edgeList){
                                        if (edge1.getDepartureId() == edgeOrm.getDepartureId() && edge1.getDestinationId() == edgeOrm.getDestinationId()){
                                            insertData = false;
                                            break;
                                        }
                                    }

                                    if(insertData){
                                        edgeArrayList.add(edgeOrm);
                                    }
                                }
                        edgeDao.insertInTx(edgeArrayList);
                        ArrayList<com.emergencyescape.greendao.Image> imageArrayList = new ArrayList<>();
                                for (Image image : images){
                                    loadImageTable();
                                    Log.v("onNextIterFloorImage", image.getQuota() + " image url: " + image.getUrl());

                                    List<Maps> mapses = mapsDao.queryBuilder()
                                            .where(MapsDao.Properties.Name.eq(image.getMap()))
                                            .list();

                                    // Salvo i nodi presi dal server nel DB
                                    com.emergencyescape.greendao.Image imageOrm = new com.emergencyescape.greendao.Image();

                                    imageOrm.setUrl(image.getUrl());
                                    imageOrm.setQuote(image.getQuota());
                                    imageOrm.setMapId(mapses
                                            .get(0)
                                            .getId());


                                    boolean insertData = true;

                                    List<com.emergencyescape.greendao.Image> imageList = imageDao.loadAll();
                                    for (com.emergencyescape.greendao.Image image1 : imageList){
                                        if (image1.getQuote()==imageOrm.getQuote()){
                                            insertData = false;
                                            break;
                                        }
                                    }

                                    if(insertData){
                                        imageArrayList.add(imageOrm);
                                    }
                                }
                        imageDao.insertInTx(imageArrayList);
                    }
                });

    }

    public void refreshDb(){
        Observable<MapResponse> mapResponseObservableRaw = service.getAPI().getMap(mapName,token);
        Observable<MapResponse> mapResponseObservable = service.getMap(mapResponseObservableRaw);

        subscription = mapResponseObservable
                .subscribe(new Observer<MapResponse>() {

                    @Override
                    public void onCompleted() {
                        Log.v("onCompleted", "END");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG, e.toString());
                    }

                    @Override
                    public void onNext(MapResponse mapResponse) {
                        try {
                            List<Node> nodes = mapResponse.getNodes();
                            List<Edge> edges = mapResponse.getEdges();
                            List<Image> images = mapResponse.getImages();
                            Date mapResponseDate = ISO8601DateParser.parse(mapResponse.getLastUpdateMap());
                            List<Maps> mapsList = mapsDao.loadAll();
                            Maps thisMap = mapsList.get(0);
                            for (Maps map : mapsList) {
                                if (map.getName().equals(mapResponse.getName())) {
                                    thisMap = map;
                                    break;
                                }
                            }
                            if (mapResponseDate.after(thisMap.getLastUpdate())) { // TODO: Verificare cosa succede all'inizio

                                thisMap.setLastUpdate(mapResponseDate);
                                mapResponse.getLastUpdateMap();
                                ArrayList<com.emergencyescape.greendao.Node> nodeArrayList = new ArrayList<>();
                                for (Node node : nodes){
                                    dropNodeTable();
                                    loadNodeTable();
                                    Log.v("onNextIterationNodeId",node.getId().toString());
                                    List<Maps> mapses = mapsDao.queryBuilder()
                                            .where(MapsDao.Properties.Name.eq(node.getMapName()))
                                            .list();

                                    // Salvo i nodi presi dal server nel DB
                                    com.emergencyescape.greendao.Node nodeOrm = new com.emergencyescape.greendao.Node();
                                    nodeOrm.setId(node.getId());
                                    nodeOrm.setCode(node.getCode());
                                    nodeOrm.setDescription(node.getDescr());
                                    nodeOrm.setQuote(node.getQuota());
                                    nodeOrm.setX(node.getX());
                                    nodeOrm.setY(node.getY());
                                    nodeOrm.setWidth(node.getWidth());
                                    nodeOrm.setType(node.getType());
                                    nodeOrm.setMapId(mapses
                                            .get(0)
                                            .getId()); // TODO: Sistemare questa cosa

                                    nodeArrayList.add(nodeOrm);
                                }
                                nodeDao.insertInTx(nodeArrayList);
                                ArrayList<com.emergencyescape.greendao.Edge> edgeArrayList = new ArrayList<>();
                                for (Edge edge : edges){
                                    dropEdgeTable();
                                    loadEdgeTable();
                                    Log.v("onNextIterationEdgeFrom", edge.getFrom());
                                    Log.v("onNextIterationEdgeTo", edge.getTo());



                                    List<com.emergencyescape.greendao.Node> nodeDeparture = nodeDao.queryBuilder().where(NodeDao.Properties.Code.eq(edge.getFrom())).list();
                                    List<com.emergencyescape.greendao.Node> nodeDestination = nodeDao.queryBuilder().where(NodeDao.Properties.Code.eq(edge.getTo())).list();


                                    com.emergencyescape.greendao.Edge edgeOrm = new com.emergencyescape.greendao.Edge();
                                    edgeOrm.setDepartureToOne(nodeDeparture.get(0));
                                    edgeOrm.setDestinationToOne(nodeDestination.get(0));
                                    edgeOrm.setV(edge.getV());
                                    edgeOrm.setI(edge.getI());
                                    edgeOrm.setC(edge.getC());
                                    edgeOrm.setLos(edge.getLos());
                                    edgeOrm.setLength(edge.getLength());
                                    edgeOrm.setSurface(edge.getArea());

                                    edgeOrm.setEm_cost(edge.getEmgcost());
                                    edgeOrm.setNo_em_cost(edge.getLength());

                                    edgeArrayList.add(edgeOrm);
                                }
                                edgeDao.insertInTx(edgeArrayList);
                                List<com.emergencyescape.greendao.Edge> edgeList = edgeDao.loadAll();
                                ArrayList imageArrayList = new ArrayList();
                                for (Image image : images){
                                    dropImageTable();
                                    loadImageTable();
                                    Log.v("onNextIterFloorImage", image.getQuota() + " image url: " + image.getUrl());

                                    List<Maps> mapses = mapsDao.queryBuilder()
                                            .where(MapsDao.Properties.Name.eq(image.getMap()))
                                            .list();

                                    // Salvo i nodi presi dal server nel DB
                                    com.emergencyescape.greendao.Image imageOrm = new com.emergencyescape.greendao.Image();

                                    imageOrm.setUrl(image.getUrl());
                                    imageOrm.setQuote(image.getQuota());
                                    imageOrm.setMapId(mapses
                                            .get(0)
                                            .getId());

                                    imageArrayList.add(imageOrm);
                                }
                                imageDao.insertInTx(imageArrayList);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });









    }



    private void setDb(){
        this.db = dbHelper.getDb();
    }



}
