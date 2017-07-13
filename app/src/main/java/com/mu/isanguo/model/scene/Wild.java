package com.mu.isanguo.model.scene;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Point;

import com.mu.isanguo.utils.XmlResourceParserUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * 荒野
 * !Created by muchunping on 2017/7/11.
 */
public class Wild extends Scene {
    protected Wild(int id, String name, String description, Point location) {
        super(id, name, description, TYPE_WILD, location);
    }

    public void buildFromXml(Resources resources, XmlResourceParser xrp) throws IOException, XmlPullParserException {
        XmlResourceParserUtils.readCurrentTagUntilEnd(xrp, new XmlResourceParserUtils.TagHandler() {
            @Override
            public void handleTag(XmlResourceParser xrp, String tagName) throws XmlPullParserException, IOException {
                if (tagName.equals("npc-list")) {
                    readSystemActorList(xrp);
                }
                if (tagName.equals("neighbor-list")) {
                    readNeighborList(xrp);
                }
            }
        });
    }

    @Override
    public String toString() {
        return "City{" +
                "location=" + location +
                "} " + super.toString();
    }
}
