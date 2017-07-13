package com.mu.isanguo.model.player;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.mu.isanguo.R;
import com.mu.isanguo.SgApplication;
import com.mu.isanguo.utils.XmlResourceParserUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * !Created by muchunping on 2017/6/29.
 */

public class SystemPlayer extends Player {
    public static final int SHOP_ID_WEAPON = 2001;
    public static final String XML_TAG = "system-actor";

    private List<String> actionList = new ArrayList<>();
    private int id;

    public SystemPlayer(int id, String name, boolean male) {
        super(id, name, male);
    }

    public static SystemPlayer createFromXml(final Resources resources, XmlResourceParser xrp) throws IOException, XmlPullParserException {
        String name = resources.getString(xrp.getAttributeResourceValue(null, "name", R.string.app_name));
        int id = xrp.getAttributeIntValue(null, "id", -1);
        final SystemPlayer player = new SystemPlayer(id, name, true);
        XmlResourceParserUtils.readCurrentTagUntilEnd(xrp, new XmlResourceParserUtils.TagHandler() {
            @Override
            public void handleTag(XmlResourceParser xrp, String tagName) throws XmlPullParserException, IOException {
                if(tagName.equals("action-list")) {
                    readActionList(resources, xrp, player);
                }
            }
        });
        return player;
    }

    private static void readActionList(final Resources res, XmlResourceParser xrp, final SystemPlayer player) throws IOException, XmlPullParserException {
        XmlResourceParserUtils.readCurrentTagUntilEnd(xrp, new XmlResourceParserUtils.TagHandler() {
            @Override
            public void handleTag(XmlResourceParser xrp, String tagName) throws XmlPullParserException, IOException {
                if (tagName.equals("action")) {
                    player.actionList.add(res.getString(xrp.getAttributeResourceValue(null, "name", R.string.app_name)));
                }
            }
        });
    }

    public String getName() {
        return name;
    }

    public List<String> getActions() {
        return actionList;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SystemActor{" +
                "name='" + name + '\'' +
                ", actionList=" + actionList +
                '}';
    }
}
