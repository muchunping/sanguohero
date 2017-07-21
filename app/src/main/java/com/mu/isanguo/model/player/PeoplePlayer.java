package com.mu.isanguo.model.player;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mu.isanguo.core.SgWorld;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * !Created by muchunping on 2017/6/29.
 */

public class PeoplePlayer extends Player {

    public static final int Career_ZHanShi = 0;
    public static final int Career_FaShi = 1;
    public static final int Career_DaoShi = 2;

    private final int career;
    private int sceneId;

    public PeoplePlayer(String name, boolean male, int career) {
        super(-1, name, male);
        this.career = career;
    }

    public void joinWorld(SgWorld world) {
        world.setCurrentScene(sceneId);
    }

    public static PeoplePlayer createBy(String infoString) {
        JSONObject object;
        try {
            object = new JSONObject(infoString);
            String name = object.getString("name");
            boolean male = object.getBoolean("male");
            int career = object.getInt("career");
            PeoplePlayer player = new PeoplePlayer(name, male, career);
            player.sceneId = object.getInt("sceneId");
            return player;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void save(Context context){
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
        spf.edit().putString("people_player_info", "").apply();
    }

    public void save(StringBuilder builder) {
        builder.append("name:'").append(name).append("'");
        builder.append(",");
        builder.append("male:'").append(male).append("'");
        builder.append(",");
        builder.append("career:'").append(career).append("'");
        builder.append(",");
        builder.append("level:'").append(level).append("'");
//        builder.append(",");
//        builder.append("vigor:'").append(getVigor()).append("'");
        builder.append(",");
        builder.append("sceneId:'").append(sceneId).append("'");
        builder.append(",");
        builder.append("fightProperty:{");
//        fightProperty.save(builder);
        builder.append("}");
    }
}
