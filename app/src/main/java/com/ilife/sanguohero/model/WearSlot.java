package com.ilife.sanguohero.model;

import android.util.SparseArray;

import com.ilife.sanguohero.db.Actor;
import com.ilife.sanguohero.db.Equipment;

public final class WearSlot {

    public enum SlotType {
        head("头", 0),
        neck("脖子", 1),
        body("上身", 2),
        leftWrist("左手腕", 3),
        rightWrist("右手腕", 4),
        leftFinger("左手指", 5),
        rightFinger("右手指", 6),
        leg("下身", 7),
        foot("腿", 8),
        medal("勋章", 9),
        amulet("护身符", 10);

        String description;
        int value;

        SlotType(String desc, int v) {
            description = desc;
            value = v;
        }
    }

    public interface OnWearChangedListener {
        void wearChanged(Equipment older, Equipment newer);
    }

    private SparseArray<Equipment> wears = new SparseArray<>(11);
    private OnWearChangedListener listener;

    public void setListener(OnWearChangedListener listener) {
        this.listener = listener;
    }

    public boolean wear(Equipment equip) {
        int slot = equip.getWearSlot();
        Equipment older = wears.get(slot);
        wears.put(slot, equip);
        if (equip.equals(older)) return false;
        if (listener != null) listener.wearChanged(older, equip);
        return true;
    }

    public boolean strip(int slot) {
        Equipment equip = wears.get(slot);
        if (equip == null) return false;
        wears.remove(slot);
        if (listener != null) listener.wearChanged(null, equip);
        return true;
    }

    public Equipment getEquipmentOnSlot(int slot) {
        return wears.get(slot);
    }

    public Equipment getEquipmentOnSlot(SlotType type) {
        return getEquipmentOnSlot(type.value);
    }

    /**
     * 计算装备属性
     */
    public void calculateWearTrait(Actor.FightTraits ft) {
        int size = wears.size();
        Equipment equip;
        for (int i = 0; i < size; i++) {
            equip = wears.valueAt(i);
            ft.HP.max += equip.getHP();
            ft.MP.max += equip.getMP();
            ft.PA += equip.getPA();
            ft.MA += equip.getMA();
            ft.DEF += equip.getDEF();
            ft.IAS += equip.getIAS();
            ft.ACC += equip.getACC();
            ft.DOD += equip.getDOD();
        }
    }
}
