package com.emergencyescape.tap;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.emergencyescape.Coordinate2D;
import com.emergencyescape.FloorPathHelper;
import com.emergencyescape.MyApplication;
import com.emergencyescape.R;
import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.Node;
import com.emergencyescape.greendao.NodeDao;
import com.emergencyescape.greendao.User;
import com.emergencyescape.greendao.UserDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * com.emergencyescape.qr
 * QrPresenter
 */
public class MapPresenter extends CommonBehaviourPresenter<TapView> {

    private DaoSession daoSession = MyApplication.getSession();
    private NodeDao nodeDao = daoSession.getNodeDao();
    private UserDao userDao = daoSession.getUserDao();


    public void setUserDeparture(Node departureNode) { // TODO: Aggiornare anche il Server
        List<User> allUser = userDao.loadAll();
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase("vale")){
                singleUser.setDepartureId(departureNode.getId());
                userDao.update(singleUser);
            }
        }
    }

    public Drawable getFloorImage(String floor){ // TODO: Andare a prendere immagini precedentemente caricate
        Drawable drawable = MyApplication.context.getResources().getDrawable(R.drawable.q145);
        if(floor.equalsIgnoreCase("145")) {
            drawable = MyApplication.context.getResources().getDrawable(R.drawable.q145);

        }else if(floor.equalsIgnoreCase("150")) {
            drawable = MyApplication.context.getResources().getDrawable(R.drawable.q150);

        }else if(floor.equalsIgnoreCase("155")) {
            drawable = MyApplication.context.getResources().getDrawable(R.drawable.q155);

        }
        return drawable;
    }

    public Node getNodeTapped(Float xDp, Float yDp, String floor){
        Node nodeTapped = new Node();
        List<Node> queryNodeList = new ArrayList<>();
        List<Node> nodeList = new ArrayList<>();
        List<Node> floorNodeList = new ArrayList<>();
        List<Coordinate2D> coordinate2DList= new ArrayList<>(); // Coordinate di tutti i nodi del grafo
        Coordinate2D coordinate2D = new Coordinate2D();
        Double minDistance = Double.POSITIVE_INFINITY;
        FloorPathHelper floorPathHelper = new FloorPathHelper();
        Coordinate2D scaledTap = floorPathHelper.getMetersCoordinates(xDp,yDp,floor);

        nodeList = nodeDao.loadAll();
        for (Node node : nodeList){ // Prendo solo i nodi del piano
            Integer nodeQuote = node.getQuote();
            Integer floorQuote = Integer.parseInt(floor);
            if (nodeQuote.equals(floorQuote)){
                floorNodeList.add(node);
            }
        }
        // floorNodeList = nodeDao.queryBuilder().where(NodeDao.Properties.Quote.eq(Integer.getInteger(floor))).list(); // Errore SQL
        Log.v("floorNodeList", Integer.toString(floorNodeList.size()));
        for (Node node : floorNodeList){ // Trasformo array Node Dao in array di Coordinate2D
            coordinate2D.setX((float)node.getX());
            coordinate2D.setY((float)node.getY());
            coordinate2D.setQuote(node.getQuote());
            coordinate2DList.add(coordinate2D);
        }
        Log.v("floorNodeCoordinates", Integer.toString(coordinate2DList.size()));
        int i = 0;
        for (Coordinate2D nodeCoordinates : coordinate2DList){ // Prendo il nodo a distanza minore dal tap
            Double distance = nodeCoordinates.getDistance(scaledTap.getX(),scaledTap.getY());
            if (distance < minDistance){
                nodeTapped = floorNodeList.get(i);
            }
            i++;
        }
        return nodeTapped;
    }


}
