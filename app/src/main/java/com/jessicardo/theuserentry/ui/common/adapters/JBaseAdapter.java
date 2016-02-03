
package com.jessicardo.theuserentry.ui.common.adapters;


import com.jessicardo.theuserentry.util.HelperUtil;

import android.support.v7.widget.RecyclerView;

public abstract class JBaseAdapter extends RecyclerView.Adapter {

    public void logI(String logMessage) {
        HelperUtil.logI(logMessage, this.getClass());
    }

    public void logE(String logMessage) {
        HelperUtil.logE(logMessage, this.getClass());
    }

    public void logD(String logMessage) {
        HelperUtil.logD(logMessage, this.getClass());
    }

    public void logV(String logMessage) {
        HelperUtil.logV(logMessage, this.getClass());
    }

    public void logW(String logMessage) {
        HelperUtil.logW(logMessage, this.getClass());
    }

    public void logWtf(String logMessage) {
        HelperUtil.logWtf(logMessage, this.getClass());
    }

}
