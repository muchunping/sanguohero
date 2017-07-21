package com.mu.isanguo.model.player;

/**
 * !Created by muchunping on 2017/6/29.
 */

public class Player {
    private final int id;
    protected final String name;
    protected final boolean male;
    protected int level;


    public Player(int id, String name, boolean male) {
        this.id = id;
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

    public int getId() {
        return id;
    }
}
