package cn.igame.sanguoheros.model;

/**
 * 物品抽象类
 * Created by Administrator on 2015/12/18.
 */
public abstract class Goods {
    protected final int id;
    protected final String name;
    protected final String description;
    protected final int imageDescriptor;
    protected boolean stackable = false;

    Goods(int id, String name, String description, int imageDescriptor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageDescriptor = imageDescriptor;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageDescriptor() {
        return imageDescriptor;
    }
}
