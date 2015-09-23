package com.ilife.sanguohero.db;

import android.content.ContentValues;

/**
 * 
 */
public abstract class Props extends DbObject {
	public enum Type{
		consumbables,
		equipment
	}
	
	protected boolean stackable = false;
	protected int amount = 1;
	protected boolean useable = false;
	private String photoString;
	protected Type type;
	private int price;

	@Override
	protected void createFromContentValues(ContentValues cv) {
		super.createFromContentValues(cv);
		Integer i;
		String s;
		
		i = cv.getAsInteger(FIELD_PRICE);
		if (i != null) price = i;
		
		s = cv.getAsString(FILED_PHOTO);
		if(s != null) photoString = s;
	}
	

	public boolean isStackable() {
		return stackable;
	}
	public int getAmount() {
		return amount;
	}
	
	public void amountPlus(){
		amount ++;
	}
	public void amountReduce(){
		amount --;
	}
	public boolean isUseable() {
		return useable;
	}
	public String getPhotoString() {
		return photoString;
	}
	public int getPrice() {
		return price;
	}
	
	public static final String FIELD_STACKABLE = "stackable";
	public static final String FIELD_AMOUNT = "amount";
	public static final String FILED_USEABLE = "useable";
	public static final String FILED_PHOTO = "photo";
	public static final String FIELD_PRICE = "price";
	public static final String PHOTO_PREFIX = "com.ilife.sanguohero:drawable/items_";
}
