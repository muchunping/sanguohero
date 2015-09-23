package com.ilife.sanguohero.model;

import android.util.SparseArray;

import com.ilife.sanguohero.db.Actor;
import com.ilife.sanguohero.db.Equipment;

public final class WearSlot {

	public enum SlotType {
		head("ͷ", 0), neck("����", 1), body("����", 2), belt("��", 3), leg("��", 4), foot(
				"��", 5), wrist("����",6), finger("��ָ", 7);

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
	 * ���㴩����װ��������
	 */
	public void calculateWearTrait(Actor.FightTraits ft){
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
