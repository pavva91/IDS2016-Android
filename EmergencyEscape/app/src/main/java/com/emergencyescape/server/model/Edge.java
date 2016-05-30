
package com.emergencyescape.server.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Edge {

    private Double v;
    private Double i;
    private Double los;
    private Double c;
    private Double length;
    private String from;
    private String to;
    private Double area;
    private Integer numpers;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The v
     */
    public Double getV() {
        return v;
    }

    /**
     * 
     * @param v
     *     The v
     */
    public void setV(Double v) {
        this.v = v;
    }

    /**
     * 
     * @return
     *     The i
     */
    public Double getI() {
        return i;
    }

    /**
     * 
     * @param i
     *     The i
     */
    public void setI(Double i) {
        this.i = i;
    }

    /**
     * 
     * @return
     *     The los
     */
    public Double getLos() {
        return los;
    }

    /**
     * 
     * @param los
     *     The los
     */
    public void setLos(Double los) {
        this.los = los;
    }

    /**
     * 
     * @return
     *     The c
     */
    public Double getC() {
        return c;
    }

    /**
     * 
     * @param c
     *     The c
     */
    public void setC(Double c) {
        this.c = c;
    }

    /**
     * 
     * @return
     *     The length
     */
    public Double getLength() {
        return length;
    }

    /**
     * 
     * @param length
     *     The length
     */
    public void setLength(Double length) {
        this.length = length;
    }

    /**
     * 
     * @return
     *     The from
     */
    public String getFrom() {
        return from;
    }

    /**
     * 
     * @param from
     *     The from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 
     * @return
     *     The to
     */
    public String getTo() {
        return to;
    }

    /**
     * 
     * @param to
     *     The to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * 
     * @return
     *     The area
     */
    public Double getArea() {
        return area;
    }

    /**
     * 
     * @param area
     *     The area
     */
    public void setArea(Double area) {
        this.area = area;
    }

    /**
     * 
     * @return
     *     The numpers
     */
    public Integer getNumpers() {
        return numpers;
    }

    /**
     * 
     * @param numpers
     *     The numpers
     */
    public void setNumpers(Integer numpers) {
        this.numpers = numpers;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
