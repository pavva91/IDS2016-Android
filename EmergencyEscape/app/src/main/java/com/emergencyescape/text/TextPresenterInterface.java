package com.emergencyescape.text;

import com.emergencyescape.greendao.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valerio Mattioli on 03/06/2016.
 */
public interface TextPresenterInterface {
    ArrayList<String> getNodesList();
    void setUserDeparture(String departure);
    Long getDepartureIdFromName(String departureName);
    void setUserDestination(String destination);
    Long getDestinationIdFromName(String destinationName);
    String getUserDeparture();

}
