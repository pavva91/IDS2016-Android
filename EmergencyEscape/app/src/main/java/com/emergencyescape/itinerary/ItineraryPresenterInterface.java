package com.emergencyescape.itinerary;

import com.emergencyescape.dijkstra.Graph;
import com.emergencyescape.greendao.Node;

import java.util.List;
import java.util.Map;

/**
 * Created by Valerio Mattioli on 24/05/2016.
 */
public interface ItineraryPresenterInterface {
    String getDepartureCode();
    Node getDeparture();
    String getDestination();
    List<String> getEmergencyDestinations();
    Graph.CostPathPair getEmergencyShortestPath(String departure, List<String> allEmergencyExit);
    Graph.CostPathPair getShortestPath(String departure, String destination, Boolean emergencyState);
}
