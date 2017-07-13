package com.mu.isanguo.model;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * !Created by muchunping on 2017/7/12.
 */

public interface BuildFromXml {
    String KEY_ID = "id";
    String KEY_NAME = "name";
    String KEY_DESCRIPTION = "description";
    String KEY_TYPE = "type";

    void buildFromXml(Resources resources, XmlResourceParser xrp) throws IOException, XmlPullParserException;
}
