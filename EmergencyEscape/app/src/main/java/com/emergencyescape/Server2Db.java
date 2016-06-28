package com.emergencyescape;
/**
 * Created by Valerio Mattioli on 01/06/2016.
 */

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

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

        setDb();
        NodeDao.dropTable(db,true);
        NodeDao.createTable(db,false);

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

                        nodeDao.insert(node);
                    }
                });
    }

    public void loadEdges(){

        setDb();
        EdgeDao.dropTable(db,true);
        EdgeDao.createTable(db,false);





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
                        Log.e(LOG+" Edge: ", e.toString());
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

                        edge.setEm_cost(response.getEmgcost());
                        edge.setNo_em_cost(response.getLength());


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
                            com.emergencyescape.greendao.Maps maps = new Maps();
                            maps.setName(singleMapResponse.getName());
                            maps.setLastUpdate(singleMapResponse.getLastUpdateMap());



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

        setDb();
        ImageDao.dropTable(db,true);
        ImageDao.createTable(db,false);

        Observable<MapResponse> mapResponseObservable = service.getAPI().getMap(mapName,token);
        Observable<Image> imageObservable= service.getImages(mapResponseObservable); // Deserializzo la risposta



        subscription = imageObservable
                .subscribe(new Observer<Image>() {
                    @Override
                    public void onCompleted() {
                        Log.v("onCompleted","END");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG, "Image: " + e.toString());

                    }

                    @Override
                    public void onNext(Image response) {
                        Log.v("onNextIterFloorImage",response.getQuota() + " image url: " + response.getUrl());

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
                        imageDao.insert(image);
                        // downloadFromUrl(image.getUrl(), Integer.toString(image.getQuote()) + ".png"); //TODO: Sistemare Download
                    }
                });
    }

    public void initializeDb(){
        setDb();
        UserDao.dropTable(db,true);
        UserDao.createTable(db,false);

        /*
        com.emergencyescape.greendao.User user = new com.emergencyescape.greendao.User();
        user.setName("vale");
        user.setPassword("123");
        user.setToken("12m2t7oc43godndv767tkj9hue");
        userDao.insert(user);
        */

        NodeDao.dropTable(db,true);
        NodeDao.createTable(db,false);

        // Risposta generale server
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

                        nodeDao.insert(node);
                    }
                });


        EdgeDao.dropTable(db,true);
        EdgeDao.createTable(db,false);


        Observable<Edge> edgeObservable= service.getEdges(mapResponseObservable); // Deserializzo la risposta


        subscription = edgeObservable
                .subscribe(new Observer<Edge>() {
                    @Override
                    public void onCompleted() {
                        Log.v("onCompleted","END");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG+" Edge: ", e.toString());
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

                        edge.setEm_cost(response.getEmgcost());
                        edge.setNo_em_cost(response.getLength());

                        edgeDao.insert(edge);
                    }
                });


        ImageDao.dropTable(db,true);
        ImageDao.createTable(db,false);

        Observable<Image> imageObservable= service.getImages(mapResponseObservable); // Deserializzo la risposta



        subscription = imageObservable
                .subscribe(new Observer<Image>() {
                    @Override
                    public void onCompleted() {
                        Log.v("onCompleted","END");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG, "Image: " + e.toString());

                    }

                    @Override
                    public void onNext(Image response) {
                        Log.v("onNextIterFloorImage",response.getQuota() + " image url: " + response.getUrl());

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
                        imageDao.insert(image);
                        // downloadFromUrl(image.getUrl(), Integer.toString(image.getQuote()) + ".png"); //TODO: Sistemare Download
                    }
                });


    }



    private void setDb(){
        this.db = dbHelper.getDb();
    }



}
