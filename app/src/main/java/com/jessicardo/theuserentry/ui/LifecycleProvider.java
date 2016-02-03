package com.jessicardo.theuserentry.ui;

public interface LifecycleProvider {

    public void registerLifcycleListener(LifecycleListener listener);

    public void unregisterLifcycleListener(LifecycleListener listener);

    public boolean isVisible();

}
