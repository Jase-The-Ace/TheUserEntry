package com.jessicardo.theuserentry.events;


import com.jessicardo.theuserentry.JError;

public class FetchedPersonsEvent extends BaseEvent {

    public FetchedPersonsEvent(boolean isSuccess) {
        super(isSuccess);
    }

    public FetchedPersonsEvent(JError error) {
        super(error);
    }
}
