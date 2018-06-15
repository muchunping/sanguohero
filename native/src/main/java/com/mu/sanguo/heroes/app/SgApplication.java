package com.mu.sanguo.heroes.app;

import android.app.Application;
import android.content.Context;

public class SgApplication extends Application {
    private static Context appContext;
    private WorldContext worldContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        worldContext = new WorldContext();
    }

    public static Context getAppContext() {
        return appContext;
    }

    public WorldContext getWorldContext() {
        return worldContext;
    }
}
