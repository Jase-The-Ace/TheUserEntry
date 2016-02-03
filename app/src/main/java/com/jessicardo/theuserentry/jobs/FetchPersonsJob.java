package com.jessicardo.theuserentry.jobs;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.jessicardo.theuserentry.api.controllers.FirebaseController;
import com.jessicardo.theuserentry.api.models.Person;
import com.jessicardo.theuserentry.events.FetchedPersonsEvent;
import com.jessicardo.theuserentry.model.PersonModel;
import com.path.android.jobqueue.Params;

import javax.inject.Inject;

public class FetchPersonsJob extends BaseJob {

    private static final String TAG = FetchPersonsJob.class.getSimpleName();

    @Inject
    protected FirebaseController mFirebaseController;

    @Inject
    protected PersonModel mPersonModel;

    public FetchPersonsJob() {
        super(new Params(Priority.MID).requireNetwork().persist());
    }

    @Override
    public void onRun() throws Throwable {
        super.onRun();
        mFirebaseController.fetchData(Person.TABLE_NAME, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mPersonModel.deleteAll();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Person person = postSnapshot.getValue(Person.class);
                    mPersonModel.savePerson(person);
                }
                getEventBus().post(new FetchedPersonsEvent(true));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                getEventBus().post(new FetchedPersonsEvent(getError()));
            }
        });
    }

    @Override
    protected void onCancel() {
        super.onCancel();
        getEventBus().post(new FetchedPersonsEvent(getError()));
    }
}