package com.mu.sanguo.heroes;

import android.app.Application;

import com.mu.sanguo.heroes.context.ControllerContext;
import com.mu.sanguo.heroes.context.WorldContext;

/**
 * !Created by muchunping on 2018/4/8.
 */
public class SgApplication extends Application {

    private final SgPreferences preferences = new SgPreferences();
    private final WorldContext world = new WorldContext();
    private final ControllerContext controllers = new ControllerContext(this, world);
    private final WorldSetup setup = new WorldSetup(world, controllers, this);

    private static SgApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        setup.startResourceLoader(getResources());
    }

    public static SgApplication getApplication() {
        return application;
    }

    public WorldContext getWorld() {
        return world;
    }

    public WorldSetup getWorldSetup() {
        return setup;
    }

    public SgPreferences getPreferences() {
        return preferences;
    }

    public ControllerContext getControllerContext() {
        return controllers;
    }

}
