package com.mu.isanguo;

import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mu.isanguo.core.SgWorld;
import com.mu.isanguo.model.player.PeoplePlayer;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SgWorld world = createWorld();
        PeoplePlayer player = loadPeopleIfExist();
        if (player == null) {
            createPeople();
        } else {
            player.joinWorld(world);
            enterWorld();
        }
    }

    private PeoplePlayer loadPeopleIfExist() {
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);
        String playerInfoString = spf.getString("player_info", null);
        if (playerInfoString != null) {
            return PeoplePlayer.createBy(playerInfoString);
        }
        return null;
    }

    private SgWorld createWorld() {
        ((SgApplication) getApplication()).initWorld();
        return ((SgApplication) getApplication()).getWorld();
    }

    private void createPeople() {

    }

    private void enterWorld() {

    }
}
