package cn.igame.sanguoheros.model;

import org.json.JSONObject;

/**
 * 战斗属性
 * Created by Administrator on 2015/11/9.
 */
public class FightProperty {
    public static final int ATTACK_DEFAULT_POINT = 10;
    public static final int DEFENSE_DEFAULT_POINT = 10;
    public static final int HEALTH_DEFAULT_POINT = 100;
    public static final int MANA_DEFAULT_POINT = 100;
    public static final int SPEED_DEFAULT_POINT = 2000;

    public int attackPoint;    //攻击点数
    public int defensePoint;   //防御点数
    public int healthPoint;    //生命点数
    public int manaPoint;      //魔法点数
    public int speedPoint;     //速度点数

    public static FightProperty readFightPropertyFromJson(JSONObject object) {
        FightProperty property = new FightProperty();
        property.attackPoint = object.optInt("attack_point", 0);
        property.defensePoint = object.optInt("defense_oint", 0);
        property.healthPoint = object.optInt("health_point", 0);
        property.manaPoint = object.optInt("mana_point", 0);
        property.speedPoint = object.optInt("speed_point", 0);
        return property;
    }

    public static FightProperty createWithDefaultValue() {
        FightProperty property = new FightProperty();
        property.attackPoint = ATTACK_DEFAULT_POINT;
        property.defensePoint = DEFENSE_DEFAULT_POINT;
        property.healthPoint = HEALTH_DEFAULT_POINT;
        property.manaPoint = MANA_DEFAULT_POINT;
        property.speedPoint = SPEED_DEFAULT_POINT;
        return property;
    }

    public void save(StringBuilder builder) {
        builder.append("attackPoint:'").append(attackPoint).append("'");
        builder.append(",");
        builder.append("defensePoint:'").append(defensePoint).append("'");
        builder.append(",");
        builder.append("healthPoint:'").append(healthPoint).append("'");
        builder.append(",");
        builder.append("manaPoint:'").append(manaPoint).append("'");
        builder.append(",");
        builder.append("speedPoint:'").append(speedPoint).append("'");
    }
}
