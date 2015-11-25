package cn.igame.sanguoheros.model;

/**
 * 场景类
 * Created by Administrator on 2015/11/9.
 */
public abstract class Scene {
    public static final int TYPE_CITY = 0;          //城市
    public static final int TYPE_WILD = 1;          //荒野
    public static final int TYPE_ISOLATION = 2;     //副本

    protected int id;
    protected String name;
    protected String description;
    protected int type;
    protected Scene[] neighbors;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

    public Scene[] getNeighbors() {
        return neighbors;
    }
}
