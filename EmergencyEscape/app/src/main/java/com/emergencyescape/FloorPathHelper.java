package com.emergencyescape;
/**
 * Created by Valerio Mattioli on 14/06/2016.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * com.emergencyescape
 * FloorPathHelper Mapping .png - coordinate DB
 */
public class FloorPathHelper {
    // OFFSET (dp)
    private Float xOffset145 = 329f;
    private Float yOffset145 = 3219.845f;

    private Float xOffset150 = 365f;
    private Float yOffset150 = 3212f;

    private Float xOffset155 = 355f;
    private Float yOffset155 = 3230f;

    // RAPPORTI DI SCALA: dp/m
    private Float xRatio145 = 6.4286f;
    private Float yRatio145 = 6.3333f;

    private Float xRatio150 = 6.5f;
    private Float yRatio150 = 6.3333f;

    private Float xRatio155 = 6.4286f;
    private Float yRatio155 = 6.3333f;

    /**
     * Scala quota 145 con .png (818x477)
     * @param floorPathCoordinates coordinate espresse in m
     * @return Coordinate espresse in dp
     */
    public List<Coordinate2D> scale145Path(List<Coordinate2D> floorPathCoordinates){ //TODO: Deve prendere come parametro direttamente l'array di coordinate
        List<Coordinate2D> pathScaledCoordinates = new ArrayList<>();

        for(Coordinate2D coordinates:floorPathCoordinates){
            // TRASFORMA m --> dp
            coordinates.setX(coordinates.getX() * xRatio145);

            coordinates.setY(coordinates.getY() * yRatio145);

            coordinates.setX(coordinates.getX() - xOffset145);

            coordinates.setY(coordinates.getY() - yOffset145);
            coordinates.setY(-coordinates.getY()); // L'asse delle y è invertita dai m a dp

            //coordinates = convertMeterToDp(coordinates,xOffset145,yOffset145,xRatio145,yRatio145);

            pathScaledCoordinates.add(coordinates);
        }
        return pathScaledCoordinates;
    }

    /**
     * Scala quota 150 con .png (818x477)
     * @param floorPathCoordinates coordinate espresse in m
     * @return Coordinate espresse in dp
     */
    public List<Coordinate2D> scale150Path(List<Coordinate2D> floorPathCoordinates){ //TODO: Deve prendere come parametro direttamente l'array di coordinate
        List<Coordinate2D> pathScaledCoordinates = new ArrayList<>();

        for(Coordinate2D coordinates:floorPathCoordinates){
            // TRASFORMA m --> dp
            // TODO: Fare qua Scaling e offset

            coordinates.setX(coordinates.getX() * xRatio150);

            coordinates.setY(coordinates.getY() * yRatio150);

            coordinates.setX(coordinates.getX() - xOffset150);

            coordinates.setY(coordinates.getY() - yOffset150);
            coordinates.setY(-coordinates.getY()); // L'asse delle y è invertita dai m a dp

            //coordinates = convertMeterToDp(coordinates,xOffset150,yOffset150,xRatio150,yRatio150);

            pathScaledCoordinates.add(coordinates);
        }
        return pathScaledCoordinates;
    }

    /**
     * Scala quota 155 con .png (818x477)
     * @param floorPathCoordinates coordinate espresse in m
     * @return Coordinate espresse in dp
     */
    public List<Coordinate2D> scale155Path(List<Coordinate2D> floorPathCoordinates){ //TODO: Deve prendere come parametro direttamente l'array di coordinate
        List<Coordinate2D> pathScaledCoordinates = new ArrayList<>();

        for(Coordinate2D coordinates:floorPathCoordinates){
            // TRASFORMA m --> dp
            // TODO: Fare qua Scaling e offset

            coordinates.setX(coordinates.getX() * xRatio155);

            coordinates.setY(coordinates.getY() * yRatio155);

            coordinates.setX(coordinates.getX() - xOffset155);

            coordinates.setY(coordinates.getY() - yOffset155);
            coordinates.setY(-coordinates.getY()); // L'asse delle y è invertita dai m a dp

            //coordinates = convertMeterToDp(coordinates,xOffset155,yOffset155,xRatio155,yRatio155); //TODO: Una volta finito riconoscimento tap fare refactoring disegno path con questa

            pathScaledCoordinates.add(coordinates);
        }
        return pathScaledCoordinates;
    }

    /**
     *
     * @param xx - espresso rispetto 818 px
     * @param yy - espresso rispetto 477 px
     * @param floor
     * @return
     */
    public Coordinate2D getMetersCoordinates(Float xx, Float yy, String floor){
        Coordinate2D meterCoordinates = new Coordinate2D();
        meterCoordinates.setX(xx);
        meterCoordinates.setY(yy);
        Float xOffset;
        Float yOffset;
        Float xRatio;
        Float yRatio;
        if(floor=="145"){
            xOffset=xOffset145;
            yOffset=yOffset145;
            xRatio=xRatio145;
            yRatio=yRatio145;
            meterCoordinates.setQuote(Integer.getInteger("145"));
            meterCoordinates = convertDpToMeter(meterCoordinates,xOffset,yOffset,xRatio,yRatio);
        }else if(floor=="150"){
            xOffset=xOffset150;
            yOffset=yOffset150;
            xRatio=xRatio150;
            yRatio=yRatio150;
            meterCoordinates.setQuote(Integer.getInteger("150"));
            meterCoordinates = convertDpToMeter(meterCoordinates,xOffset,yOffset,xRatio,yRatio);
        }else if(floor=="155"){
            xOffset=xOffset155;
            yOffset=yOffset155;
            xRatio=xRatio155;
            yRatio=yRatio155;
            meterCoordinates.setQuote(Integer.getInteger("155"));
            meterCoordinates = convertDpToMeter(meterCoordinates,xOffset,yOffset,xRatio,yRatio);
        }
        return meterCoordinates;
    }

    /**
     * Converte una qualsiasi coppia di coordinate da metri a dp
     * @param coordinate2D
     * @param xOffset
     * @param yOffset
     * @param xRatio
     * @param yRatio
     * @return
     */
    private Coordinate2D convertMeterToDp(Coordinate2D coordinate2D,Float xOffset,Float yOffset, Float xRatio, Float yRatio){
        coordinate2D.setX(coordinate2D.getX() * xRatio);

        coordinate2D.setY(coordinate2D.getY() * yRatio);

        coordinate2D.setX(coordinate2D.getX() - xOffset);

        coordinate2D.setY(coordinate2D.getY() - yOffset);
        coordinate2D.setY(-coordinate2D.getY()); // L'asse delle y è invertita dai m a dp
        return coordinate2D;
    }

    /**
     * Converte una qualsiasi coppia di coordinate da dp a metri
     * @param coordinate2D
     * @param xOffset
     * @param yOffset
     * @param xRatio
     * @param yRatio
     * @return
     */
    private Coordinate2D convertDpToMeter(Coordinate2D coordinate2D,Float xOffset,Float yOffset, Float xRatio, Float yRatio){
        coordinate2D.setX(coordinate2D.getX() + xOffset);

        coordinate2D.setY(coordinate2D.getY() + yOffset);
        coordinate2D.setY(-coordinate2D.getY()); // L'asse delle y è invertita dai m a dp

        coordinate2D.setX(coordinate2D.getX() / xRatio);

        coordinate2D.setY(coordinate2D.getY() / yRatio);
        return coordinate2D;
    }
}
