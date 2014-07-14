package com.sg.sghero.db;

import android.content.ContentValues;

public final class SystemActor extends DbObject {
	private int actionId;

	@Override
	protected void createFromContentValues(ContentValues cv) {
		Integer i ;
		String s;
		
		i = cv.getAsInteger(FIELD_CODE);
		if(i != null) code = i;
		
		s = cv.getAsString(FIELD_NAME);
		if(s != null) name = s;
		
		i = cv.getAsInteger(FIELD_ACTION);
		if(i != null) actionId = i;
	}

	public int getAction() {
		return actionId;
	}

	public static final String TABLE_NAME= "sysactor";
	public static final String FIELD_ACTION = "action";
	
}
