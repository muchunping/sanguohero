package com.sg.sghero.db;

import android.content.ContentValues;

public final class Scene extends DbObject {
	private int type;
	private int[] neighborsCode;
	private int[] npcsCode;
	private SystemActor[] npcs;
	private Scene[] neighbors;
	
	public void rebuild(DataProvider db){
		db.open();
		npcs = db.querySysActor(npcsCode);
		neighbors = db.querySceneList(neighborsCode);
		db.close();
	}

	@Override
	protected void createFromContentValues(ContentValues cv) {
		super.createFromContentValues(cv);
		Integer i;
		String s;
		String[] ss;

		i = cv.getAsInteger(FIELD_TYPE);
		if(i != null) type = i;

		s = cv.getAsString(FIELD_NEIGHBOR);
		if(s != null && !s.isEmpty()){
			ss = s.split(SPLIT_SYMBOL);
			neighborsCode = new int[ss.length];
			for (int j = 0; j < ss.length; j++) {
				neighborsCode[j] = Integer.parseInt(ss[j]);
			}
		}
		
		s = cv.getAsString(FIELD_NPCLIST);
		if(s != null && !s.isEmpty()){
			ss = s.split(SPLIT_SYMBOL);
			npcsCode = new int[ss.length];
			for (int j = 0; j < ss.length; j++) {
				npcsCode[j] = Integer.parseInt(ss[j]);
			}
		}
	}

	public int getType() {
		return type;
	}
	
	public SystemActor[] getNpcs() {
		return npcs;
	}

	public Scene[] getNeighbors() {
		return neighbors;
	}

	@Override
	public String toString() {
		return name;
	}

	public static final String TABLE_NAME= "scene";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_NEIGHBOR = "neighbor";
	public static final String FIELD_NPCLIST = "npclist";
	
	public static final int type_city = 1;
	public static final int type_wild = 2;
}
