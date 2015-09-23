package com.ilife.sanguohero.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class SgApplication extends Application {
	public WorldContext world = new WorldContext();
	
	public static final SgApplication getApplication(Activity activity){
		return (SgApplication) activity.getApplication();
	}
	
	public static final SgApplication getApplication(Context context){
		return getApplication((Activity)context);
	}
}
