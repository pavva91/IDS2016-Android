package com.emergencyescape.itinerary;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import android.content.Context;
import android.graphics.Path;

import com.emergencyescape.Coordinate2D;
import com.emergencyescape.DeviceDimensionsHelper;
import com.emergencyescape.FloorPathHelper;
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

import java.util.ArrayList;
import java.util.List;

/**
 * com.emergencyescape.qr
 * QrPresenter
 */
public class ItineraryPresenter extends CommonBehaviourPresenter<ItineraryView> implements ItineraryPresenterInterface {

    Context context = MyApplication.getInstance().getApplicationContext();
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
        FloorPathHelper pathHelper = new FloorPathHelper();
        // Old: List<Edge> edgeDaoPath = getShortestPathCoordinates(shortestPath); // Shortest Path di Edge (problema grafo che si ripercuote su Coordinates
        List<Coordinate2D> pathCoordinates = getShortestPathCoordinates(shortestPath); // OK
        List<Coordinate2D> floorPathCoordinates = new ArrayList<>(); // path del piano di departure
        Path pathToPrint = new Path(); // Path da mandare in stampa
        List<Coordinate2D> pathPrintCoordinates;


        com.emergencyescape.greendao.Node departureNode = getDeparture();
        Integer quoteInteger = departureNode.getQuote();

        for(Coordinate2D singleNodeCoordinates : pathCoordinates){ // TODO: Verificare funzionalità
            Integer quoteNode = singleNodeCoordinates.getQuote();
            if(quoteNode.equals(quoteInteger)){
                floorPathCoordinates.add(singleNodeCoordinates);
            }
        }

         // coordinate path da stampare

        if(quoteInteger==145){
            pathPrintCoordinates = pathHelper.scale145Path(floorPathCoordinates); // ok
            boolean firstNode = true;
            for(Coordinate2D node:pathPrintCoordinates) { // Costruisco il Path passandogli le coordinate
                if(firstNode) {
                    pathToPrint.moveTo(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context), DeviceDimensionsHelper.convertDpToPixel(node.getY(), context)); // INIZIO PATH
                    firstNode = false;
                }else {
                    pathToPrint.lineTo(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context), DeviceDimensionsHelper.convertDpToPixel(node.getY(), context)); // COLLEGO PUNTI PATH
                }
            }
            return pathToPrint; // sembra funzionare

        } else if(quoteInteger==150){
            Path customPath = new Path();
            customPath.moveTo(160,150); // INIZIO PATH
            customPath.lineTo(200,300);
            customPath.lineTo(500,300);
            customPath.lineTo(100,400);
            customPath.lineTo(250,300);
            customPath.lineTo(100,120);
            customPath.lineTo(200,300);
            customPath.lineTo(500,300);
            customPath.lineTo(100,400);
            customPath.lineTo(200,300);
            customPath.lineTo(500,300);
            customPath.lineTo(100,400);
            return customPath;

        } else if(quoteInteger==155){
            Path customPath = new Path();
            customPath.moveTo(160,150); // INIZIO PATH
            customPath.lineTo(200,300);
            customPath.lineTo(500,300);
            customPath.lineTo(100,400);
            customPath.lineTo(250,300);
            customPath.lineTo(100,120);
            customPath.lineTo(100,400);
            customPath.lineTo(250,300);
            customPath.lineTo(100,120);
            return customPath;

        }
        return new Path();
    }

    public List<Coordinate2D> getShortestPathCoordinates(Graph.CostPathPair path){ // Ora funziona ma devo prendere le coordinate da qua dentro
        // TODO: Trasformare il path Dijkstra in Path greenDao - da fare

        String depDijkstraValue;
        String destinationDijkstraValue;

        String depDaoValue;
        String destinationDaoValue;

        List<Graph.Edge> edgeDijkstraPath = path.getPath(); // lista di lati del path
        List<Edge> allEdgeValues = edgeDao.loadAll(); // tutti i lati nel DB
        List<Edge> edgeDaoPath = new ArrayList<>(); // andrà a contenere il sottinsieme dei lati nel DB del path

        List<Coordinate2D> pathCoordinates = new ArrayList<>();
        boolean firstNode = true;


        for (Graph.Edge edge : edgeDijkstraPath) {
            Graph.Vertex departureVertex = edge.getFromVertex();
            Graph.Vertex destinationVertex = edge.getToVertex(); // CODE (String)

            depDijkstraValue = departureVertex.getValue().toString();
            destinationDijkstraValue = destinationVertex.getValue().toString();

            for(Edge edgeDao : allEdgeValues) {

                com.emergencyescape.greendao.Node departureNode = edgeDao.getDepartureToOne();
                com.emergencyescape.greendao.Node destinationNode = edgeDao.getDestinationToOne();

                depDaoValue = departureNode.getCode();
                destinationDaoValue = destinationNode.getCode();

                if ((depDaoValue == depDijkstraValue && destinationDaoValue == destinationDijkstraValue) || (depDaoValue == destinationDijkstraValue && destinationDaoValue == depDijkstraValue ) ){ //  && (destinationDaoValue == destinationDijkstraValue)

                        edgeDaoPath.add(edgeDao); /* TODO: Non dimenticare quanto sei stato coglione:
                                                    Non funziona bene
                                                    Salta valori, forse ciclo troppo grosso e va in overflow
                                                    trovare modo per non andare in overflow, per il resto sembra funzionare
                                                    non sembra andare in overflow, se metto solo una condizione funziona
                                                    guardare perché colori arancione/verde variabili
                                                    TROVATO BUG: IL GRAFO DAO è ORIENTATO - IL GRAFO DIJKSTRA è NON ORIENTATO
                                                    SONO UN DEFICIENTE*/

                    // TODO: Ora quel problema dei due grafi devo sistemarlo anche per andare a prendere le coordinate, lo devo fare qui dentro


                    if(firstNode) {
                        Coordinate2D singleFirstNode = new Coordinate2D();
                        if (depDaoValue == depDijkstraValue) {
                            singleFirstNode.setX((float) edgeDao.getDepartureToOne().getX());
                            singleFirstNode.setY((float) edgeDao.getDepartureToOne().getY());
                            singleFirstNode.setQuote(edgeDao.getDepartureToOne().getQuote());
                        } else{
                            singleFirstNode.setX((float) edgeDao.getDestinationToOne().getX());
                            singleFirstNode.setY((float) edgeDao.getDestinationToOne().getY());
                            singleFirstNode.setQuote(edgeDao.getDestinationToOne().getQuote());
                        }
                        pathCoordinates.add(singleFirstNode);
                        firstNode = false;
                    }
                    Coordinate2D singleNode = new Coordinate2D();
                    if (depDaoValue == depDijkstraValue) {
                        singleNode.setX((float) edgeDao.getDestinationToOne().getX());
                        singleNode.setY((float) edgeDao.getDestinationToOne().getY());
                        singleNode.setQuote(edgeDao.getDestinationToOne().getQuote());
                    } else{
                        singleNode.setX((float) edgeDao.getDepartureToOne().getY());
                        singleNode.setY((float) edgeDao.getDepartureToOne().getX());
                        singleNode.setQuote(edgeDao.getDepartureToOne().getQuote());
                    }
                    pathCoordinates.add(singleNode);
                }
            }
        }

        return pathCoordinates; // TODO: Verificare funzionalità, sembra funzionare 23:45 15/6/2016
    }

    public List<com.emergencyescape.greendao.Node> CostPathPair2ListGreenDaoNode(Graph.CostPathPair path){

        List<com.emergencyescape.greendao.Node> nodeList = new ArrayList<>();
        List<Graph.Edge> edgeList = path.getPath(); // lista di lati del path
        List<Edge> allDbValues = edgeDao.loadAll();
        List<Edge> edgeDaoPath = new ArrayList<>();
        for(Edge edgeDao : allDbValues) {
            for (Graph.Edge edge : edgeList) {
                Graph.Vertex departureVertex = edge.getFromVertex();
                Graph.Vertex destinationVertex = edge.getToVertex(); // CODE (String)
                com.emergencyescape.greendao.Node departureNode = edgeDao.getDepartureToOne();
                if (departureNode.getCode() == departureVertex.getValue().toString()){
                    edgeDaoPath.add(edgeDao);
                }
            }
        }
        return nodeList;
    }
}



