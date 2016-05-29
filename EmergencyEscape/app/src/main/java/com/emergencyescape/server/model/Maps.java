package com.emergencyescape.server.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Maps {

    private String name;
    private List<Object> edges = new ArrayList<Object>();
    private List<Object> nodes = new ArrayList<Object>();
    private String lastModify;
    private java.util.Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The edges
     */
    public List<Object> getEdges() {
        return edges;
    }

    /**
     *
     * @param edges
     * The edges
     */
    public void setEdges(List<Object> edges) {
        this.edges = edges;
    }

    /**
     *
     * @return
     * The nodes
     */
    public List<Object> getNodes() {
        return nodes;
    }

    /**
     *
     * @param nodes
     * The nodes
     */
    public void setNodes(List<Object> nodes) {
        this.nodes = nodes;
    }

    /**
     *
     * @return
     * The lastModify
     */
    public String getLastModify() {
        return lastModify;
    }

    /**
     *
     * @param lastModify
     * The last_modify
     */
    public void setLastModify(String lastModify) {
        this.lastModify = lastModify;
    }

    public java.util.Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
