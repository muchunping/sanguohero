package cn.igame.sanguoheros.model;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.igame.sanguoheros.util.XmlResourceParserUtils;

/**
 * 场景类
 * Created by Administrator on 2015/11/9.
 */
public abstract class Scene {
    public static final String XML_TAG = "scene";
    public static final int TYPE_CITY = 0;          //城市
    public static final int TYPE_WILD = 1;          //荒野
    public static final int TYPE_ISOLATION = 2;     //副本

    protected int id;
    protected String name;
    protected String description;
    protected int type;
    protected List<Integer> neighborList = new ArrayList<>();
    protected List<Integer> systemActorList = new ArrayList<>();

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
                if (tagName.equals(SystemActor.XML_TAG)) {
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

    public static Scene parseFromXml(XmlResourceParser xrp) throws IOException, XmlPullParserException {
        int type = xrp.getAttributeIntValue(null, "type", 0);
        switch (type) {
            default:
            case TYPE_CITY:
                City city = new City();
                city.createFromXml(xrp);
                return city;
            case TYPE_WILD:
                return null;
            case TYPE_ISOLATION:
                return null;
        }
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
