package cn.igame.sanguoheros.model;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Point;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.app.SgApplication;
import cn.igame.sanguoheros.util.XmlResourceParserUtils;

/**
 * 城市
 * Created by Administrator on 2015/11/9.
 */
public class City extends Scene {

    private Point location;

    protected City() {
    }

    public City(String name, int x, int y) {
        type = TYPE_CITY;
        super.name = name;
        location = new Point(x, y);
    }

    public void createFromXml(XmlResourceParser xrp) throws IOException, XmlPullParserException {
        final Resources resources = SgApplication.getAppContext().getResources();
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

    public Point getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "City{" +
                "location=" + location +
                "} " + super.toString();
    }
}
