package com.mu.sanguo.heroes.context;

import android.util.SparseArray;

import com.mu.sanguo.heroes.model.Actor;
import com.mu.sanguo.heroes.model.Item;
import com.mu.sanguo.heroes.model.Scene;
import com.mu.sanguo.heroes.model.SystemActor;

import java.util.ArrayList;
import java.util.List;

/**
 * !Created by muchunping on 2018/4/8.
 */
public class WorldContext {
    public Scene currentScene;

    public SparseArray<Scene> sceneList = new SparseArray<>();
    public SparseArray<Item> itemList = new SparseArray<>();
    public SparseArray<Actor> systemActorList = new SparseArray<>();

    public Actor findSystemActorById(int id) {
        return systemActorList.get(id);
    }
}
