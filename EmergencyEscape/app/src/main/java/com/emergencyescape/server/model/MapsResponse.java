
package com.emergencyescape.server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

// TODO: Non la uso, da cancellare

@Generated("org.jsonschema2pojo")
public class MapsResponse {

    private String name;
    private List<Object> nodes = new ArrayList<Object>();
    private List<Object> edges = new ArrayList<Object>();
    private List<Object> images = new ArrayList<Object>();
    private String lastUpdateMap;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     *     The nodes
     */
    public List<Object> getNodes() {
        return nodes;
    }

    /**
     *
     * @param nodes
     *     The nodes
     */
    public void setNodes(List<Object> nodes) {
        this.nodes = nodes;
    }

    /**
     *
     * @return
     *     The edges
     */
    public List<Object> getEdges() {
        return edges;
    }

    /**
     *
     * @param edges
     *     The edges
     */
    public void setEdges(List<Object> edges) {
        this.edges = edges;
    }

    /**
     *
     * @return
     *     The images
     */
    public List<Object> getImages() {
        return images;
    }

    /**
     *
     * @param images
     *     The images
     */
    public void setImages(List<Object> images) {
        this.images = images;
    }

    /**
     *
     * @return
     *     The lastUpdateMap
     */
    public String getLastUpdateMap() {
        return lastUpdateMap;
    }

    /**
     *
     * @param lastUpdateMap
     *     The lastUpdateMap
     */
    public void setLastUpdateMap(String lastUpdateMap) {
        this.lastUpdateMap = lastUpdateMap;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
