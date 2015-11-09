package cn.igame.sanguoheros.model;

/**
 * 场景类
 * Created by Administrator on 2015/11/9.
 */
public class Scene {
    public static final int TYPE_CITY = 0;          //城市
    public static final int TYPE_WILD = 1;          //荒野
    public static final int TYPE_ISOLATION = 2;     //副本

    private int id;
    private String name;
    private String description;
    private int type;
    private Scene[] neighbors;
}
