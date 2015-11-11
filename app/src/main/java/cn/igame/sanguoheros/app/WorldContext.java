package cn.igame.sanguoheros.app;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import cn.igame.sanguoheros.model.Player;
import cn.igame.sanguoheros.model.Scene;

/**
 * 游戏中的全局上下文
 * Created by Administrator on 2015/11/9.
 */
public class WorldContext {
    private Player player;      //玩家对象
    private Scene scene;        //当前的场景

    public void init() {
        //加载资源

    }

    public void joinWorld(@NonNull Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Scene getScene() {
        return scene;
    }

    public void initPlayer(Player player) {
        player.setLevel(0);
        player.setSceneId(10001);
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
