package com.mu.isanguo.model.props;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.mu.isanguo.model.BuildFromXml;
import com.mu.isanguo.model.Model;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * 物品
 * !Created by muchunping on 2017/7/12.
 */

public abstract class Props extends Model implements BuildFromXml {
    public static final String XML_TAG = "item";
    protected static final int TYPE_DRUG = 1;
    protected static final int TYPE_MATERIAL = 2;

    protected final int type;

    public Props(int id, String name, String description, int type) {
        super(id, name, description);
        this.type = type;
    }

    public static Props parseFromXml(Resources resources, XmlResourceParser xrp) throws IOException, XmlPullParserException {
        int id = readIntFromXml(xrp, KEY_ID);
        String name = readStringFromXml(resources, xrp, KEY_NAME);
        String description = readStringFromXml(resources, xrp, KEY_DESCRIPTION);
        int type = readIntFromXml(xrp, KEY_TYPE);

        Props props;
        switch (type){
            default:
            case TYPE_DRUG:
                props = new Drug(id, name, description);
                break ;
            case TYPE_MATERIAL:
                props = new Material(id, name, description);
                break;
        }
        props.buildFromXml(resources, xrp);
        return props;
    }
}
