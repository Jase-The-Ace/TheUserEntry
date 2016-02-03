package com.jessicardo.theuserentry.events;


import com.jessicardo.theuserentry.JError;

public class CreatedPersonEvent extends BaseEvent {

    public CreatedPersonEvent(boolean isSuccess) {
        super(isSuccess);
    }

    public CreatedPersonEvent(JError error) {
        super(error);
    }
}
