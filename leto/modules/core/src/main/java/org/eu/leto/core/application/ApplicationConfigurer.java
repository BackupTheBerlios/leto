package org.eu.leto.core.application;

public interface ApplicationConfigurer {
    void onDispose(Application application);


    void onInit(Application application);


    void onStart(Application application);


    void onStop(Application application);
}
