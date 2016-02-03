package com.jessicardo.theuserentry.jobs;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.jessicardo.theuserentry.api.controllers.FirebaseController;
import com.jessicardo.theuserentry.api.models.Person;
import com.jessicardo.theuserentry.events.CreatedPersonEvent;
import com.path.android.jobqueue.Params;

import javax.inject.Inject;

public class CreatePersonsJob extends BaseJob {

    private static final String TAG = CreatePersonsJob.class.getSimpleName();

    @Inject
    protected FirebaseController mFirebaseController;

    private Person mPerson;

    public CreatePersonsJob(Person person) {
        super(new Params(Priority.MID).requireNetwork().persist());
        mPerson = person;
    }

    @Override
    public void onRun() throws Throwable {
        super.onRun();
        mFirebaseController.saveData(mPerson, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    getEventBus().post(new CreatedPersonEvent(true));
                } else {
                    getEventBus().post(new CreatedPersonEvent(getError()));
                }
            }
        });

    }

    @Override
    protected void onCancel() {
        super.onCancel();
        getEventBus().post(new CreatedPersonEvent(getError()));
    }
}