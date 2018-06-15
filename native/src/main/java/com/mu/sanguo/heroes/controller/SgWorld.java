package com.mu.sanguo.heroes.controller;

import com.mu.sanguo.heroes.app.SgApplication;
import com.mu.sanguo.heroes.model.Monster;
import com.mu.sanguo.heroes.model.Scene;

import java.util.HashMap;

/**
 * !Created by muchunping on 2018/4/7.
 */
public final class SgWorld implements World {
    private final HashMap<String, Scene> sceneMap = new HashMap<>();
    private final HashMap<String, Monster> monsterHashMap = new HashMap<>();

    private Thread initThread = new Thread() {
        @Override
        public void run() {
            loadScenes();
            loadMonsters();
            loadProps();
        }
    };

    public SgWorld(SgApplication application) {

    }

    @Override
    public void createInBackground() {
        initThread.start();
    }

    @Override
    public void destroy() {

    }

    private void loadScenes() {
//        sceneMap.put("chang_an", new City());
//        sceneMap.put("luo_yang", new City());
//        sceneMap.put("du_she_gu", new Wild());
    }

    private void loadMonsters() {

    }

    private void loadProps() {

    }
}
