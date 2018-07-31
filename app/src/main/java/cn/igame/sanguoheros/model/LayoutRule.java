package cn.igame.sanguoheros.model;

/**
 * !Created by muchunping on 2018/7/19.
 */
public enum LayoutRule {
    LONG_FEI("龙飞阵", 1, 2, 3, 5, 8),
    HU_XIAO("虎啸阵", 2, 4, 6, 7, 9);

    public String name;
    public int[] layout;

    LayoutRule(String name, int i, int i1, int i2, int i3, int i4) {
        this.name = name;
        layout = new int[]{i, i1, i2, i3, i4};
    }
}
