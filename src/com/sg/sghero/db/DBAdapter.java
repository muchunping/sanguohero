package com.sg.sghero.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
	public static final String TABLE_NAME = "sanguohero";
	public static final int DB_VERSION = 1;
	
	private DatabaseOpenHelper helper;
	private SQLiteDatabase db;
	public DBAdapter(Context cxt) {
		helper = new DatabaseOpenHelper(cxt);
	}
	
	public void open(){
		db = helper.getWritableDatabase();
	}
	
	public void close(){
		db.close();
	}
	
	public class DatabaseOpenHelper extends SQLiteOpenHelper{

		public DatabaseOpenHelper(Context context) {
			super(context, TABLE_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		}
	}
}
