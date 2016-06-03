package com.emergencyescape.itinerary;

/**
 * Created by Valerio Mattioli on 24/05/2016.
 */
public interface ItineraryPresenterInterface {
    void loadMaps();
    void rxUnSubscribe();
    String getDeparture();
    String getDestination();
}
