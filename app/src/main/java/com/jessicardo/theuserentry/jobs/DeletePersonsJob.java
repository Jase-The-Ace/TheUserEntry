package com.jessicardo.theuserentry.jobs;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.jessicardo.theuserentry.api.controllers.FirebaseController;
import com.jessicardo.theuserentry.api.models.Person;
import com.jessicardo.theuserentry.events.DeletedPersonEvent;
import com.path.android.jobqueue.Params;

import javax.inject.Inject;

public class DeletePersonsJob extends BaseJob {

    private static final String TAG = DeletePersonsJob.class.getSimpleName();

    @Inject
    protected FirebaseController mFirebaseController;

    private String mId;

    public DeletePersonsJob(String id) {
        super(new Params(Priority.MID).requireNetwork().persist());
        mId = id;
    }

    @Override
    public void onRun() throws Throwable {
        super.onRun();
        mFirebaseController.deleteData(Person.TABLE_NAME, mId, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    getEventBus().post(new DeletedPersonEvent(true));
                } else {
                    getEventBus().post(new DeletedPersonEvent(getError()));
                }
            }
        });

    }

    @Override
    protected void onCancel() {
        super.onCancel();
        getEventBus().post(new DeletedPersonEvent(getError()));
    }
}