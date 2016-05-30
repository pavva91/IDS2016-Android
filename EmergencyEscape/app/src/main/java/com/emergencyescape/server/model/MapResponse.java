
package com.emergencyescape.server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MapResponse {

    private String name;
    private List<Node> nodes = new ArrayList<Node>();
    private List<Edge> edges = new ArrayList<Edge>();
    private List<String> images = new ArrayList<String>();
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
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * 
     * @param nodes
     *     The nodes
     */
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * 
     * @return
     *     The edges
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * 
     * @param edges
     *     The edges
     */
    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    /**
     * 
     * @return
     *     The images
     */
    public List<String> getImages() {
        return images;
    }

    /**
     * 
     * @param images
     *     The images
     */
    public void setImages(List<String> images) {
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
