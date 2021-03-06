package com.emergencyescape.greendao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.emergencyescape.greendao.Edge;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "EDGE".
*/
public class EdgeDao extends AbstractDao<Edge, Long> {

    public static final String TABLENAME = "EDGE";

    /**
     * Properties of entity Edge.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Length = new Property(1, double.class, "length", false, "LENGTH");
        public final static Property V = new Property(2, double.class, "v", false, "V");
        public final static Property I = new Property(3, double.class, "i", false, "I");
        public final static Property C = new Property(4, double.class, "c", false, "C");
        public final static Property Los = new Property(5, double.class, "los", false, "LOS");
        public final static Property Surface = new Property(6, double.class, "surface", false, "SURFACE");
        public final static Property Em_cost = new Property(7, Double.class, "em_cost", false, "EM_COST");
        public final static Property No_em_cost = new Property(8, Double.class, "no_em_cost", false, "NO_EM_COST");
        public final static Property DepartureId = new Property(9, long.class, "departureId", false, "DEPARTURE_ID");
        public final static Property DestinationId = new Property(10, long.class, "destinationId", false, "DESTINATION_ID");
    };

    private DaoSession daoSession;

    private Query<Edge> node_DepartureToManyEdgeQuery;
    private Query<Edge> node_DestinationToManyEdgeQuery;

    public EdgeDao(DaoConfig config) {
        super(config);
    }
    
    public EdgeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"EDGE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"LENGTH\" REAL NOT NULL ," + // 1: length
                "\"V\" REAL NOT NULL ," + // 2: v
                "\"I\" REAL NOT NULL ," + // 3: i
                "\"C\" REAL NOT NULL ," + // 4: c
                "\"LOS\" REAL NOT NULL ," + // 5: los
                "\"SURFACE\" REAL NOT NULL ," + // 6: surface
                "\"EM_COST\" REAL," + // 7: em_cost
                "\"NO_EM_COST\" REAL," + // 8: no_em_cost
                "\"DEPARTURE_ID\" INTEGER NOT NULL ," + // 9: departureId
                "\"DESTINATION_ID\" INTEGER NOT NULL );"); // 10: destinationId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"EDGE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Edge entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getLength());
        stmt.bindDouble(3, entity.getV());
        stmt.bindDouble(4, entity.getI());
        stmt.bindDouble(5, entity.getC());
        stmt.bindDouble(6, entity.getLos());
        stmt.bindDouble(7, entity.getSurface());
 
        Double em_cost = entity.getEm_cost();
        if (em_cost != null) {
            stmt.bindDouble(8, em_cost);
        }
 
        Double no_em_cost = entity.getNo_em_cost();
        if (no_em_cost != null) {
            stmt.bindDouble(9, no_em_cost);
        }
        stmt.bindLong(10, entity.getDepartureId());
        stmt.bindLong(11, entity.getDestinationId());
    }

    @Override
    protected void attachEntity(Edge entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Edge readEntity(Cursor cursor, int offset) {
        Edge entity = new Edge( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getDouble(offset + 1), // length
            cursor.getDouble(offset + 2), // v
            cursor.getDouble(offset + 3), // i
            cursor.getDouble(offset + 4), // c
            cursor.getDouble(offset + 5), // los
            cursor.getDouble(offset + 6), // surface
            cursor.isNull(offset + 7) ? null : cursor.getDouble(offset + 7), // em_cost
            cursor.isNull(offset + 8) ? null : cursor.getDouble(offset + 8), // no_em_cost
            cursor.getLong(offset + 9), // departureId
            cursor.getLong(offset + 10) // destinationId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Edge entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLength(cursor.getDouble(offset + 1));
        entity.setV(cursor.getDouble(offset + 2));
        entity.setI(cursor.getDouble(offset + 3));
        entity.setC(cursor.getDouble(offset + 4));
        entity.setLos(cursor.getDouble(offset + 5));
        entity.setSurface(cursor.getDouble(offset + 6));
        entity.setEm_cost(cursor.isNull(offset + 7) ? null : cursor.getDouble(offset + 7));
        entity.setNo_em_cost(cursor.isNull(offset + 8) ? null : cursor.getDouble(offset + 8));
        entity.setDepartureId(cursor.getLong(offset + 9));
        entity.setDestinationId(cursor.getLong(offset + 10));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Edge entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Edge entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "departureToManyEdge" to-many relationship of Node. */
    public List<Edge> _queryNode_DepartureToManyEdge(long departureId) {
        synchronized (this) {
            if (node_DepartureToManyEdgeQuery == null) {
                QueryBuilder<Edge> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.DepartureId.eq(null));
                node_DepartureToManyEdgeQuery = queryBuilder.build();
            }
        }
        Query<Edge> query = node_DepartureToManyEdgeQuery.forCurrentThread();
        query.setParameter(0, departureId);
        return query.list();
    }

    /** Internal query to resolve the "destinationToManyEdge" to-many relationship of Node. */
    public List<Edge> _queryNode_DestinationToManyEdge(long destinationId) {
        synchronized (this) {
            if (node_DestinationToManyEdgeQuery == null) {
                QueryBuilder<Edge> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.DestinationId.eq(null));
                node_DestinationToManyEdgeQuery = queryBuilder.build();
            }
        }
        Query<Edge> query = node_DestinationToManyEdgeQuery.forCurrentThread();
        query.setParameter(0, destinationId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getNodeDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getNodeDao().getAllColumns());
            builder.append(" FROM EDGE T");
            builder.append(" LEFT JOIN NODE T0 ON T.\"DEPARTURE_ID\"=T0.\"_id\"");
            builder.append(" LEFT JOIN NODE T1 ON T.\"DESTINATION_ID\"=T1.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Edge loadCurrentDeep(Cursor cursor, boolean lock) {
        Edge entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Node departureToOne = loadCurrentOther(daoSession.getNodeDao(), cursor, offset);
         if(departureToOne != null) {
            entity.setDepartureToOne(departureToOne);
        }
        offset += daoSession.getNodeDao().getAllColumns().length;

        Node destinationToOne = loadCurrentOther(daoSession.getNodeDao(), cursor, offset);
         if(destinationToOne != null) {
            entity.setDestinationToOne(destinationToOne);
        }

        return entity;    
    }

    public Edge loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Edge> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Edge> list = new ArrayList<Edge>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Edge> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Edge> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
