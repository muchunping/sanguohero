package com.mu.sanguo.heroes.model;

/**
 * !Created by muchunping on 2018/4/7.
 */
public class SgActor implements Actor {

    protected String name;

    public SgActor(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
