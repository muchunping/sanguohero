package com.sg.sghero.db;

import android.content.ContentValues;

public class Monster extends Actor {
	private int experience;
	private int location;
	private int[] loot;
	
	public Monster() {
		super();
	}

	@Override
	protected void createFromContentValues(ContentValues cv) {
		super.createFromContentValues(cv);
		Integer i;
		String s;
		String[] ss;
		
		i = cv.getAsInteger(FIELD_EXPERIENCE);
		if(i != null) experience = i;
		
		i = cv.getAsInteger(FIELD_LOCATION);
		if(i != null) location = i;

		s = cv.getAsString(FIELD_LOOT);
		if(s != null && !s.isEmpty()){
			ss = s.split(SPLIT_SYMBOL);
			loot = new int[ss.length];
			for (int j = 0; j < ss.length; j++) {
				loot[j] = Integer.parseInt(ss[j]);
			}
		}
			
	}

	public int getExperience() {
		return experience;
	}

	public int getLocation() {
		return location;
	}

	public int[] getLoot() {
		return loot;
	}

	public static final String TABLE_NAME = "monster";
	public static final String FIELD_EXPERIENCE = "experience";
	public static final String FIELD_LOCATION = "scene";
	public static final String FIELD_LOOT = "loot";

	public static final String PHOTO_PREFIX = "com.sg.sanguohero:drawable/monsters_";
}
