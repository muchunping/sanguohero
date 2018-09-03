package cn.igame.sanguoheros.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.model.Player;
import cn.igame.sanguoheros.model.Scene;
import cn.igame.sanguoheros.model.SystemActor;
import cn.igame.sanguoheros.util.Logger;

/**
 * 游戏中的全局上下文
 * Created by Administrator on 2015/11/9.
 */
public class WorldContext {
    private Player player;      //玩家对象
    private Scene scene;        //当前的场景

    private List<Scene> sceneList = new ArrayList<>();
    private List<SystemActor> systemActorList = new ArrayList<>();

    public void init(Context context) {
        //加载资源
        parseSystemActorXml(context.getResources());
        parseSceneXml(context.getResources());
    }

    //解析场景列表
    private void parseSceneXml(Resources resources) {
        Logger.dL("解析场景 -> 开始");
        sceneList.clear();
        XmlResourceParser xrp = resources.getXml(R.xml.scene);
        try {
            int eventType;
            while ((eventType = xrp.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String s = xrp.getName();
                    if (s.equals(Scene.XML_TAG)) {
                        Scene scene = Scene.parseFromXml(xrp);
                        sceneList.add(scene);
                    }
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        Logger.dL("解析场景 -> 完成:" + sceneList);
    }

    private void parseSystemActorXml(Resources resources) {
        Logger.dL("解析系统角色 -> 开始");
        systemActorList.clear();
        //解析NPC列表
        XmlResourceParser xrp = resources.getXml(R.xml.system_actor);
        try {
            int eventType;
            while ((eventType = xrp.next()) != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String s = xrp.getName();
                    if (s.equals(SystemActor.XML_TAG)) {
                        SystemActor systemActor = new SystemActor();
                        systemActor.createFromXml(xrp);
                        systemActorList.add(systemActor);
                    }
                }
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        Logger.dL("解析系统角色 -> 完成:" + systemActorList);
    }

    public void joinWorld(@NonNull Player player) {
        Logger.dL("玩家<" + player.getName() + ">进入了游戏:" + player);
        this.player = player;
        for (Scene scene : sceneList) {
            if (scene.getId() == player.getSceneId()) {
                this.scene = scene;
                Logger.dL("玩家" + player.getName() + "位于" + scene.getName());
                break;
            }
        }
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        this.player.setSceneId(scene.getId());
    }

    public Player getPlayer() {
        return player;
    }

    public Scene getScene() {
        return scene;
    }

    public List<Scene> getSceneList() {
        return sceneList;
    }

    public List<SystemActor> getSystemActorList() {
        return systemActorList;
    }

    public SystemActor findSystemActorById(int id) {
        for (SystemActor actor : systemActorList) {
            if (actor.getId() == id) {
                return actor;
            }
        }
        return null;
    }

    public void initPlayer(Player player) {
        Logger.dL("初始化玩家<" + player.getName() + ">的信息");
        player.setLevel(0);
        player.setSceneId(1001);
        joinWorld(player);
    }

    public void savePlayer() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        player.save(builder);
        builder.append("}");

        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(SgApplication.getAppContext());
        spf.edit().putString("player_info", builder.toString()).apply();
    }
}
