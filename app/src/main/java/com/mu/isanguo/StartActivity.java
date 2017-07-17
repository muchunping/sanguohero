package com.mu.isanguo;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mu.isanguo.core.SgWorld;
import com.mu.isanguo.model.player.PeoplePlayer;
import com.mu.isanguo.ui.CreatePeopleFragment;

public class StartActivity extends AppCompatActivity {
    public static final String FRAGMENT_CREATE_PEOPLE = "create people";

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
        Fragment fragment = getFragmentManager().findFragmentByTag(FRAGMENT_CREATE_PEOPLE);
        if (fragment == null) {
            fragment = new CreatePeopleFragment();
            getFragmentManager().beginTransaction().replace(R.id.root_layout, fragment, FRAGMENT_CREATE_PEOPLE).commit();
        }else {
            getFragmentManager().beginTransaction().show(fragment).commit();
        }
    }

    private void enterWorld() {

    }
}
