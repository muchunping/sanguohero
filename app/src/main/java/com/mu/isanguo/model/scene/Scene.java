package com.mu.isanguo.model.scene;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Point;

import com.mu.isanguo.R;
import com.mu.isanguo.model.BuildFromXml;
import com.mu.isanguo.model.player.SystemPlayer;
import com.mu.isanguo.utils.XmlResourceParserUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * !Created by muchunping on 2017/6/29.
 */

public abstract class Scene implements BuildFromXml{
    public static final String XML_TAG = "scene";
    public static final int TYPE_CITY = 0;          //城市
    public static final int TYPE_WILD = 1;          //荒野
    public static final int TYPE_CHAMBER = 2;       //密室

    protected final int id;
    protected final String name;
    protected final String description;
    protected final int type;
    protected final Point location;
    protected final List<Integer> neighborList = new ArrayList<>();
    protected final List<Integer> systemActorList = new ArrayList<>();

    protected Scene(int id, String name, String description, int type, Point location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.location = location;
    }

    protected void readNeighborList(XmlResourceParser xrp) throws IOException, XmlPullParserException {
        XmlResourceParserUtils.readCurrentTagUntilEnd(xrp, new XmlResourceParserUtils.TagHandler() {
            @Override
            public void handleTag(XmlResourceParser xrp, String tagName) throws XmlPullParserException, IOException {
                if (tagName.equals(Scene.XML_TAG)) {
                    neighborList.add(xrp.getAttributeIntValue(null, "id", 0));
                }
            }
        });
    }

    protected void readSystemActorList(XmlResourceParser xrp) throws IOException, XmlPullParserException {
        XmlResourceParserUtils.readCurrentTagUntilEnd(xrp, new XmlResourceParserUtils.TagHandler() {
            @Override
            public void handleTag(XmlResourceParser xrp, String tagName) throws XmlPullParserException, IOException {
                if (tagName.equals(SystemPlayer.XML_TAG)) {
                    systemActorList.add(xrp.getAttributeIntValue(null, "id", 0));
                }
            }
        });
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

    public Point getLocation() {
        return location;
    }

    public List<Scene> getNeighborList(List<Scene> sceneList) {
        List<Scene> result = new ArrayList<>(neighborList.size());
        for (int i : neighborList) {
            for (Scene scene : sceneList) {
                if (scene.getId() == i)
                    result.add(scene);
            }
        }
        return result;
    }

    public List<Integer> getSystemActorList() {
        return systemActorList;
    }

    public static Scene parseFromXml(Resources resources, XmlResourceParser xrp) throws IOException, XmlPullParserException {
        int id = xrp.getAttributeIntValue(null, "id", -1);
        String name = resources.getString(xrp.getAttributeResourceValue(null, "name", R.string.app_name));
        int type = xrp.getAttributeIntValue(null, "type", 0);
        String description = resources.getString(xrp.getAttributeResourceValue(null, "description", R.string.app_name));
        String coordinate = xrp.getAttributeValue(null, "coordinate");
        String[] splitString = coordinate.split(",");
        if (splitString.length < 2) {
            throw new XmlPullParserException("The format of the coordinates is incorrect");
        }
        Point location = new Point(Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1]));
        Scene scene;
        switch (type) {
            default:
            case TYPE_CITY:
                scene = new City(id, name, description, location);
                scene.buildFromXml(resources, xrp);
            case TYPE_WILD:
                scene = new Wild(id, name, description, location);
                scene.buildFromXml(resources, xrp);
            case TYPE_CHAMBER:
                scene = new Chamber(id, name, description, location);
                scene.buildFromXml(resources, xrp);
        }
        return scene;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", neighborList=" + neighborList +
                ", systemActorList=" + systemActorList +
                '}';
    }
}
