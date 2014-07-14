package com.sg.sghero.db;

import android.content.ContentValues;

public final class Scene extends DbObject {
	private int type;
	private int[] neighbors;

	@Override
	protected void createFromContentValues(ContentValues cv) {
		Integer i;
		String s;
		String[] ss;

		i = cv.getAsInteger(FIELD_CODE);
		if (i != null) code = i;

		s = cv.getAsString(FIELD_NAME);
		if (s != null) name = s;
		
		i = cv.getAsInteger(FIELD_TYPE);
		if(i != null) type = i;

		s = cv.getAsString(FIELD_NEIGHBOR);
		if(s != null && !s.isEmpty()){
			ss = s.split(SPLIT_SYMBOL);
			neighbors = new int[ss.length];
			for (int j = 0; j < ss.length; j++) {
				neighbors[j] = Integer.parseInt(ss[j]);
			}
		}
	}

	public int getType() {
		return type;
	}
	
	public int[] getNeighbors(){
		return neighbors;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public static final String TABLE_NAME= "scene";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_NEIGHBOR = "neighbor";
	
	public static final int type_city = 1;
	public static final int type_wild = 0;
}
