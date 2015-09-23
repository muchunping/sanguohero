package com.ilife.sanguohero.util;

import android.util.Log;

public class ILog {
	private static boolean debug = true;
	private static final String TAG = "SgHero";
	
	public static void i(String msg){
		if(debug)
			Log.i(TAG, msg + "");
	}
	
	public static void e(String msg){
		if(debug)
			Log.e(TAG, msg + "");
	}
}
