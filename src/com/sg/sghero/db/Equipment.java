package com.sg.sghero.db;

import android.content.ContentValues;

public class Equipment extends Props {
	protected int wearSlot = 0;
	
	protected int HP = 0;
	protected int MP = 0;
	protected int PA = 0;
	protected int MA = 0;
	protected int DEF = 0;
	protected int IAS = 0;
	protected int ACC = 0;
	protected int DOD = 0;
	
	public Equipment() {
		super();
		stackable = false;
	}
	
	@Override
	protected void createFromContentValues(ContentValues cv) {
		super.createFromContentValues(cv);
		
		Integer i;
		
		i = cv.getAsInteger(FIELD_WEARSLOT);
		if(i != null) wearSlot = i;
		
		i = cv.getAsInteger(FIELD_HP);
		if(i != null) HP = i;
		
		i = cv.getAsInteger(FIELD_MP);
		if(i != null) MP = i;
		
		i = cv.getAsInteger(FIELD_PA);
		if(i != null) PA = i;
		
		i = cv.getAsInteger(FIELD_MA);
		if(i != null) MA = i;
		
		i = cv.getAsInteger(FIELD_DEF);
		if(i != null) DEF = i;
		
		i = cv.getAsInteger(FIELD_IAS);
		if(i != null) IAS = i;
		
		i = cv.getAsInteger(FIELD_ACC);
		if(i != null) ACC = i;
		
		i = cv.getAsInteger(FIELD_DOD);
		if(i != null) DOD = i;
	}
	
	public int getWearSlot() {
		return wearSlot;
	}

	public int getHP() {
		return HP;
	}

	public int getMP() {
		return MP;
	}

	public int getPA() {
		return PA;
	}

	public int getMA() {
		return MA;
	}

	public int getDEF() {
		return DEF;
	}

	public int getIAS() {
		return IAS;
	}

	public int getACC() {
		return ACC;
	}

	public int getDOD() {
		return DOD;
	}

	public static final String TABLE_NAME= "equipment";
	public static final String FIELD_WEARSLOT = "wearslot";
	public static final String FIELD_HP = "hp";
	public static final String FIELD_MP = "mp";
	public static final String FIELD_PA = "pa";
	public static final String FIELD_MA = "ma";
	public static final String FIELD_DEF = "def";
	public static final String FIELD_IAS = "ias";
	public static final String FIELD_ACC = "acc";
	public static final String FIELD_DOD = "dod";
}
