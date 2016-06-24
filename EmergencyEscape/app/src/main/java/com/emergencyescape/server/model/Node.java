
package com.emergencyescape.server.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Node {

    private String mapName;
    private Long id;
    private String code;
    private String descr;
    private Integer quota;
    private Integer x;
    private Integer y;
    private Double width;
    private String type;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The mapName
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * 
     * @param mapName
     *     The mapName
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     * 
     * @param descr
     *     The descr
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * 
     * @return
     *     The quota
     */
    public Integer getQuota() {
        return quota;
    }

    /**
     * 
     * @param quota
     *     The quota
     */
    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    /**
     * 
     * @return
     *     The x
     */
    public Integer getX() {
        return x;
    }

    /**
     * 
     * @param x
     *     The x
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * 
     * @return
     *     The y
     */
    public Integer getY() {
        return y;
    }

    /**
     * 
     * @param y
     *     The y
     */
    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * 
     * @return
     *     The width
     */
    public Double getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(Double width) {
        this.width = width;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
