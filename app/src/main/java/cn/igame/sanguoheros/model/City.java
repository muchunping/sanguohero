package cn.igame.sanguoheros.model;

import android.graphics.Point;

/**
 * 城市
 * Created by Administrator on 2015/11/9.
 */
public class City extends Scene{

    private Point location;

    public City(String name, int x, int y) {
        type = TYPE_CITY;
        super.name = name;
        location = new Point(x, y);
    }

    public Point getLocation() {
        return location;
    }
}
