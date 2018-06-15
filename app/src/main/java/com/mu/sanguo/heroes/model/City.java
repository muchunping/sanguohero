package com.mu.sanguo.heroes.model;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Point;

import com.mu.sanguo.heroes.R;
import com.mu.sanguo.heroes.SgApplication;
import com.mu.sanguo.heroes.util.XmlResourceParserUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * !Created by muchunping on 2018/4/7.
 */
public class City extends SgScene {

    protected City() {
        type = TYPE_CITY;
    }

    public City(String name, int x, int y) {
        type = TYPE_CITY;
        super.name = name;
        location = new Point(x, y);
    }

    public void parseFromXml(XmlResourceParser xrp) throws IOException, XmlPullParserException {
        final Resources resources = SgApplication.getApplication().getResources();
        name = resources.getString(xrp.getAttributeResourceValue(null, "name", R.string.app_name));
        id = xrp.getAttributeIntValue(null, "id", -1);
        String coordinate = xrp.getAttributeValue(null, "coordinate");
        String[] splitString = coordinate.split(",");
        if (splitString.length > 1)
            location = new Point(Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1]));
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
