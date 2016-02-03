package com.jessicardo.theuserentry.db;

import com.jessicardo.theuserentry.App;
import com.jessicardo.theuserentry.dbgen.DaoMaster;
import com.jessicardo.theuserentry.dbgen.DaoSession;

import android.database.sqlite.SQLiteDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DbHelper {

    private static final String DB_NAME = App.class.getSimpleName();

    private DaoSession mDaoSession;

    private DaoMaster mDaoMaster;

    private SQLiteDatabase mDatabase;

    @Inject
    public DbHelper() {
        this(DB_NAME);
    }

    public DbHelper(String dbName) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(
                App.getInstance(), dbName, null);
        mDatabase = devOpenHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDatabase);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    /**
     * Be careful where this is called, this dumps the whole tables / data and recreates the DB
     */
    public void recreateDBTables() {
        mDaoMaster.dropAllTables(mDatabase, true);
        mDaoMaster.createAllTables(mDatabase, true);
    }
}
