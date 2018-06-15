package com.mu.sanguo.heroes.model;

import java.util.Collections;
import java.util.List;

/**
 * !Created by muchunping on 2018/4/7.
 */
public abstract class SgActor implements Actor, XmlCreator {

    protected String name;

    SgActor(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getActions() {
        return Collections.emptyList();
    }
}
