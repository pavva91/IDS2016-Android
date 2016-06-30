package com.emergencyescape.dijkstra;
/**
 * Created by Valerio Mattioli on 06/06/2016.
 */

import com.emergencyescape.utils.DBHelper;
import com.emergencyescape.MyApplication;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.Edge;
import com.emergencyescape.greendao.EdgeDao;
import com.emergencyescape.greendao.Node;
import com.emergencyescape.greendao.NodeDao;

import java.util.ArrayList;
import java.util.List;

/**
 * com.emergencyescape.dijkstra
 * Db2Dijkstra
 * Classe che popola le classi Graph, Vertex, Edge del package Dijkstra con i valori del DB
 */
public class Db2Dijkstra {
    private String LOG = this.toString();
    private DaoSession daoSession = MyApplication.getSession();
    private NodeDao nodeDao = daoSession.getNodeDao();
    private EdgeDao edgeDao = daoSession.getEdgeDao();
    private DBHelper dbHelper = MyApplication.getInstance().getDbHelper(); // Serve per avere greenDao
    private List<Graph.Vertex> vertexList = new ArrayList<>();
    private List<Graph.Edge> edgeDijkstraList = new ArrayList<>();

    /**
     * Costruisco il Grafo
     * @param emergencyState
     */
    public Db2Dijkstra(boolean emergencyState){
        this.setDijkstraVertex();
        this.setDijkstraEdge(emergencyState);
    }

    /**
     * Prendo i Vertici dal DB
     */
    public void setDijkstraVertex() {
        List<Node> nodeList = nodeDao.loadAll();
        for (Node node : nodeList) {
            Graph.Vertex vertex = new Graph.Vertex(node.getCode());
            vertexList.add(vertex);
        }
    }

    public List<Graph.Vertex> getVertexList(){
        return this.vertexList;
    }

    /**
     * Prendo gli Archi dal DB
     * @param emergencyState
     */
    public void setDijkstraEdge(boolean emergencyState) {
        List<Edge> edgeList = edgeDao.loadAll();
        Double cost;
        for (Edge edge : edgeList) {
            if(emergencyState){
                cost = edge.getEm_cost();
            } else {
                cost = edge.getNo_em_cost();
            }
            cost = cost * 100; // Perchè Graph.Edge lavora su valori interi di costo, evito approssimazione
            Graph.Edge edgeDijkstra = new Graph.Edge(cost.intValue(),getVertex(edge.getDepartureToOne().getCode()),getVertex(edge.getDestinationToOne().getCode()));
            edgeDijkstraList.add(edgeDijkstra);

        }

    }

    public List<Graph.Edge> getEdgeDijkstraList(){
        return this.edgeDijkstraList;
    }

    public Graph.Vertex getVertex(String code){
        Graph.Vertex vertexToReturn = null;
        for (Graph.Vertex vertex:vertexList){
            if( vertex.getValue().toString().equalsIgnoreCase(code)){
                vertexToReturn=vertex;
            }
        }
        return vertexToReturn;
    }


}
