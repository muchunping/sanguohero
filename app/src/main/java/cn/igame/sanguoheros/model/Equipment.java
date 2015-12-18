package cn.igame.sanguoheros.model;

/**
 * 装备
 * Created by Administrator on 2015/12/16.
 */
public class Equipment extends Goods{

    public Equipment(int type, String name, int picPath, String description, int level, int rareStar,
                     int attackPoint, int defensePoint, int healthPoint, int manaPoint, int speedPoint) {
        super(name, description, picPath);
        this.level = level;
        this.rareStar = rareStar;
        this.type = Type.valueOf(type);

        fightProperty = new FightProperty();
        fightProperty.attackPoint = attackPoint;
        fightProperty.defensePoint = defensePoint;
        fightProperty.healthPoint = healthPoint;
        fightProperty.manaPoint = manaPoint;
        fightProperty.speedPoint = speedPoint;
    }

    enum Type {
        WEAPON(1){
            @Override
            public String toString() {
                return "武器";
            }
        },
        HELMET(2),
        CLOTHES(3),
        SHOES(4),
        BRACELET(5),
        RING(6),
        NECKLACE(7);

        int value;

        Type(int i) {
            value = i;
        }

        static Type valueOf(int type) {
            switch (type) {
                case 1:
                    return WEAPON;
                case 2:
                    return HELMET;
                case 3:
                    return CLOTHES;
                case 4:
                    return SHOES;
                case 5:
                    return BRACELET;
                case 6:
                    return RING;
                case 7:
                    return NECKLACE;
            }
            return WEAPON;
        }
    }

    protected final Type type;
    protected final FightProperty fightProperty;
    protected final int level;
    protected final int rareStar;

    public Type getType() {
        return type;
    }


    public FightProperty getFightProperty() {
        return fightProperty;
    }

    public int getLevel() {
        return level;
    }

    public int getRareStar() {
        return rareStar;
    }
}
