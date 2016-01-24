package cn.igame.sanguoheros.model;

/**
 * 怪物
 * Created by Administrator on 2016/1/10.
 */
public class Ogre {
    public static final int TYPE_NORMAL = 0;    //普通怪
    public static final int TYPE_ELITE = 1;     //精英怪
    public static final int TYPE_BOSS = 2;      //BOSS怪

    private String name;
    private int level;
    private int type = TYPE_NORMAL;
    private FightProperty fightProperty;

    public Ogre() {
    }
}
