package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the example project DaoExample.
 *
 * Run it as a Java application (not Android).
 *
 * @author Valerio Mattioli
 */

public class EmergencyEscapeGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "com.emergencyescape.greendao"); // Dove crea il model DAO

        addNote(schema);
        addCustomerOrder(schema);

        addUser(schema);

        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }

    private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        order.addToOne(customer, customerId);

        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);
    }
    private static void addUser(Schema schema) {
        Entity note = schema.addEntity("User");
        note.addIdProperty();
        note.addStringProperty("user").notNull();
        note.addStringProperty("psw").notNull();
        note.addStringProperty("partenza");
        note.addStringProperty("destinazione");
    }
}
