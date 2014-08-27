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
		synchronized (this) {
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
	}

	public void close() {
		if (mDb != null && mDb.isOpen()) {
			mDb.close();
			mDb = null;
        }
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
	
	public Scene[] queryAllScene(){
		Cursor c = mDb.query(Scene.TABLE_NAME, null, null, null, null, null, null);
		if(c == null) return null;
		if(c.getCount() <= 0) return null;
		
		Scene[] ss = new Scene[c.getCount()];
		for (int i = 0; i < ss.length; i++) {
			if(!c.moveToNext()) break;
			ss[i] = new Scene();
			ss[i].createFromCursor(c);
		}
		c.close();
		return ss;
	}
	
	public Scene[] querySceneList(int[] codeList){
		String where = SystemActor.FIELD_CODE + " in" + translateIntArray(codeList);
		Cursor c = mDb.query(Scene.TABLE_NAME, null, where, null, null, null, null);
		if(c == null) return null;
		if(c.getCount() <= 0) return null;
		
		Scene[] ss = new Scene[c.getCount()];
		for (int i = 0; i < ss.length; i++) {
			if(!c.moveToNext()) break;
			ss[i] = new Scene();
			ss[i].createFromCursor(c);
		}
		c.close();
		return ss;
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
	
	
	public SystemActor[] querySysActor(int[] npclist){
		if(npclist == null) return new SystemActor[0];
		String where = SystemActor.FIELD_CODE + " in" + translateIntArray(npclist);
		Cursor c = mDb.query(SystemActor.TABLE_NAME, null, where, null, null, null, null);
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
	
	public Consumables[] queryAllConsumables(){
		Cursor c = mDb.query(Consumables.TABLE_NAME, null, null, null, null, null, null);
		if(c == null) return null;
		if(c.getCount() <= 0)return null;
		
		Consumables[] cs = new Consumables[c.getCount()];
		for (int i = 0; i < cs.length; i++) {
			if(!c.moveToNext()) break;
			cs[i] = new Consumables();
			cs[i].createFromCursor(c);
		}
		c.close();
		return cs;
	}
	
	public Equipment[] queryAllEquipment(){
		Cursor c = mDb.query(Equipment.TABLE_NAME, null, null, null, null, null, null);
		if(c == null) return null;
		if(c.getCount() <= 0)return null;
		
		Equipment[] cs = new Equipment[c.getCount()];
		for (int i = 0; i < cs.length; i++) {
			if(!c.moveToNext()) break;
			cs[i] = new Equipment();
			cs[i].createFromCursor(c);
		}
		c.close();
		return cs;
	}
	
	private String translateIntArray(int[] array){
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		for (int i = 0; i < array.length - 1; i++) {
			builder.append(array[i]);
			builder.append(",");
		}
		builder.append(array[array.length - 1]);
		builder.append(")");
		return builder.toString();
	}
}
