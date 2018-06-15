package com.mu.sanguo.heroes.model;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * !Created by muchunping on 2018/4/7.
 */
public class Monster extends SgActor {

    public Monster(String name) {
        super(name);
    }

    @Override
    public void parseFromXml(XmlResourceParser xrp) throws XmlPullParserException, IOException {

    }
}
