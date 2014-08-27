package com.sg.sghero.db;

import android.content.ContentValues;

public final class SystemActor extends DbObject {
	public static final int CODE_WEAPON = 10;
	public static final int CODE_ARMOR = 11;
	public static final int CODE_JEWELRY = 12;
	public static final int CODE_DRUG = 13;
	public static final int CODE_HOTEL = 14;
	public static final int CODE_HOTEL_DEPUTY = 15;
	public static final int CODE_TAVERN = 16;
	
	private String[] action;

	@Override
	protected void createFromContentValues(ContentValues cv) {
		super.createFromContentValues(cv);
		String s;
		
		s = cv.getAsString(FIELD_ACTION);
		if(s != null) {
			action = s.split(SPLIT_SYMBOL);
		}
	}

	public String[] getAction() {
		return action;
	}
	
	public static final String TABLE_NAME= "systemactor";
	public static final String FIELD_ACTION = "action";
	
}
