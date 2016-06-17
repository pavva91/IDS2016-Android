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

    /**
     * Scala quota 145 con .png (818x477)
     * @param floorPathCoordinates coordinate espresse in m
     * @return Coordinate espresse in dp
     */
    public List<Coordinate2D> scale145Path(List<Coordinate2D> floorPathCoordinates){ //TODO: Deve prendere come parametro direttamente l'array di coordinate
        List<Coordinate2D> pathScaledCoordinates = new ArrayList<>();

        // OFFSET (dp)
        Float xOffset = 329f;
        Float yOffset = 3219.845f;

        // RAPPORTI DI SCALA: dp/m
        Float xRatio = 6.4286f;
        Float yRatio = 6.3333f;

        for(Coordinate2D coordinates:floorPathCoordinates){
            // TRASFORMA m --> dp
            coordinates.setX(coordinates.getX() * xRatio);

            coordinates.setY(coordinates.getY() * yRatio);

            coordinates.setX(coordinates.getX() - xOffset);

            coordinates.setY(coordinates.getY() - yOffset);
            coordinates.setY(-coordinates.getY()); // L'asse delle y è invertita dai m a dp

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
        // OFFSET (dp)
        Float xOffset = 365f;
        Float yOffset = 3212f;

        // RAPPORTI DI SCALA: dp/m
        Float xRatio = 6.5f;
        Float yRatio = 6.3333f;

        for(Coordinate2D coordinates:floorPathCoordinates){
            // TRASFORMA m --> dp
            // TODO: Fare qua Scaling e offset

            coordinates.setX(coordinates.getX() * xRatio);

            coordinates.setY(coordinates.getY() * yRatio);

            coordinates.setX(coordinates.getX() - xOffset);

            coordinates.setY(coordinates.getY() - yOffset);
            coordinates.setY(-coordinates.getY()); // L'asse delle y è invertita dai m a dp

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

        // OFFSET (dp)
        Float xOffset = 355f;
        Float yOffset = 3230f;

        // RAPPORTI DI SCALA: dp/m
        Float xRatio = 6.4286f;
        Float yRatio = 6.3333f;

        for(Coordinate2D coordinates:floorPathCoordinates){
            // TRASFORMA m --> dp
            // TODO: Fare qua Scaling e offset

            coordinates.setX(coordinates.getX() * xRatio);

            coordinates.setY(coordinates.getY() * yRatio);

            coordinates.setX(coordinates.getX() - xOffset);

            coordinates.setY(coordinates.getY() - yOffset);
            coordinates.setY(-coordinates.getY()); // L'asse delle y è invertita dai m a dp

            pathScaledCoordinates.add(coordinates);
        }
        return pathScaledCoordinates;
    }
}
