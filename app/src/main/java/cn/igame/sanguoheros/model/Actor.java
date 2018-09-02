package cn.igame.sanguoheros.model;

/**
 * Actor
 * Created by Mouchunping018 on 2016/9/16.
 */
public abstract class Actor {
    protected String name;

    protected Actor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void action();
}
