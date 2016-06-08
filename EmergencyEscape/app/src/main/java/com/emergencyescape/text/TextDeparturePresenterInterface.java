package com.emergencyescape.text;

import com.emergencyescape.greendao.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valerio Mattioli on 03/06/2016.
 */
public interface TextDeparturePresenterInterface {
    ArrayList<String> getNodesList();
    void setUserDeparture(String departure);
    Long getDepartureIdFromName(String departureName);
}
