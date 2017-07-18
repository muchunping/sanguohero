package com.mu.isanguo.model.player;

import com.mu.isanguo.core.SgWorld;

/**
 * !Created by muchunping on 2017/6/29.
 */

public class PeoplePlayer extends Player {

    public static final int Career_ZHanShi = 0;
    public static final int Career_FaShi = 1;
    public static final int Career_DaoShi = 2;

    private final int career;

    public PeoplePlayer(String name, boolean male, int career) {
        super(-1, name, male);
        this.career = career;
    }

    public void joinWorld(SgWorld world) {

    }

    public static PeoplePlayer createBy(String infoString) {
        return null;
    }

    public void save(){


    }
}
