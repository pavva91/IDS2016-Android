package com.emergencyescape.server.model;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Generated("org.jsonschema2pojo")
public class MapsResponse {

    private List<Maps> maps = new ArrayList<Maps>();
    private java.util.Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The map
     */
    public List<Maps> getMaps() {
        return maps;
    }

    /**
     *
     * @param map
     * The map
     */
    public void setMap(List<Maps> maps) {
        this.maps = maps;
    }

    public java.util.Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}