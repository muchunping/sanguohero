package com.mu.sanguo.heroes.resource;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.SparseArray;

import com.mu.sanguo.heroes.R;
import com.mu.sanguo.heroes.context.WorldContext;
import com.mu.sanguo.heroes.model.Item;
import com.mu.sanguo.heroes.model.Model;
import com.mu.sanguo.heroes.model.Scene;
import com.mu.sanguo.heroes.model.SgItem;
import com.mu.sanguo.heroes.model.SgScene;
import com.mu.sanguo.heroes.model.SystemActor;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * !Created by muchunping on 2018/4/9.
 */
public class ResourceLoader {

    public static void loadResourcesAsync(WorldContext world, Resources resources) {
        //load scenes
        world.sceneList.clear();
        XmlResourceParser xrp = resources.getXml(R.xml.scenes);
        try {
            int eventType;
            while ((eventType = xrp.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String s = xrp.getName();
                    if (s.equals(SgScene.XML_TAG)) {
                        Scene scene = SgScene.createByXml(xrp);
                        world.sceneList.put(scene.getId(), scene);
                    }
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        //load items
        world.itemList.clear();
        xrp = resources.getXml(R.xml.items);
        try {
            int eventType;
            while ((eventType = xrp.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String s = xrp.getName();
                    if (s.equals(SgItem.XML_TAG)) {
                        Item item = SgItem.createByXml(xrp);
                        world.itemList.put(item.getId(), item);
                    }
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        //load system actors
        world.systemActorList.clear();
        //解析NPC列表
        xrp = resources.getXml(R.xml.system_actors);
        try {
            int eventType;
            while ((eventType = xrp.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String s = xrp.getName();
                    if (s.equals(SystemActor.XML_TAG)) {
                        SystemActor systemActor = new SystemActor();
                        systemActor.parseFromXml(xrp);
                        world.systemActorList.put(systemActor.getId(), systemActor);
                    }
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
//
//    private static <T extends Model> SparseArray<T> parseXml(Resources resources, int xmlId, String rootTag, Class<T> clazz) {
//        SparseArray<T> list = new SparseArray<>();
//        XmlResourceParser xrp = resources.getXml(xmlId);
//        try {
//            int eventType;
//            while ((eventType = xrp.next()) != XmlResourceParser.END_DOCUMENT) {
//                if (eventType == XmlResourceParser.START_TAG) {
//                    String s = xrp.getName();
//                    if (s.equals(rootTag)) {
//                        T t =
//                        list.put(t.getId(), t);
//                    }
//                }
//            }
//        } catch (XmlPullParserException | IOException | IllegalAccessException | InstantiationException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
}
