package com.sg.sghero.db;

import android.content.ContentValues;

public final class SystemActor extends DbObject {
	private String action;

	@Override
	protected void createFromContentValues(ContentValues cv) {
		super.createFromContentValues(cv);
		String s;
		
		s = cv.getAsString(FIELD_ACTION);
		if(s != null) action = s;
	}

	public String getAction() {
		return action;
	}
	
	public static final String TABLE_NAME= "systemactor";
	public static final String FIELD_ACTION = "action";
	
}
