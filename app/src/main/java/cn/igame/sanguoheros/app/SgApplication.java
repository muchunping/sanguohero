package cn.igame.sanguoheros.app;

import android.app.Application;
import android.content.Context;

public class SgApplication extends Application {
    private static Context appContext;
    private static WorldContext worldContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        worldContext = new WorldContext();
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static WorldContext getWorldContext() {
        return worldContext;
    }
}
