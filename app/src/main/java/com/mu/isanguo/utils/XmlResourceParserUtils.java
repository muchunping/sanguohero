package com.mu.isanguo.utils;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * !Created by muchunping on 2017/7/11.
 */

public class XmlResourceParserUtils {
    public interface TagHandler {
        void handleTag(XmlResourceParser xrp, String tagName) throws XmlPullParserException, IOException;
    }

    public static void readCurrentTagUntilEnd(XmlResourceParser xrp, TagHandler handler) throws XmlPullParserException, IOException {
        String outerTagName = xrp.getName();
        String tagName;
        int eventType;
        while ((eventType = xrp.next()) != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                tagName = xrp.getName();
                handler.handleTag(xrp, tagName);
            } else if (eventType == XmlResourceParser.END_TAG) {
                tagName = xrp.getName();
                if (tagName.equals(outerTagName)) return;
            }
        }
    }
}
