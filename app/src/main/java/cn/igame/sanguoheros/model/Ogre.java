package cn.igame.sanguoheros.model;

/**
 * 怪物
 * Created by Administrator on 2016/1/10.
 */
public class Ogre extends TurnActor {
    public static final int TYPE_NORMAL = 0;    //普通怪
    public static final int TYPE_ELITE = 1;     //精英怪
    public static final int TYPE_BOSS = 2;      //BOSS怪

    private final String name;
    private final int level;
    private final int type;
    private FightProperty fightProperty;

    public Ogre(String name, int level, int type, FightProperty fightProperty) {
        super(name);
        this.name = name;
        this.level = level;
        this.type = type;
        this.fightProperty = fightProperty;
        setSpeed(fightProperty.speedPoint);
    }

    @Override
    public void action() {

    }
}
