package com.mu.sanguo.heroes.model;

import android.content.res.XmlResourceParser;
import android.support.annotation.Nullable;

import com.mu.sanguo.heroes.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * !Created by muchunping on 2018/4/7.
 */
public class Player extends SgActor {
    private int physicalAttackPoint = Config.Basic_Pyhsical_Attack_Point;
    private int magicAttackPoint = Config.Basic_Magic_Attack_Point;
    private int physicalDefensePoint = Config.Basic_Pyhsical_Defense_Point;
    private int magicDefensePoint = Config.Basic_Magic_Defense_Point;
    private int healthPoint = Config.Basic_Health_Point;
    private int manaPoint = Config.Basic_Mana_Point;
    private int speedPoint = Config.Basic_Speed_Point;

    private final int DEFAULT_VIGOR = 50;

    private String name;
    private int level;
    private int sex;
    private int sceneId;
    private int vigor; //体力

    private FightProperty fightProperty;

    public Player(String name, int sex) {
        super(name);
        this.name = name;
        this.sex = sex;
        fightProperty = FightProperty.createWithDefaultValue();
        this.vigor = DEFAULT_VIGOR;
    }

    @Nullable
    public static Player readPlayerInfoFromString(String playerInfoString) {
        JSONObject object = null;
        try {
            object = new JSONObject(playerInfoString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (object == null) return null;
        Player player = new Player(object.optString("name"), object.optInt("level"));
        player.sex = object.optInt("sex");
        player.vigor = object.optInt("vigor");
        player.sceneId = object.optInt("sceneId");
        JSONObject fightObject = object.optJSONObject("fight_property");
        if (fightObject != null) {
            player.fightProperty = FightProperty.readFightPropertyFromJson(fightObject);
        } else {
            player.fightProperty = FightProperty.createWithDefaultValue();
        }
        return player;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getSex() {
        return sex;
    }

    public int getVigor() {
        return vigor;
    }

    public String getSexString() {
        return sex == 0 ? "男" : "女";
    }

    public int getSceneId() {
        return sceneId;
    }

    public FightProperty getFightProperty() {
        return fightProperty;
    }

    public void save(StringBuilder builder) {
        builder.append("name:'").append(getName()).append("'");
        builder.append(",");
        builder.append("sex:'").append(getSex()).append("'");
        builder.append(",");
        builder.append("level:'").append(getLevel()).append("'");
        builder.append(",");
        builder.append("vigor:'").append(getVigor()).append("'");
        builder.append(",");
        builder.append("sceneId:'").append(getSceneId()).append("'");
        builder.append(",");
        builder.append("fightProperty:{");
        fightProperty.save(builder);
        builder.append("}");
    }

    @Override
    public void parseFromXml(XmlResourceParser xrp) throws XmlPullParserException, IOException {

    }
}
