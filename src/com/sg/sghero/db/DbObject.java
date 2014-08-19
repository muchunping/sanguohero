package com.sg.sghero.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Parcel;
import android.os.Parcelable;

public abstract class DbObject implements Parcelable{
	protected int code;
	protected String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public final void createFromCursor(Cursor c) {
		ContentValues cv = new ContentValues(c.getColumnCount());
		DatabaseUtils.cursorRowToContentValues(c, cv);
		createFromContentValues(cv);
	}

	/**
	 * ��{@link ContentValues}��ȡ��ֵ��������Ӧ����
	 */
	protected void createFromContentValues(ContentValues cv){
		Integer i;
		String s;
		
		i = cv.getAsInteger(FIELD_CODE);
		if (i != null) code = i;

		s = cv.getAsString(FIELD_NAME);
		if (s != null) name = s;
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
	
	@Override
	public int describeContents() {
		return 0;
	}

	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	
	public static final String SPLIT_SYMBOL = "-";
}
