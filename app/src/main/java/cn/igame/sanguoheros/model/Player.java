package cn.igame.sanguoheros.model;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 玩家
 * Created by Administrator on 2015/11/9.
 */
public class Player extends TurnActor {
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
        setSpeed(fightProperty.speedPoint);
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

    @Override
    public void action() {

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
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", sex=" + sex +
                ", sceneId=" + sceneId +
                ", vigor=" + vigor +
                ", fightProperty=" + fightProperty +
                '}';
    }
}
