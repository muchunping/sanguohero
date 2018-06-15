package com.mu.sanguo.heroes.model;

import android.graphics.Point;

import java.util.List;

/**
 * !Created by muchunping on 2018/4/7.
 */
public interface Scene {
    void search();

    Scene[] getGates();

    int getId();

    String getName();

    int getType();

    List<Integer> getSystemActorList();

    List<Scene> getNeighborList(List<Scene> sceneList);

    Point getLocation();
}
