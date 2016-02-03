package com.jessicardo.theuserentry;

public class JError {

    private String mErrorMessage;

    private int mErrorCode;

    public JError(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    public JError(String errorMessage, int errorCode) {
        mErrorMessage = errorMessage;
        mErrorCode = errorCode;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        mErrorCode = errorCode;
    }
}
