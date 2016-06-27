package com.emergencyescape.text;

import java.util.ArrayList;

/**
 * Created by Valerio Mattioli on 03/06/2016.
 */
public interface TextDestinationPresenterInterface {
    ArrayList<String> getNodesList();
    Long getDepartureIdFromName(String departureName);
    void setUserDestination(String destination);
    Long getDestinationIdFromName(String destinationName);
    String getUserDeparture();
}
