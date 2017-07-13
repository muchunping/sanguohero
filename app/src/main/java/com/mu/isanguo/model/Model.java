package com.mu.isanguo.model;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.mu.isanguo.R;

/**
 * !Created by muchunping on 2017/7/12.
 */

public abstract class Model {
    private final int id;
    private final String name;
    private final String description;

    protected Model(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    protected static String readStringFromXml(Resources resources, XmlResourceParser xrp, String key) {
        return resources.getString(xrp.getAttributeResourceValue(null, key, R.string.app_name));
    }

    protected static int readIntFromXml(XmlResourceParser xrp, String key){
        return xrp.getAttributeIntValue(null, key, -1);
    }
}
