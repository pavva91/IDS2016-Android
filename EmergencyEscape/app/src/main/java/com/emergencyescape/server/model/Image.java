
package com.emergencyescape.server.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Image {

    private String url;
    private Integer quota;
    private String map;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
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
     *     The map
     */
    public String getMap() {
        return map;
    }

    /**
     * 
     * @param map
     *     The map
     */
    public void setMap(String map) {
        this.map = map;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
