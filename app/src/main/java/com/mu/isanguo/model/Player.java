package com.mu.isanguo.model;

/**
 * !Created by muchunping on 2017/6/29.
 */

public class Player {
    private final String name;
    private final boolean male;
    private int level;


    public Player(String name, boolean male) {
        this.name = name;
        this.male = male;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public boolean isMale() {
        return male;
    }
}
