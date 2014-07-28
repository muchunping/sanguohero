package com.sg.sghero.app;

import com.sg.sanguohero.R;

import android.content.Context;

public final class Action {

	public static String show(Context c, String action){
		if(action.equalsIgnoreCase("merchandise"))
			return c.getString(R.string.action_merchandise);
		else if(action.equalsIgnoreCase("rest"))
			return c.getString(R.string.action_rest);
		else if(action.equalsIgnoreCase("inquire"))
			return c.getString(R.string.action_inquire);
		else if(action.equalsIgnoreCase("entrust"))
			return c.getString(R.string.action_entrust);
		else
			return action;
	}
}
