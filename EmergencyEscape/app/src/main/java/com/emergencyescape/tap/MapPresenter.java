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

    /**
     * Ritorna il nodo cliccato
     * @param xDp Rispetto misure immagine originale
     * @param yDp Rispetto misure immagine originale
     * @param floor
     * @return
     */
    public Node getNodeTapped(Float xDp, Float yDp, String floor){
        Node nodeTapped = new Node();
        List<Node> queryNodeList = new ArrayList<>();
        List<Node> nodeList = new ArrayList<>();
        List<Node> floorNodeList = new ArrayList<>();
        List<Coordinate2D> coordinate2DList= new ArrayList<>(); // Coordinate di tutti i nodi del grafo

        Double minDistance = Double.POSITIVE_INFINITY;
        FloorPathHelper floorPathHelper = new FloorPathHelper();
        Coordinate2D scaledTap = floorPathHelper.getMetersCoordinates(xDp,yDp,floor); // trasformo coordinate tap da pixel immagine originale in metri

        nodeList = nodeDao.loadAll();
        for (Node node : nodeList){ // Prendo solo i nodi del piano
            Integer nodeQuote = node.getQuote();
            Integer floorQuote = Integer.parseInt(floor);
            if (nodeQuote.equals(floorQuote)){
                floorNodeList.add(node); // ok
            }
        }
        for (Node node : floorNodeList){ // Trasformo array Node Dao in array di Coordinate2D
            Coordinate2D coordinate2D = new Coordinate2D();
            coordinate2D.setX((float)node.getX());
            coordinate2D.setY((float)node.getY());
            coordinate2D.setQuote(node.getQuote());
            coordinate2DList.add(coordinate2D);
        }
        int i = 0;
        for (Coordinate2D nodeCoordinates : coordinate2DList){ // Prendo il nodo a distanza minore dal tap
            Double distance = nodeCoordinates.getDistance(scaledTap.getX(),scaledTap.getY());
            if (distance < minDistance){
                nodeTapped = floorNodeList.get(i);
                minDistance = distance;
            }
            i=i+1;
        }
        return nodeTapped;
    }


}
