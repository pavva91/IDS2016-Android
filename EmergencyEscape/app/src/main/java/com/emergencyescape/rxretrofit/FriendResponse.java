package com.emergencyescape.rxretrofit;

import java.util.ArrayList;

/**
 * Created by cteegarden on 1/25/16.
 *
 * // ArrayList with all the record of Friend table
 */
public class FriendResponse {

    public FriendLocations friendLocations;

    public FriendResponse(){} // costruttore vuoto

    public class FriendLocations { // Inner class
        public Data data;
        public class Data{ // Inner class
            public ArrayList<Friend> friend = new ArrayList<>();
            public class Friend{ // DB table
                public String friendName;
                public String friendType;
                public String lat;
                public String lon;
            }
        }
    }
}
