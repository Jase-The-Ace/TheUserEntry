package com.jessicardo.theuserentry.model;

import com.jessicardo.theuserentry.api.models.Person;
import com.jessicardo.theuserentry.dbgen.DaoSession;
import com.jessicardo.theuserentry.dbgen.PersonDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;


@Singleton
public class PersonModel {

    private final PersonDao mPersonDao;

    private final DaoSession mDaoSession;

    private final Query<Person> mSortedLastNameQuery;

    private final Query<Person> mFindByIdQuery;

    private final DeleteQuery<Person> mDeleteByIdQuery;

    @Inject
    public PersonModel(DaoSession daoSession) {
        mDaoSession = daoSession;
        mPersonDao = mDaoSession.getPersonDao();

        mSortedLastNameQuery = mPersonDao.queryBuilder()
                .orderAsc(PersonDao.Properties.Last_name)
                .build();

        mFindByIdQuery = mPersonDao.queryBuilder()
                .where(PersonDao.Properties.Id.eq("x"))
                .build();

        mDeleteByIdQuery = mPersonDao.queryBuilder()
                .where(PersonDao.Properties.Id.eq("x"))
                .buildDelete();
    }

    public List<Person> loadAll() {
        return mPersonDao.loadAll();
    }

    public List<Person> loadAllSortedByLastName() {
        return mSortedLastNameQuery.forCurrentThread().list();
    }

    public Person loadById(String id) {
        Query<Person> threadQuery = mFindByIdQuery.forCurrentThread();
        threadQuery.setParameter(0, id);
        return threadQuery.unique();
    }

    public long savePerson(Person contact) {
        long id = 0;
        if (contact != null) {
            id = mPersonDao.insertOrReplace(contact);
        }
        return id;
    }

    public void savePersons(final List<Person> persons) {
        if (persons != null) {
            mPersonDao.insertOrReplaceInTx(persons);
        }
    }

    public void deleteById(String id) {
        mDeleteByIdQuery.setParameter(0, id);
        mDeleteByIdQuery.executeDeleteWithoutDetachingEntities();
    }

    public void delete(Person contact) {
        mPersonDao.delete(contact);
    }


    public void deleteAll() {
        mPersonDao.deleteAll();
    }

}
