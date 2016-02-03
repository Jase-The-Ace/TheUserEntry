package com.jessicardo.theuserentry.events;


import com.jessicardo.theuserentry.JError;

public class DeletedPersonEvent extends BaseEvent {

    public DeletedPersonEvent(boolean isSuccess) {
        super(isSuccess);
    }

    public DeletedPersonEvent(JError error) {
        super(error);
    }
}
