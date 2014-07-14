package com.sg.sghero.db;

import android.content.ContentValues;

import com.sg.sghero.util.Range;

public class Monster extends Actor {
	private int experience;
	private int location;
	private int[] loot;
	
	public Monster() {
		super();
	}

	@Override
	protected void createFromContentValues(ContentValues cv) {
		Integer i;
		Float f;
		String s;
		String[] ss;
		
		i = cv.getAsInteger(FIELD_CODE);
		if (i != null) code = i;

		s = cv.getAsString(FIELD_NAME);
		if (s != null) name = s;
		
		i = cv.getAsInteger(FIELD_EXPERIENCE);
		if(i != null) experience = i;
		
		i = cv.getAsInteger(FIELD_LOCATION);
		if(i != null) location = i;
		
		i = cv.getAsInteger(FIELD_HP);
		if(i != null) fightTraits.HP = new Range(i, i);
		
		i = cv.getAsInteger(FIELD_MP);
		if(i != null) fightTraits.MP = new Range(i, i);
		
		f = cv.getAsFloat(FIELD_BAT);
		if(f != null) fightTraits.BAT = f;
		
		i = cv.getAsInteger(FIELD_PA);
		if(i != null) fightTraits.PA = i;
		
		i = cv.getAsInteger(FIELD_MA);
		if(i != null) fightTraits.MA = i;
		
		i = cv.getAsInteger(FIELD_DEF);
		if(i != null) fightTraits.DEF = i;
		
		i = cv.getAsInteger(FIELD_ACC);
		if(i != null) fightTraits.ACC = i;
		
		i = cv.getAsInteger(FIELD_DOD);
		if(i != null) fightTraits.DOD = i;
		
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
	public static final String FIELD_LOCATION = "location";
	public static final String FIELD_LOOT = "loot";
}
