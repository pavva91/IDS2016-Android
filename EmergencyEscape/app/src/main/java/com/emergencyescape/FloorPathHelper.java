package com.emergencyescape;
/**
 * Created by Valerio Mattioli on 14/06/2016.
 */

import android.graphics.Path;

import com.emergencyescape.greendao.Edge;

import java.util.ArrayList;
import java.util.List;

/**
 * com.emergencyescape
 * FloorPathHelper Mapping .png - coordinate DB
 */
public class FloorPathHelper { // Sembra funzionare

    public List<Coordinate2D> scale145Path(List<Coordinate2D> floorPathCoordinates){ //TODO: Deve prendere come parametro direttamente l'array di coordinate
        List<Coordinate2D> pathScaledCoordinates = new ArrayList<>();
        Coordinate2D singleNode = new Coordinate2D();

        Float xOffset = 200f;
        Float yOffset = 200f;

        for(Coordinate2D coordinates:floorPathCoordinates){
            // TODO: Fare qua Scaling e offset
            coordinates.setY(coordinates.getY() - yOffset);
            pathScaledCoordinates.add(coordinates);

        }

        return pathScaledCoordinates; // TODO: Verificare funzionalità
    }

    public List<Coordinate2D> getCoordinates(List<Edge> edgeList){
        List<Coordinate2D> pathCoordinates = new ArrayList<>();

        boolean firstNode = true;

        for(Edge edge:edgeList){


            if(firstNode) {
                Coordinate2D singleNode = new Coordinate2D();
                singleNode.setX((float) edge.getDepartureToOne().getX());
                singleNode.setY((float) edge.getDepartureToOne().getY());
                pathCoordinates.add(singleNode);
                firstNode = false;
            }
            Coordinate2D singleNode = new Coordinate2D();
            singleNode.setX((float)edge.getDestinationToOne().getX());
            singleNode.setY((float)edge.getDestinationToOne().getY());
            pathCoordinates.add(singleNode);
        }

        return pathCoordinates;
    }
}
