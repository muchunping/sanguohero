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
	
	public Actor() {
		fightTraits = new FightTraits();
	}
	
	protected FightTraits fightTraits;
	
	public FightTraits getFightTraits(){
		return fightTraits;
	}

	@Override
	protected void createFromContentValues(ContentValues cv) {
		
	}

	public static final String FIELD_HP = "hp";
	public static final String FIELD_MP = "mp";
	public static final String FIELD_PA = "pa";
	public static final String FIELD_MA = "ma";
	public static final String FIELD_BAT = "bat";
	public static final String FIELD_DEF = "def";
	public static final String FIELD_ACC = "acc";
	public static final String FIELD_DOD = "dod";
}
