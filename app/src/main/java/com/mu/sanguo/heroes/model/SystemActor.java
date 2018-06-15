package com.mu.sanguo.heroes.model;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.mu.sanguo.heroes.R;
import com.mu.sanguo.heroes.SgApplication;
import com.mu.sanguo.heroes.util.XmlResourceParserUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * NPC
 * Created by Administrator on 2015/11/9.
 */
public class SystemActor extends SgActor{
    public static final int SHOP_ID_WEAPON = 2001;
    public static final String XML_TAG = "system-actor";

    private List<String> actionList = new ArrayList<>();
    private int id;

    public SystemActor() {
        super("");
    }

    public void parseFromXml(XmlResourceParser xrp) throws IOException, XmlPullParserException {
        final Resources resources = SgApplication.getApplication().getResources();
        name = resources.getString(xrp.getAttributeResourceValue(null, "name", R.string.app_name));
        id = xrp.getAttributeIntValue(null, "id", -1);
        XmlResourceParserUtils.readCurrentTagUntilEnd(xrp, new XmlResourceParserUtils.TagHandler() {
            @Override
            public void handleTag(XmlResourceParser xrp, String tagName) throws XmlPullParserException, IOException {
                if(tagName.equals("action-list")) {
                    readActionList(resources, xrp);
                }
            }
        });
    }

    private void readActionList(final Resources res, XmlResourceParser xrp) throws IOException, XmlPullParserException {
        XmlResourceParserUtils.readCurrentTagUntilEnd(xrp, new XmlResourceParserUtils.TagHandler() {
            @Override
            public void handleTag(XmlResourceParser xrp, String tagName) throws XmlPullParserException, IOException {
                if (tagName.equals("action")) {
                    actionList.add(res.getString(xrp.getAttributeResourceValue(null, "name", R.string.app_name)));
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