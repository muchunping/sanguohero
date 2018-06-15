package com.mu.sanguo.heroes.model;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * !Created by muchunping on 2018/4/12.
 */
public class SgItem implements Item, XmlCreator {
    private int id;

    public final static String XML_TAG = "item-list";

    @Override
    public void parseFromXml(XmlResourceParser xrp) throws XmlPullParserException, IOException {

    }

    public static Item createByXml(XmlResourceParser xrp) throws IOException, XmlPullParserException {
        SgItem item = new SgItem();
        item.parseFromXml(xrp);
        return item;
    }

    @Override
    public int getId() {
        return id;
    }
}
