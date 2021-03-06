package com.jessicardo.theuserentry.dbgen;


import com.jessicardo.theuserentry.api.models.Person;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table PERSON.
*/
public class PersonDao extends AbstractDao<Person, Long> {

    public static final String TABLENAME = "PERSON";

    /**
     * Properties of entity Person.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property First_name = new Property(1, String.class, "first_name", false, "FIRST_NAME");
        public final static Property Last_name = new Property(2, String.class, "last_name", false, "LAST_NAME");
        public final static Property Dob = new Property(3, String.class, "dob", false, "DOB");
        public final static Property Zip_code = new Property(4, String.class, "zip_code", false, "ZIP_CODE");
    };


    public PersonDao(DaoConfig config) {
        super(config);
    }
    
    public PersonDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PERSON' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'FIRST_NAME' TEXT," + // 1: first_name
                "'LAST_NAME' TEXT," + // 2: last_name
                "'DOB' TEXT," + // 3: dob
                "'ZIP_CODE' TEXT);"); // 4: zip_code
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PERSON'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Person entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String first_name = entity.getFirstName();
        if (first_name != null) {
            stmt.bindString(2, first_name);
        }
 
        String last_name = entity.getLastName();
        if (last_name != null) {
            stmt.bindString(3, last_name);
        }
 
        String dob = entity.getDob();
        if (dob != null) {
            stmt.bindString(4, dob);
        }
 
        String zip_code = entity.getZipCode();
        if (zip_code != null) {
            stmt.bindString(5, zip_code);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Person readEntity(Cursor cursor, int offset) {
        Person entity = new Person( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // first_name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // last_name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // dob
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // zip_code
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Person entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFirstName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLastName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDob(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setZipCode(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Person entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Person entity) {
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
    
}
