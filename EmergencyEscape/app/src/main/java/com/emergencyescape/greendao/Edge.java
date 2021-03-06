package com.emergencyescape.greendao;

import com.emergencyescape.greendao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "EDGE".
 */
public class Edge {

    private Long id;
    private double length;
    private double v;
    private double i;
    private double c;
    private double los;
    private double surface;
    private Double em_cost;
    private Double no_em_cost;
    private long departureId;
    private long destinationId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient EdgeDao myDao;

    private Node departureToOne;
    private Long departureToOne__resolvedKey;

    private Node destinationToOne;
    private Long destinationToOne__resolvedKey;


    public Edge() {
    }

    public Edge(Long id) {
        this.id = id;
    }

    public Edge(Long id, double length, double v, double i, double c, double los, double surface, Double em_cost, Double no_em_cost, long departureId, long destinationId) {
        this.id = id;
        this.length = length;
        this.v = v;
        this.i = i;
        this.c = c;
        this.los = los;
        this.surface = surface;
        this.em_cost = em_cost;
        this.no_em_cost = no_em_cost;
        this.departureId = departureId;
        this.destinationId = destinationId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getEdgeDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getV() {
        return v;
    }

    public void setV(double v) {
        this.v = v;
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getLos() {
        return los;
    }

    public void setLos(double los) {
        this.los = los;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public Double getEm_cost() {
        return em_cost;
    }

    public void setEm_cost(Double em_cost) {
        this.em_cost = em_cost;
    }

    public Double getNo_em_cost() {
        return no_em_cost;
    }

    public void setNo_em_cost(Double no_em_cost) {
        this.no_em_cost = no_em_cost;
    }

    public long getDepartureId() {
        return departureId;
    }

    public void setDepartureId(long departureId) {
        this.departureId = departureId;
    }

    public long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(long destinationId) {
        this.destinationId = destinationId;
    }

    /** To-one relationship, resolved on first access. */
    public Node getDepartureToOne() {
        long __key = this.departureId;
        if (departureToOne__resolvedKey == null || !departureToOne__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NodeDao targetDao = daoSession.getNodeDao();
            Node departureToOneNew = targetDao.load(__key);
            synchronized (this) {
                departureToOne = departureToOneNew;
            	departureToOne__resolvedKey = __key;
            }
        }
        return departureToOne;
    }

    public void setDepartureToOne(Node departureToOne) {
        if (departureToOne == null) {
            throw new DaoException("To-one property 'departureId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.departureToOne = departureToOne;
            departureId = departureToOne.getId();
            departureToOne__resolvedKey = departureId;
        }
    }

    /** To-one relationship, resolved on first access. */
    public Node getDestinationToOne() {
        long __key = this.destinationId;
        if (destinationToOne__resolvedKey == null || !destinationToOne__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NodeDao targetDao = daoSession.getNodeDao();
            Node destinationToOneNew = targetDao.load(__key);
            synchronized (this) {
                destinationToOne = destinationToOneNew;
            	destinationToOne__resolvedKey = __key;
            }
        }
        return destinationToOne;
    }

    public void setDestinationToOne(Node destinationToOne) {
        if (destinationToOne == null) {
            throw new DaoException("To-one property 'destinationId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.destinationToOne = destinationToOne;
            destinationId = destinationToOne.getId();
            destinationToOne__resolvedKey = destinationId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
