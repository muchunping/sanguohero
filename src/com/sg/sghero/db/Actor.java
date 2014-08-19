package com.sg.sghero.db;

import android.content.ContentValues;

import com.sg.sghero.util.Range;

public class Actor extends DbObject{
	public static final class FightTraits {
		public Range HP; // healthPoint
		public Range MP; // manaPoint
		public int PA; // physicalAttack
		public int MA; // magicalAttack
		public int DEF; // defense
		public float BAT; // Base Attack Time
		public int ACC; // accuracy
		public int DOD; // dodge
		public int IAS; // Increase attack speed
//		public float AT; // attack time
		
		public float getAT(){
			float value =  BAT *= (1 + IAS / 100f);
			if(value < 0.8f * BAT)
				value = 0.8f * BAT;
			if(value > 1.4f * BAT)
				value = 1.4f * BAT;
			return value;
		}
	}
	
	protected int level;
	protected FightTraits fightTraits;
	
	public Actor() {
		fightTraits = new FightTraits();
		level = 0;
	}
	
	public final FightTraits getFightTraits(){
		return fightTraits;
	}
	
	public final int getLevel(){
		return level;
	}

	@Override
	protected void createFromContentValues(ContentValues cv) {
		super.createFromContentValues(cv);
		Integer i;
		Float f;
		
		i = cv.getAsInteger(FIELD_HP);
		if(i != null) fightTraits.HP = new Range(i, i);
				
		i = cv.getAsInteger(FIELD_MP);
		if(i != null) fightTraits.MP = new Range(i, i);
		
		i = cv.getAsInteger(FIELD_PA);
		if(i != null) fightTraits.PA = i;
		
		i = cv.getAsInteger(FIELD_DEF);
		if(i != null) fightTraits.DEF = i;
		
		i = cv.getAsInteger(FIELD_ACC);
		if(i != null) fightTraits.ACC = i;
		
		i = cv.getAsInteger(FIELD_DOD);
		if(i != null) fightTraits.DOD = i;
		
		f = cv.getAsFloat(FIELD_BAT);
		if(f != null) fightTraits.BAT = f;
		
		i = cv.getAsInteger(FIELD_LEVEL);
		if(i != null) level = i;
	}

	public static final String FIELD_HP = "hp";
	public static final String FIELD_MP = "mp";
	public static final String FIELD_PA = "attack";
	public static final String FIELD_MA = "ma";
	public static final String FIELD_BAT = "speed";
	public static final String FIELD_DEF = "defense";
	public static final String FIELD_ACC = "accuracy";
	public static final String FIELD_DOD = "dodge";
	public static final String FIELD_LEVEL = "level";
}
