package com.mu.isanguo.core;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.mu.isanguo.R;
import com.mu.isanguo.model.props.Props;
import com.mu.isanguo.model.scene.Scene;
import com.mu.isanguo.model.player.SystemPlayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * !Created by muchunping on 2017/7/10.
 */

public class SgWorld {
    private List<Scene> sceneList = new ArrayList<>();
    private List<SystemPlayer> systemActorList = new ArrayList<>();
    private List<Props> propsList = new ArrayList<>();
    private int currentScene;

    public void init(Context context) {
        //加载资源
        parsePropsXml(context.getResources());
        parseSystemActorXml(context.getResources());
        parseSceneXml(context.getResources());
    }

    private void parsePropsXml(Resources resources) {
        propsList.clear();
        XmlResourceParser xrp = resources.getXml(R.xml.items);
        try {
            int eventType;
            while ((eventType = xrp.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String s = xrp.getName();
                    if (s.equals(Props.XML_TAG)) {
                        Props props = Props.parseFromXml(resources, xrp);
                        propsList.add(props);
                    }
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    //解析场景列表
    private void parseSceneXml(Resources resources) {
        sceneList.clear();
        XmlResourceParser xrp = resources.getXml(R.xml.scenes);
        try {
            int eventType;
            while ((eventType = xrp.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String s = xrp.getName();
                    if (s.equals(Scene.XML_TAG)) {
                        Scene scene = Scene.parseFromXml(resources, xrp);
                        sceneList.add(scene);
                    }
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    private void parseSystemActorXml(Resources resources) {
        systemActorList.clear();
        //解析NPC列表
        XmlResourceParser xrp = resources.getXml(R.xml.system_actors);
        try {
            int eventType;
            while ((eventType = xrp.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String s = xrp.getName();
                    if (s.equals(SystemPlayer.XML_TAG)) {
                        SystemPlayer systemActor = SystemPlayer.createFromXml(resources, xrp);
                        systemActorList.add(systemActor);
                    }
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentScene(int currentScene) {
        this.currentScene = currentScene;
    }
}
