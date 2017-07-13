package com.mu.isanguo.model.player;

import com.mu.isanguo.core.SgWorld;

/**
 * !Created by muchunping on 2017/6/29.
 */

public class PeoplePlayer extends Player {
    public PeoplePlayer(String name, boolean male) {
        super(-1, name, male);
    }

    public void joinWorld(SgWorld world) {

    }

    public static PeoplePlayer createBy(String infoString) {
        return null;
    }
}
