package com.emergencyescape.itinerary;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import android.graphics.Path;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.emergencyescape.MyApplication;
import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;
import com.emergencyescape.dijkstra.Db2Dijkstra;
import com.emergencyescape.dijkstra.Dijkstra;
import com.emergencyescape.dijkstra.Graph;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.Edge;
import com.emergencyescape.greendao.EdgeDao;
import com.emergencyescape.greendao.NodeDao;
import com.emergencyescape.greendao.User;
import com.emergencyescape.greendao.UserDao;
import com.emergencyescape.server.ServerService;
import com.emergencyescape.server.model.MapResponse;
import com.emergencyescape.server.model.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * com.emergencyescape.qr
 * QrPresenter
 */
public class ItineraryPresenter extends CommonBehaviourPresenter<ItineraryView> implements ItineraryPresenterInterface {

    DaoSession daoSession = MyApplication.getSession();
    UserDao userDao = daoSession.getUserDao();
    NodeDao nodeDao = daoSession.getNodeDao();
    EdgeDao edgeDao = daoSession.getEdgeDao();

    @Override
    public String getDepartureCode() {
        String userDeparture = "";
        List<User> allUser = userDao.loadAll(); // select *
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase("vale")){
                userDeparture = singleUser.getDepartureToOneUser().getCode();
            }
        }
        return userDeparture;
    }

    @Override
    public com.emergencyescape.greendao.Node getDeparture() {
        com.emergencyescape.greendao.Node userDeparture = new com.emergencyescape.greendao.Node();
        List<User> allUser = userDao.loadAll(); // select *
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase("vale")){
                userDeparture = singleUser.getDepartureToOneUser();
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

    @Override
    public List<String> getEmergencyDestinations() {
        String singleEmergencyExit;
        List<String> allEmergencyExit = new ArrayList<>();
        List<com.emergencyescape.greendao.Node> allNode = nodeDao.loadAll();
        for (com.emergencyescape.greendao.Node singleNode : allNode) {
            if(singleNode.getType().equalsIgnoreCase("emergency_exit")){
                singleEmergencyExit = singleNode.getCode();
                allEmergencyExit.add(singleEmergencyExit);
            }
        }
        return allEmergencyExit;
    }

    @Override
    public Graph.CostPathPair getEmergencyShortestPath(String departure, List<String> allEmergencyExit) {
        List<Graph.CostPathPair> allEmergencyPath = new ArrayList<>();
        Graph.CostPathPair shortestPath = new Graph.CostPathPair(0, new ArrayList<>());
        boolean shortestPathNotAValue = true;
        for (String singleEmergencyExit : allEmergencyExit) { // Calcolo il miglior percorso per ogni uscita di emergenza
            allEmergencyPath.add(this.getShortestPath(
                    departure,
                    singleEmergencyExit,
                    true));
            if(shortestPathNotAValue){
                shortestPath = allEmergencyPath.get(allEmergencyPath.size()-1);
                shortestPathNotAValue = false;
            }
            if(allEmergencyPath.get(allEmergencyPath.size()-1).getCost() <= shortestPath.getCost()){ // Scelgo quello a costo minore
                shortestPath = allEmergencyPath.get(allEmergencyPath.size()-1);
            }
        }
        return shortestPath;
    }

    @Override
    public Graph.CostPathPair getShortestPath(String departure, String destination, Boolean emergencyState) {
        Db2Dijkstra db2Dijkstra = new Db2Dijkstra(emergencyState);
        Graph graph = new Graph(db2Dijkstra.getVertexList(),db2Dijkstra.getEdgeDijkstraList());
        Graph.CostPathPair shortestPath = Dijkstra.getShortestPath(
                graph,
                db2Dijkstra.getVertex(departure),
                db2Dijkstra.getVertex(destination));
        return shortestPath;
    }

    @Override
    public Path getScaledPath(Graph.CostPathPair shortestPath){
        com.emergencyescape.greendao.Node departureNode = getDeparture();
        Integer quoteInteger = departureNode.getQuote();

        if(quoteInteger==145){

        } else if(quoteInteger==150){

        } else if(quoteInteger==155){

        }
    }

    public List<com.emergencyescape.greendao.Node> CostPathPair2ListGreenDaoNode(Graph.CostPathPair path){
        // TODO: Trasformare il path Dijkstra in Path greenDao
        List<Graph.Edge> edgeList = path.getPath();
        List<Edge> allDbValues = edgeDao.loadAll();
        for (Graph.Edge edge : edgeList) {
            Graph.Vertex fromVertex = edge.getFromVertex();
            Graph.Vertex toVertex = edge.getToVertex(); // CODE
        }
    }
    }


}
