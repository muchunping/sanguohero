package com.sg.sghero.model;

import android.util.SparseArray;

import com.sg.sghero.db.Actor.FightTraits;
import com.sg.sghero.db.Equipment;

public final class WearSlot {

	public enum SlotType {
		head("头", 0), neck("脖子", 1), body("身体", 2), belt("腰", 3), leg("腿", 4), foot(
				"脚", 5), wrist("手腕",6), finger("手指", 7);

		String description;
		int value;

		SlotType(String desc, int v) {
			description = desc;
			value = v;
		}
	}
	
	public interface OnWearChangedListener{
		public void wearChanged(Equipment older, Equipment newer);
	}
	
	private SparseArray<Equipment> wears = new SparseArray<Equipment>(8);
	private OnWearChangedListener listener;

	public void setListener(OnWearChangedListener listener) {
		this.listener = listener;
	}

	public boolean wear(Equipment equip){
		int slot = equip.getWearSlot();
		Equipment older = wears.get(slot);
		if(equip.equals(older)) return false;
		wears.put(slot, equip);
		if(listener != null) listener.wearChanged(older, equip);
		return true;
	}
	
	public boolean strip(int slot){
		Equipment equip = wears.get(slot);
		if(equip == null) return false;
		wears.remove(slot);
		if(listener != null) listener.wearChanged(null, equip);
		return true;
	}
	
	public Equipment getEquipmentOnSlot(int slot){
		return wears.get(slot);
	}
	
	public Equipment getEquipmentOnSlot(SlotType type){
		return getEquipmentOnSlot(type.value);
	}
	
	/**
	 * 计算穿戴的装备的属性
	 */
	public void calculateWearTrait(FightTraits ft){
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
