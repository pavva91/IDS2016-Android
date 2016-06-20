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

    /**
     * Ritorna distanza Euclidea rispetto al punto passato come parametro
     * @param xx ascissa del punto da cui misurare distanza
     * @param yy odinata del punto da cui misurare distanza
     * @return Distanza Euclidea
     */
    public Double getDistance(Float xx, Float yy){
        Float xDifference = Math.abs(x - xx);
        Float yDifference = Math.abs(y - yy);
        Double euclideanDistance = Math.sqrt((xDifference*xDifference)+(yDifference*yDifference));

        return euclideanDistance;
    }
}
