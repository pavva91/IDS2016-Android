package com.emergencyescape.text;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */



import com.emergencyescape.DBHelper;
import com.emergencyescape.MyApplication;
import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;
import com.emergencyescape.dijkstra.Db2Dijkstra;
import com.emergencyescape.dijkstra.Dijkstra;
import com.emergencyescape.dijkstra.Graph;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.Node;
import com.emergencyescape.greendao.NodeDao;
import com.emergencyescape.greendao.User;
import com.emergencyescape.greendao.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * com.emergencyescape.text
 * TextDeparturePresenter
 */
public class TextDestinationPresenter extends CommonBehaviourPresenter<TexterView> implements TextDestinationPresenterInterface {
    private DaoSession daoSession = MyApplication.getSession();
    private NodeDao nodeDao = daoSession.getNodeDao();
    private UserDao userDao = daoSession.getUserDao();
    private DBHelper dbHelper = MyApplication.getInstance().getDbHelper();



    @Override
    public ArrayList<String> getNodesList(){
        ArrayList<String> allNames = new ArrayList<String>();
        List<Node> allNodes = nodeDao.loadAll();
        for (Node singleNode : allNodes) {
            String singleName = singleNode.getCode().toString();
            allNames.add(singleName);
        }
        return allNames;
    }


    public void setUserDeparture(String departure) {
        List<User> allUser = userDao.loadAll();
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase("vale")){
                singleUser.setDepartureId(this.getDepartureIdFromName(departure));
                userDao.update(singleUser);
            }
        }
    }

    @Override
    public Long getDepartureIdFromName(String departureName){
        List<Node> allNodes = nodeDao.loadAll();
        Long departureId = -1L;
        for (Node singleNode : allNodes) {
            if(singleNode.getCode().equalsIgnoreCase(departureName)){
                departureId = singleNode.getId();
            }
        }
        return departureId;
    }

    @Override
    public void setUserDestination(String destination) {
        List<User> allUser = userDao.loadAll();
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase("vale")){
                singleUser.setDestinationId(this.getDestinationIdFromName(destination));
                userDao.update(singleUser);
            }
        }
    }

    @Override
    public Long getDestinationIdFromName(String destinationName){
        List<Node> allNodes = nodeDao.loadAll();
        Long destinationId = -1L;
        for (Node singleNode : allNodes) {
            if(singleNode.getCode().equalsIgnoreCase(destinationName)){
                destinationId = singleNode.getId();
            }
        }
        return destinationId;
    }
    @Override
    public String getUserDeparture() {
        String userDeparture = "";
        List<User> allUser = userDao.loadAll();
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase("vale")){
                userDeparture = singleUser.getDepartureToOneUser().getCode();
            }
        }
        return userDeparture;
    }


    public String getUserDestination() {
        String userDestination = "";
        List<User> allUser = userDao.loadAll();
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase("vale")){
                userDestination = singleUser.getDestinationToOneUser().getCode();
            }
        }
        return userDestination;
    }


    public Graph.CostPathPair getShortestPath(String departure, String destination) {
        Db2Dijkstra db2Dijkstra = new Db2Dijkstra();
        Graph graph = new Graph(db2Dijkstra.getVertexList(),db2Dijkstra.getEdgeDijkstraList());
        Graph.CostPathPair shortestPath = Dijkstra.getShortestPath(
                graph,
                db2Dijkstra.getVertex(departure),
                db2Dijkstra.getVertex(destination));
        return shortestPath;
    }
}
