package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the emergency escape project.
 *
 * Run it as a Java application (not Android).
 *
 * @author Valerio Mattioli
 */

public class EmergencyEscapeGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "com.emergencyescape.greendao"); // Dove crea il model DAO

        // Creo Tabelle
        Entity user = schema.addEntity("User");
        Entity edge = schema.addEntity("Edge");
        Entity node = schema.addEntity("Node");
        Entity map = schema.addEntity("Maps");
        Entity image = schema.addEntity("Image");

        // Definisco Strutture Dati
        addUser(user);
        addEdge(edge);
        addImage(image);
        addMaps(map);
        addNode(node);

        // Creo relazioni tra tabelle
        relationMapToImage(map,image);
        relationMapToNode(map,node);
        relationNodeToEdge(node,edge);
        relationNodeToUser(node,user);

        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }

    private static void addUser(Entity user) {
        user.addIdProperty().notNull().primaryKey();
        user.addStringProperty("name").notNull();
        user.addStringProperty("password").notNull();
        user.addStringProperty("token").notNull();
    }
    private static void addEdge(Entity edge) {
        edge.addIdProperty().primaryKey();
        edge.addDoubleProperty("length").notNull();
        edge.addDoubleProperty("v").notNull();
        edge.addDoubleProperty("i").notNull();
        edge.addDoubleProperty("c").notNull();
        edge.addDoubleProperty("los").notNull();
        edge.addDoubleProperty("surface").notNull();
        edge.addDoubleProperty("em_cost");
        edge.addDoubleProperty("no_em_cost");
    }
    private static void addNode(Entity node) {
        node.addIdProperty().primaryKey();
        node.addStringProperty("code").notNull();
        node.addStringProperty("description");
        node.addIntProperty("quote").notNull();
        node.addIntProperty("x").notNull();
        node.addIntProperty("y").notNull();
        node.addDoubleProperty("width").notNull();
        node.addStringProperty("type").notNull();
        node.addIntProperty("n_people");
    }
    private static void addMaps(Entity map) {
        map.addIdProperty().notNull().primaryKey();
        map.addStringProperty("name").notNull();
        map.addDateProperty("lastUpdate");
    }
    private static void addImage(Entity image) {
        image.addIdProperty().primaryKey();
        image.addIntProperty("quote").notNull().unique();
        image.addStringProperty("url").notNull();
    }

    // Ora faccio le relazioni

    private static void relationMapToImage(Entity map, Entity image){
        Property mapId = image.addLongProperty("mapId").notNull().getProperty(); // Creo Foreign Key
        image.addToOne(map, mapId); // Creo la bidirezionalità della relazione
        ToMany mapToImage = map.addToMany(image, mapId); // Creo Relazione a molti (N)
    }

    private static void relationMapToNode(Entity map, Entity node){
        Property mapId = node.addLongProperty("mapId").notNull().getProperty(); // Creo Foreign Key
        node.addToOne(map, mapId);
        ToMany mapToImage = map.addToMany(node, mapId); // Creo Relazione uno a molti
    }

    private static void relationNodeToEdge(Entity node, Entity edge){
        Property departureId = edge.addLongProperty("departureId").notNull().getProperty(); // Creo Foreign Key
        Property destinationId = edge.addLongProperty("destinationId").notNull().getProperty(); // Creo Foreign Key
        edge.addToOne(node, departureId).setName("departureToOne");
        edge.addToOne(node, destinationId).setName("destinationToOne");
        ToMany nodeToEdgeDeparture = node.addToMany(edge, departureId);
        nodeToEdgeDeparture.setName("departureToManyEdge");
        ToMany nodeToEdgeDestination = node.addToMany(edge, destinationId);
        nodeToEdgeDestination.setName("destinationToManyEdge");// Creo Relazione uno a molti
    }

    private static void relationNodeToUser(Entity node, Entity user){
        Property departureId = user.addLongProperty("departureId").notNull().getProperty(); // Creo Foreign Key
        Property destinationId = user.addLongProperty("destinationId").notNull().getProperty(); // Creo Foreign Key
        user.addToOne(node, departureId).setName("departureToOneUser");
        user.addToOne(node, destinationId).setName("destinationToOneUser");
        ToMany nodeToUserDeparture = node.addToMany(user, departureId);
        nodeToUserDeparture.setName("departureToManyUser");
        ToMany nodeToUserDestination = node.addToMany(user, destinationId);
        nodeToUserDestination.setName("destinationToManyUser"); // Creo Relazione uno a molti
    }
}
