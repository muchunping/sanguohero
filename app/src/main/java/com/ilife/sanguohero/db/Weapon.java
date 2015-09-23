package com.ilife.sanguohero.db;

import android.content.ContentValues;

public class Weapon extends Equipment {
	public int type;
	public int buyPrice;
	public int sellPrice;
	public int pa;
	public int ma;
	public int acc;
	public double bat;

	/**武器类型之剑*/
	public static final int TYPE_SWORD = 1;
	/**武器类型之刀*/
	public static final int TYPE_KNIFE = 2;
	/**武器类型之枪*/
	public static final int TYPE_SPEAR = 3;
	/**武器类型之扇*/
	public static final int TYPE_FAN = 4;
	/**武器类型之弓*/
	public static final int TYPE_BOW = 5;
	/**武器类型之锤*/
	public static final int TYPE_HAMMER = 6;
	
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
	
	public static final String TABLE_NAME= "weapon";
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
