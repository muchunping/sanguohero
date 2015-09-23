package com.ilife.sanguohero.db;

import android.content.ContentValues;

public class Consumables extends Props {
	/** 生命回复量 */
	private int hp;
	/** 魔法回复量 */
	private int mp;

	public Consumables() {
		stackable = true;
		useable = true;
		type = Type.consumbables;
	}
	
	@Override
	protected void createFromContentValues(ContentValues cv) {
		super.createFromContentValues(cv);
		Integer i;
		
		i = cv.getAsInteger(FIELD_HP);
		if (i != null) hp = i;
		
		i = cv.getAsInteger(FIELD_MP);
		if (i != null) mp = i;
	}

	public int getHp() {
		return hp;
	}

	public int getMp() {
		return mp;
	}
	
	public String getEffectsString(){
		StringBuilder builder = new StringBuilder();
		if(hp > 0)
			builder.append("回复生命值" + hp + "点");
		if(mp > 0){
			if(builder.length() > 0){
				builder.append(",");
				builder.append("\n");
			}
			builder.append("回复魔法值" + mp + "点");
		}
		return builder.toString();
	}
	
	public static final String FIELD_HP = "hp";
	public static final String FIELD_MP = "mp";
	public static final String TABLE_NAME = "consumables";
}
