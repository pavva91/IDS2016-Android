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

import com.emergencyescape.greendao.Node;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "NODE".
*/
public class NodeDao extends AbstractDao<Node, Long> {

    public static final String TABLENAME = "NODE";

    /**
     * Properties of entity Node.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Code = new Property(1, String.class, "code", false, "CODE");
        public final static Property Description = new Property(2, String.class, "description", false, "DESCRIPTION");
        public final static Property Quote = new Property(3, int.class, "quote", false, "QUOTE");
        public final static Property X = new Property(4, int.class, "x", false, "X");
        public final static Property Y = new Property(5, int.class, "y", false, "Y");
        public final static Property Width = new Property(6, double.class, "width", false, "WIDTH");
        public final static Property Type = new Property(7, String.class, "type", false, "TYPE");
        public final static Property N_people = new Property(8, Integer.class, "n_people", false, "N_PEOPLE");
        public final static Property MapId = new Property(9, long.class, "mapId", false, "MAP_ID");
    };

    private DaoSession daoSession;

    private Query<Node> maps_NodeListQuery;

    public NodeDao(DaoConfig config) {
        super(config);
    }
    
    public NodeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"NODE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"CODE\" TEXT NOT NULL ," + // 1: code
                "\"DESCRIPTION\" TEXT," + // 2: description
                "\"QUOTE\" INTEGER NOT NULL ," + // 3: quote
                "\"X\" INTEGER NOT NULL ," + // 4: x
                "\"Y\" INTEGER NOT NULL ," + // 5: y
                "\"WIDTH\" REAL NOT NULL ," + // 6: width
                "\"TYPE\" TEXT NOT NULL ," + // 7: type
                "\"N_PEOPLE\" INTEGER," + // 8: n_people
                "\"MAP_ID\" INTEGER NOT NULL );"); // 9: mapId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"NODE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Node entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getCode());
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(3, description);
        }
        stmt.bindLong(4, entity.getQuote());
        stmt.bindLong(5, entity.getX());
        stmt.bindLong(6, entity.getY());
        stmt.bindDouble(7, entity.getWidth());
        stmt.bindString(8, entity.getType());
 
        Integer n_people = entity.getN_people();
        if (n_people != null) {
            stmt.bindLong(9, n_people);
        }
        stmt.bindLong(10, entity.getMapId());
    }

    @Override
    protected void attachEntity(Node entity) {
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
    public Node readEntity(Cursor cursor, int offset) {
        Node entity = new Node( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // code
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // description
            cursor.getInt(offset + 3), // quote
            cursor.getInt(offset + 4), // x
            cursor.getInt(offset + 5), // y
            cursor.getDouble(offset + 6), // width
            cursor.getString(offset + 7), // type
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // n_people
            cursor.getLong(offset + 9) // mapId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Node entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCode(cursor.getString(offset + 1));
        entity.setDescription(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setQuote(cursor.getInt(offset + 3));
        entity.setX(cursor.getInt(offset + 4));
        entity.setY(cursor.getInt(offset + 5));
        entity.setWidth(cursor.getDouble(offset + 6));
        entity.setType(cursor.getString(offset + 7));
        entity.setN_people(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setMapId(cursor.getLong(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Node entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Node entity) {
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
    
    /** Internal query to resolve the "nodeList" to-many relationship of Maps. */
    public List<Node> _queryMaps_NodeList(long mapId) {
        synchronized (this) {
            if (maps_NodeListQuery == null) {
                QueryBuilder<Node> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.MapId.eq(null));
                maps_NodeListQuery = queryBuilder.build();
            }
        }
        Query<Node> query = maps_NodeListQuery.forCurrentThread();
        query.setParameter(0, mapId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getMapsDao().getAllColumns());
            builder.append(" FROM NODE T");
            builder.append(" LEFT JOIN MAPS T0 ON T.\"MAP_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Node loadCurrentDeep(Cursor cursor, boolean lock) {
        Node entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Maps maps = loadCurrentOther(daoSession.getMapsDao(), cursor, offset);
         if(maps != null) {
            entity.setMaps(maps);
        }

        return entity;    
    }

    public Node loadDeep(Long key) {
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
    public List<Node> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Node> list = new ArrayList<Node>(count);
        
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
    
    protected List<Node> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Node> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}