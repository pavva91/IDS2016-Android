package com.emergencyescape.itinerary;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import android.content.Context;
import android.graphics.Path;

import com.emergencyescape.utils.Coordinate2D;
import com.emergencyescape.utils.DeviceDimensionsHelper;
import com.emergencyescape.utils.FloorPathHelper;
import com.emergencyescape.MyApplication;
import com.emergencyescape.businesslogic.ServerConnection;
import com.emergencyescape.businesslogic.SessionClass;
import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;
import com.emergencyescape.dijkstra.Db2Dijkstra;
import com.emergencyescape.dijkstra.Dijkstra;
import com.emergencyescape.dijkstra.Graph;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.Edge;
import com.emergencyescape.greendao.EdgeDao;
import com.emergencyescape.greendao.Node;
import com.emergencyescape.greendao.NodeDao;
import com.emergencyescape.greendao.User;
import com.emergencyescape.greendao.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * com.emergencyescape.itinerary
 * ItineraryPresenter
 */
public class ItineraryPresenter extends CommonBehaviourPresenter<ItineraryView> {

    private Context context = MyApplication.getInstance().getApplicationContext();
    private DaoSession daoSession = MyApplication.getSession();
    private UserDao userDao = daoSession.getUserDao();
    private NodeDao nodeDao = daoSession.getNodeDao();
    private EdgeDao edgeDao = daoSession.getEdgeDao();

    // Coordinate dei nodi in cui stampare icona:
    private Coordinate2D startingNode = new Coordinate2D();
    private Coordinate2D destinationNode = new Coordinate2D();

    private String printStairsMessage = "";
    private boolean booleanPrintStairsMessage = false;

    public Graph.CostPathPair shortestPath;
    private Graph.CostPathPair alternativePath;
    public Graph dijkstraGraph;
    public Db2Dijkstra db2Dijkstra;

    private SessionClass sessionClass = SessionClass.getInstance();
    private String userName = sessionClass.getUser(MyApplication.context); // userName from SharedPreferences

    private String token = sessionClass.getServerKey(MyApplication.context);
    private ServerConnection serverConnection = ServerConnection.getInstance(MyApplication.context);

    public String getDepartureCode() {
        String userDeparture = "1370";
        List<User> allUser = userDao.loadAll(); // select *
        for (User singleUser : allUser) {
            if(singleUser.getName().equals(userName)){
                userDeparture = singleUser.getDepartureToOneUser().getCode();
            }
        }
        return userDeparture;
    }

    public com.emergencyescape.greendao.Node getDeparture() {
        com.emergencyescape.greendao.Node userDeparture = new com.emergencyescape.greendao.Node();
        List<User> allUser = userDao.loadAll(); // select *
        for (User singleUser : allUser) {
            if(singleUser.getName().equals(userName)){
                userDeparture = singleUser.getDepartureToOneUser();
            }
        }
        return userDeparture;
    }

    public com.emergencyescape.greendao.Node getDestinationNodeDao() {
        com.emergencyescape.greendao.Node userDestination = new com.emergencyescape.greendao.Node();
        List<User> allUser = userDao.loadAll(); // select *
        for (User singleUser : allUser) {
            if(singleUser.getName().equals(userName)){
                userDestination = singleUser.getDestinationToOneUser();
            }
        }
        return userDestination;
    }

    public String getDestination() {
        String userDestination = "1370"; // TODO: Null pointer exception
        List<User> allUser = userDao.loadAll();
        for (User singleUser : allUser) {
            if(singleUser.getName().equals(userName)){
                userDestination = singleUser.getDestinationToOneUser().getCode();
            }
        }
        return userDestination;
    }

    /**
     * Get All emergency destinations in DB
     * @return
     */
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

    /**
     * Calculate ShortestPath in emergency state
     * @param departure
     * @param allEmergencyExit List<String> con tutte le uscite di emergenza presenti nella mappa
     * @return
     */
    public Graph.CostPathPair getEmergencyShortestPath(String departure, List<String> allEmergencyExit) {
        List<Graph.CostPathPair> allEmergencyPath = new ArrayList<>();
        Graph.CostPathPair shortestPath = new Graph.CostPathPair(0, new ArrayList<>());
        Graph.CostPathPair lengthPath = new Graph.CostPathPair(0, new ArrayList<>()); // length shortestPath
        boolean shortestPathNotAValue = true;
        for (String singleEmergencyExit : allEmergencyExit) { // Calcolo il miglior percorso per ogni uscita di emergenza
            allEmergencyPath.add(this.getShortestPath(
                    departure,
                    singleEmergencyExit,
                    true));
            if(shortestPathNotAValue){
                shortestPath = allEmergencyPath.get(allEmergencyPath.size()-1);
                lengthPath = getShortestPath(departure,singleEmergencyExit,false);
                shortestPathNotAValue = false;
            }
            if (allEmergencyPath.get(allEmergencyPath.size()-1).getCost() == shortestPath.getCost()){ // Se hanno lo stesso valore in emergenza vado a confrontare valori in non emergenza
                Graph.CostPathPair lengthShortestPath = this.getShortestPath(departure,singleEmergencyExit,false); // length path calcolato nel ciclo
                if(lengthShortestPath.getCost()<lengthPath.getCost()){
                    lengthPath = lengthShortestPath;
                    shortestPath = allEmergencyPath.get(allEmergencyPath.size()-1);
                }
            }
            if(allEmergencyPath.get(allEmergencyPath.size()-1).getCost() < shortestPath.getCost()){ // Scelgo quello a costo minore

                shortestPath = allEmergencyPath.get(allEmergencyPath.size()-1);
            }
        }
        this.shortestPath = shortestPath;
        List<Graph.Edge> path = shortestPath.getPath();
        if (path.size()!=0) {
            Graph.Edge lastEdge = path.get(path.size() - 1);
            Graph.Vertex destinationVertex = lastEdge.getToVertex();
            setUserDestination(destinationVertex.getValue().toString());
        }

        return shortestPath;
    }

    /**
     * Calcola shortest path dati in input partenza e destinazione
     * @param departure
     * @param destination
     * @param emergencyState
     * @return
     */
    public Graph.CostPathPair getShortestPath(String departure, String destination, Boolean emergencyState) {
        Db2Dijkstra db2Dijkstra = new Db2Dijkstra(emergencyState);
        Graph graph = new Graph(db2Dijkstra.getVertexList(),db2Dijkstra.getEdgeDijkstraList());
        Graph.CostPathPair shortestPath = Dijkstra.getShortestPath(
                graph,
                db2Dijkstra.getVertex(departure),
                db2Dijkstra.getVertex(destination));
        this.shortestPath = shortestPath;
        this.dijkstraGraph = graph;
        this.db2Dijkstra = db2Dijkstra;
        return shortestPath;
    }

    /**
     * Calcola il peroorso alternativo assegnando un costo molto grande al primo arco del shortestPath
     * @return
     */
    public Graph.CostPathPair calculateAlternativePath(){
        List<Graph.Edge> path = shortestPath.getPath();
        if (path.size()!=0) {
            Graph.Vertex fromVertex = path.get(0).getFromVertex();
            Graph.Vertex toVertex = path.get(0).getToVertex();
            Graph originalGraph = this.dijkstraGraph;
            List<Graph.Edge> edgeList = originalGraph.getEdges();
            List<Graph.Vertex> vertexList = originalGraph.getVertices();
            List<Graph.Edge> modifiedEdgeList = new ArrayList<>();

            int i = 0;
            for (Graph.Edge edge : edgeList) {
                Comparable edgeToValue = edge.getToVertex().getValue();
                Comparable edgeFromValue = edge.getFromVertex().getValue();

                Comparable fromValue = fromVertex.getValue();
                Comparable ToValue = toVertex.getValue();

                if ((edgeFromValue.equals(fromValue) && edgeToValue.equals(ToValue)) ||
                        (edgeFromValue.equals(ToValue) && edgeToValue.equals(fromValue))) {
                    edge.setCost(99999);
                }
                modifiedEdgeList.add(edge);
                i = i + 1;
            }
            Graph modifiedGraph = new Graph(Graph.TYPE.DIRECTED, vertexList, modifiedEdgeList);
            Graph.CostPathPair alternativePath = Dijkstra.getShortestPath(
                    modifiedGraph,
                    db2Dijkstra.getVertex(getDepartureCode()),
                    db2Dijkstra.getVertex(getDestination()));
            this.alternativePath = alternativePath;
            return alternativePath;
        }else{
            return shortestPath;
        }
    }

    /**
     * Trasformo il Path ritornato da Dijkstra nel Path grafico che andrà a stampare in FloorBitmap
     * @param shortestPath risultato alggoritmo Dijkstra
     * @return graphics.Path
     */
    public Path getScaledPath(Graph.CostPathPair shortestPath){
        booleanPrintStairsMessage = false;
        FloorPathHelper pathHelper = new FloorPathHelper();
        // Old: List<Edge> edgeDaoPath = getShortestPathCoordinates(shortestPath); // Shortest Path di Edge (problema grafo che si ripercuote su Coordinates
        List<Coordinate2D> pathCoordinates = getShortestPathCoordinates(shortestPath);
        List<Coordinate2D> floorPathCoordinates = new ArrayList<>(); // path del piano di departure
        Path pathToPrint = new Path(); // Path da mandare in stampa
        List<Coordinate2D> pathPrintCoordinates;
        com.emergencyescape.greendao.Node departureNode = getDeparture();
        Integer quoteInteger = departureNode.getQuote();


        boolean dontComeBackInFloor = false;
        int i = 0;
        int j = 0;
        for(Coordinate2D singleNodeCoordinates : pathCoordinates){ // Prendo solo i nodi del piano al primo giro
            Integer quoteNode = singleNodeCoordinates.getQuote();
            if(quoteNode.equals(quoteInteger) && !dontComeBackInFloor){
                floorPathCoordinates.add(singleNodeCoordinates);
                j=i;
                if (pathCoordinates.size()>i+1) {
                    if (!pathCoordinates.get(i+1).getQuote().equals(quoteInteger)) {
                        dontComeBackInFloor = true;
                    }
                }
            }
            i = i +1;
        }
        if (floorPathCoordinates.size()==1 && pathCoordinates.size()>1){ // Controllo per stampare indicazione scale
            printStairsMessage = pathCoordinates.get(j+1).getQuote().toString();
            booleanPrintStairsMessage = true;
        }

        if (floorPathCoordinates.size()==0){ // Risolve assegnamento partenza quando path nullo
            Coordinate2D departureCoordinate = new Coordinate2D();
            departureCoordinate.setX((float)departureNode.getX());
            departureCoordinate.setY((float)departureNode.getY());
            departureCoordinate.setQuote(quoteInteger);
            floorPathCoordinates.add(departureCoordinate);
        }

        if(quoteInteger==145){
            pathPrintCoordinates = pathHelper.scale145Path(floorPathCoordinates); // ok
            boolean firstNode = true;
            for(Coordinate2D node:pathPrintCoordinates) { // Costruisco il Path passandogli le coordinate
                if(firstNode) {
                    pathToPrint.moveTo(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context), DeviceDimensionsHelper.convertDpToPixel(node.getY(), context)); // INIZIO PATH
                    // Aggiunge l'icona di start percorso
                    startingNode.setX(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context));
                    startingNode.setY(DeviceDimensionsHelper.convertDpToPixel(node.getY(), context));
                    firstNode = false;
                }else {
                    pathToPrint.lineTo(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context), DeviceDimensionsHelper.convertDpToPixel(node.getY(), context)); // COLLEGO PUNTI PATH
                    destinationNode.setX(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context));
                    destinationNode.setY(DeviceDimensionsHelper.convertDpToPixel(node.getY(), context));
                }
            }
            return pathToPrint;

        } else if(quoteInteger==150){
            pathPrintCoordinates = pathHelper.scale150Path(floorPathCoordinates); // ok
            boolean firstNode = true;
            for(Coordinate2D node:pathPrintCoordinates) { // Costruisco il Path passandogli le coordinate
                if(firstNode) {
                    pathToPrint.moveTo(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context), DeviceDimensionsHelper.convertDpToPixel(node.getY(), context)); // INIZIO PATH
                    startingNode.setX(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context));
                    startingNode.setY(DeviceDimensionsHelper.convertDpToPixel(node.getY(), context));
                    firstNode = false;
                }else {
                    pathToPrint.lineTo(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context), DeviceDimensionsHelper.convertDpToPixel(node.getY(), context)); // COLLEGO PUNTI PATH
                    destinationNode.setX(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context));
                    destinationNode.setY(DeviceDimensionsHelper.convertDpToPixel(node.getY(), context));
                }
            }
            return pathToPrint;

        } else if(quoteInteger==155){
            pathPrintCoordinates = pathHelper.scale155Path(floorPathCoordinates); // ok
            boolean firstNode = true;
            for(Coordinate2D node:pathPrintCoordinates) { // Costruisco il Path passandogli le coordinate
                if(firstNode) {
                    pathToPrint.moveTo(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context), DeviceDimensionsHelper.convertDpToPixel(node.getY(), context)); // INIZIO PATH
                    startingNode.setX(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context));
                    startingNode.setY(DeviceDimensionsHelper.convertDpToPixel(node.getY(), context));
                    firstNode = false;
                }else {
                    pathToPrint.lineTo(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context), DeviceDimensionsHelper.convertDpToPixel(node.getY(), context)); // COLLEGO PUNTI PATH
                    destinationNode.setX(DeviceDimensionsHelper.convertDpToPixel(node.getX(), context));
                    destinationNode.setY(DeviceDimensionsHelper.convertDpToPixel(node.getY(), context));
                }
            }
            return pathToPrint;
        }
        return new Path();
    }

    /**
     * Trasforma il path Dijkstra in ArrayList<Coordinate2D>
     * @param path Path risultato dell'algoritmo di Dijkstra
     * @return List<Coordinate2D>coordinate in metri ordinate del path</Coordinate2D>
     */
    public List<Coordinate2D> getShortestPathCoordinates(Graph.CostPathPair path){
        String depDijkstraValue;
        String destinationDijkstraValue;

        String depDaoValue;
        String destinationDaoValue;

        List<Graph.Edge> edgeDijkstraPath = path.getPath(); // lista di lati del path
        List<Edge> allEdgeValues = edgeDao.loadAll(); // tutti i lati nel DB (GRAFO ORIENTATO)
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

                if ((depDaoValue.equals(depDijkstraValue) && destinationDaoValue.equals(destinationDijkstraValue)) || (depDaoValue.equals(destinationDijkstraValue) && destinationDaoValue.equals(depDijkstraValue))){
                    if(firstNode) {
                        Coordinate2D singleFirstNode = new Coordinate2D();
                        if (depDaoValue.equals(depDijkstraValue)) {
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
                    if (depDaoValue.equals(depDijkstraValue)) {
                        singleNode.setX((float) edgeDao.getDestinationToOne().getX());
                        singleNode.setY((float) edgeDao.getDestinationToOne().getY());
                        singleNode.setQuote(edgeDao.getDestinationToOne().getQuote());
                    } else{
                        singleNode.setX((float) edgeDao.getDepartureToOne().getX());
                        singleNode.setY((float) edgeDao.getDepartureToOne().getY());
                        singleNode.setQuote(edgeDao.getDepartureToOne().getQuote());
                    }
                    pathCoordinates.add(singleNode);
                }
            }
        }
        return pathCoordinates;
    }

    public Long getNodeIdFromName(String nodeName){
        List<Node> allNodes = nodeDao.loadAll();
        Long nodeId = -1L;
        for (Node singleNode : allNodes) {
            if(singleNode.getCode().equalsIgnoreCase(nodeName)){
                nodeId = singleNode.getId();
            }
        }
        return nodeId;
    }

    public void setUserDeparture(String departure) { // TODO: Aggiornare anche il Server
        List<User> allUser = userDao.loadAll();
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase(userName)){
                singleUser.setDepartureId(this.getNodeIdFromName(departure));
                userDao.update(singleUser);
                serverConnection.updateUserPosition(this.getNodeIdFromName(departure).toString(),userName,token);
            }
        }
    }

    public void setUserDestination(String destination) { // TODO: Aggiornare anche il Server
        List<User> allUser = userDao.loadAll();
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase(userName)){
                singleUser.setDestinationId(this.getNodeIdFromName(destination));
                userDao.update(singleUser);
                serverConnection.updateUserPosition(this.getNodeIdFromName(destination).toString(),userName,token);
            }
        }
    }

    public Coordinate2D getStartingNode(){
        return startingNode;
    }

    public Coordinate2D getDestinationNode(){
        return destinationNode;
    }

    public String getPrintStairsMessage(){
        return printStairsMessage;
    }

    public boolean getBooleanPrintStairsMessage(){
        return booleanPrintStairsMessage;
    }

    public Graph.CostPathPair getAlternativePath(){
        return this.alternativePath;
    }

    public void setAlternativePath(Graph.CostPathPair alternativePath){
        this.alternativePath = alternativePath;
    }
}



