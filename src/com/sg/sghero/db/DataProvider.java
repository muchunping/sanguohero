package com.sg.sghero.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataProvider {
	private static final String DB_NAME = "sanguohero";
	private static final int DB_VERSION = 2;

	private SQLiteDatabase mDb;
	private Context context;

	public DataProvider(Context c) {
		context = c;
		
		c.getDatabasePath(DB_NAME);
	}

	public void updateDatabase() {
		File file = context.getDatabasePath(DB_NAME);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdir();
		try {
			InputStream is = context.getAssets().open("sanguohero.db");
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = new byte[40000];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			fos.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void open() {
		File file = context.getDatabasePath(DB_NAME);
		if(!file.exists())
			updateDatabase();
		String path = file.getPath();
		mDb = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		int oldVersion = mDb.getVersion();
		if(oldVersion < DB_VERSION){
			mDb.close();
			updateDatabase();
			mDb = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
//			mDb.setVersion(DB_VERSION);
		}
	}

	public void close() {
		mDb.close();
	}
	
	public Scene queryScene(String where){
		Cursor c = mDb.query(Scene.TABLE_NAME, null, where, null, null, null, null);
		if(c == null) return null;
		if(c.getCount() <= 0) return null;
		if(!c.moveToFirst()) return null;
		Scene s = new Scene();
		s.createFromCursor(c);
		c.close();
		return s;
	}
	
	public Monster[] queryMonster(String where){
		Cursor c = mDb.query(Monster.TABLE_NAME, null, where, null, null, null, null);
		if(c == null) return null;
		if(c.getCount() <= 0) return null;
		
		Monster[] ms = new Monster[c.getCount()];
		for (int i = 0; i < ms.length; i++) {
			if(!c.moveToNext()) break;
			ms[i] = new Monster();
			ms[i].createFromCursor(c);
		}
		c.close();
		return ms;
	}
	
	
	public SystemActor[] querySysActor(){
		Cursor c = mDb.query(SystemActor.TABLE_NAME, null, null, null, null, null, null);
		if(c == null) return null;
		if(c.getCount() <= 0) return null;
		
		SystemActor[] sa = new SystemActor[c.getCount()];
		for (int i = 0; i < sa.length; i++) {
			if(!c.moveToNext()) break;
			sa[i] = new SystemActor();
			sa[i].createFromCursor(c);
		}
		c.close();
		return sa;
	}
}
