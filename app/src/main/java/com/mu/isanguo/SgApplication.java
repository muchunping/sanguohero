package com.mu.isanguo;

import android.app.Application;

import com.mu.isanguo.core.SgWorld;

/**
 * !Created by muchunping on 2017/7/10.
 */

public class SgApplication extends Application {
    private SgWorld world;

    public void initWorld() {
        if(world == null) {
            world = new SgWorld();
            world.init(this);
        }
    }

    public SgWorld getWorld(){
        return world;
    }
}
