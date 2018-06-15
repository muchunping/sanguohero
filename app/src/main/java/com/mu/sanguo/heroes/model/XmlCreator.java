package com.mu.sanguo.heroes.model;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * !Created by muchunping on 2018/4/12.
 */
public interface XmlCreator {

    void parseFromXml(XmlResourceParser xrp) throws XmlPullParserException, IOException;
}
