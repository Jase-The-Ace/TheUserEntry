package com.jessicardo.theuserentry.events;


import com.jessicardo.theuserentry.JError;
import com.jessicardo.theuserentry.api.models.BaseModel;

public class BaseEvent {

    private boolean mIsSuccess;

    private BaseModel mResult;

    private JError mError;

    // Source ID of the object
    private String mSourceId;

    public BaseEvent(boolean isSuccess) {
        mIsSuccess = isSuccess;
    }

    public BaseEvent(JError error) {
        mIsSuccess = false;
        mError = error;
    }

    public BaseEvent(String sourceId, boolean isSuccess) {
        mSourceId = sourceId;
        mIsSuccess = isSuccess;
    }

    public BaseEvent(String sourceId, BaseModel result) {
        mSourceId = sourceId;
        mResult = result;
        mIsSuccess = true;
    }

    public BaseEvent(String sourceId, JError error) {
        mIsSuccess = false;
        mSourceId = sourceId;
        mError = error;
    }

    public BaseEvent() {
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }

    // Helper to quickly get error message from error object.
    public String getErrorMsg() {
        String errorMessage = null;
        if (mError != null) {
            errorMessage = mError.getErrorMessage();
        }
        return errorMessage;
    }

    public BaseModel getResult() {
        return mResult;
    }

    public JError getError() {
        return mError;
    }

    public String getSourceId() {
        return mSourceId;
    }
}
