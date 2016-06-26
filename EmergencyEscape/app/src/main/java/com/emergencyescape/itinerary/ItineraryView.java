package com.emergencyescape.itinerary;

import com.emergencyescape.commonbehaviour.CommonBehaviourView;
import com.emergencyescape.dijkstra.Graph;

/**
 * Created by Valerio Mattioli on 24/05/2016.
 */
public interface ItineraryView extends CommonBehaviourView {
    Graph.CostPathPair getShortestPath();
}
