package com.sg.sghero.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Parcel;
import android.os.Parcelable;

public class DbObject implements Parcelable{
	protected int code;
	protected String name;
	protected int level;
	protected String photo;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public int getLevel(){
		return level;
	}
	
	public String getPhoto(){
		return photo;
	}

	public final void createFromCursor(Cursor c) {
		ContentValues cv = new ContentValues(c.getColumnCount());
		DatabaseUtils.cursorRowToContentValues(c, cv);
		createFromContentValues(cv);
	}

	/**
	 * 
	 */
	protected void createFromContentValues(ContentValues cv){
		Integer i;
		String s;
		
		i = cv.getAsInteger(FIELD_CODE);
		if (i != null) code = i;

		s = cv.getAsString(FIELD_NAME);
		if (s != null) name = s;
		
		i = cv.getAsInteger(FIELD_LEVEL);
		if (i != null) level = i;

		s = cv.getAsString(FIELD_PHOTO);
		if (s != null) photo = s;
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(code);
		dest.writeString(name);
	}
	
	protected DbObject(Parcel in){
		
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	public static final Parcelable.Creator<DbObject> CREATOR = new Parcelable.Creator<DbObject>() {
		public DbObject createFromParcel(Parcel in) {
			return new DbObject(in);
		}

		public DbObject[] newArray(int size) {
			return new DbObject[size];
		}
	};


	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_LEVEL = "level";
	public static final String FIELD_PHOTO = "photo";
	
	public static final String SPLIT_SYMBOL = "-";
	public static final String PHOTO_SYMBOL = "\\|";
}
