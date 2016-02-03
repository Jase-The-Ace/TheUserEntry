package com.jessicardo.theuserentry.api.controllers;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.jessicardo.theuserentry.api.models.BaseModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FirebaseController {

    private static final String TAG = FirebaseController.class.getSimpleName();

    @Inject
    protected Firebase mFirebase;

    public void saveData(BaseModel model, Firebase.CompletionListener listener) {
        mFirebase.child(model.getTableName()).child(String.valueOf(model.getId())).setValue(model,
                listener);
    }

    public void deleteData(BaseModel model, Firebase.CompletionListener listener) {
        deleteData(model.getTableName(), String.valueOf(model.getId()), listener);
    }

    public void deleteData(String tableName, String id, Firebase.CompletionListener listener) {
        mFirebase.child(tableName).child(id).removeValue(listener);
    }

    public void fetchData(String tableName, ValueEventListener listener) {
        mFirebase.child(tableName).addValueEventListener(listener);
    }

}
