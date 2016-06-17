package com.emergencyescape;
/**
 * Created by Valerio Mattioli on 15/06/2016.
 */

/**
 * com.emergencyescape
 * Coordinate2D - rappresentazione coordinate path
 * (Risolve problema mapping Dijkstra path - greenDAO path)
 */
public class Coordinate2D {
    private Float x;
    private Float y;
    private Integer quote;

    public void setX(Float xx){
        x = xx;
    }

    public Float getX(){
        return x;
    }

    public void setY(Float yy){
        y = yy;
    }

    public Float getY(){
        return y;
    }

    public void setQuote(Integer coordQuote){
        quote = coordQuote;
    }

    public Integer getQuote(){
        return quote;
    }
}
