package com.mu.isanguo.model.scene;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Point;

/**
 * 密室
 * !Created by muchunping on 2017/7/11.
 */
public class Chamber extends Scene {
    /**
     * 密室关闭时间，-1表示永久
     */
    private long duration = -1;

    private boolean isDiscover = false;
    private long discoverTime = 0;

    Chamber(int id, String name, String description, Point location) {
        super(id, name, description, TYPE_CHAMBER, location);
    }

    @Override
    public void buildFromXml(Resources resources, XmlResourceParser xrp) {
        int hour = xrp.getAttributeIntValue(null, "duration", -1);
        duration = hour != -1 ? hour * 3600000 : -1;
    }

    public void discover() {
        isDiscover = true;
        discoverTime = System.currentTimeMillis();
    }

    public boolean isDiscover() {
        if(duration != -1){
            isDiscover = discoverTime + duration > System.currentTimeMillis();
        }
        return isDiscover;
    }
}
