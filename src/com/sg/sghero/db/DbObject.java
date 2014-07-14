package com.sg.sghero.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;

public abstract class DbObject {
	protected int code;
	protected String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void createFromCursor(Cursor c) {
		ContentValues cv = new ContentValues(c.getColumnCount());
		DatabaseUtils.cursorRowToContentValues(c, cv);
		createFromContentValues(cv);
	}

	/**
	 * 从{@link ContentValues}中取出值赋予对象对应属性
	 */
	protected abstract void createFromContentValues(ContentValues cv);
	
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	
	public static final String SPLIT_SYMBOL = "-";
}
