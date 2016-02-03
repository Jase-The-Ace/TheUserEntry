package com.jessicardo.theuserentry.api.controllers;

import com.jessicardo.theuserentry.api.models.Person;
import com.jessicardo.theuserentry.jobs.CreatePersonsJob;
import com.jessicardo.theuserentry.jobs.DeletePersonsJob;
import com.jessicardo.theuserentry.jobs.FetchPersonsJob;
import com.path.android.jobqueue.JobManager;

import javax.inject.Inject;

public class PersonController {

    @Inject
    protected JobManager mJobManager;

    public void savePerson(Person person) {
        mJobManager.addJobInBackground(new CreatePersonsJob(person));
    }

    public void deletePerson(Person person) {
        deletePerson(String.valueOf(person.getId()));
    }


    public void deletePerson(String id) {
        mJobManager.addJobInBackground(new DeletePersonsJob(id));
    }

    public void fetchPersons() {
        mJobManager.addJobInBackground(new FetchPersonsJob());
    }
}
