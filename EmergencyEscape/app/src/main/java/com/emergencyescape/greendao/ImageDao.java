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

import com.emergencyescape.greendao.Image;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "IMAGE".
*/
public class ImageDao extends AbstractDao<Image, Long> {

    public static final String TABLENAME = "IMAGE";

    /**
     * Properties of entity Image.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Quote = new Property(1, int.class, "quote", false, "QUOTE");
        public final static Property Url = new Property(2, String.class, "url", false, "URL");
        public final static Property MapId = new Property(3, long.class, "mapId", false, "MAP_ID");
    };

    private DaoSession daoSession;

    private Query<Image> maps_ImageListQuery;

    public ImageDao(DaoConfig config) {
        super(config);
    }
    
    public ImageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"IMAGE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"QUOTE\" INTEGER NOT NULL UNIQUE ," + // 1: quote
                "\"URL\" TEXT NOT NULL ," + // 2: url
                "\"MAP_ID\" INTEGER NOT NULL );"); // 3: mapId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"IMAGE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Image entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getQuote());
        stmt.bindString(3, entity.getUrl());
        stmt.bindLong(4, entity.getMapId());
    }

    @Override
    protected void attachEntity(Image entity) {
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
    public Image readEntity(Cursor cursor, int offset) {
        Image entity = new Image( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // quote
            cursor.getString(offset + 2), // url
            cursor.getLong(offset + 3) // mapId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Image entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setQuote(cursor.getInt(offset + 1));
        entity.setUrl(cursor.getString(offset + 2));
        entity.setMapId(cursor.getLong(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Image entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Image entity) {
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
    
    /** Internal query to resolve the "imageList" to-many relationship of Maps. */
    public List<Image> _queryMaps_ImageList(long mapId) {
        synchronized (this) {
            if (maps_ImageListQuery == null) {
                QueryBuilder<Image> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.MapId.eq(null));
                maps_ImageListQuery = queryBuilder.build();
            }
        }
        Query<Image> query = maps_ImageListQuery.forCurrentThread();
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
            builder.append(" FROM IMAGE T");
            builder.append(" LEFT JOIN MAPS T0 ON T.\"MAP_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Image loadCurrentDeep(Cursor cursor, boolean lock) {
        Image entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Maps maps = loadCurrentOther(daoSession.getMapsDao(), cursor, offset);
         if(maps != null) {
            entity.setMaps(maps);
        }

        return entity;    
    }

    public Image loadDeep(Long key) {
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
    public List<Image> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Image> list = new ArrayList<Image>(count);
        
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
    
    protected List<Image> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Image> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
