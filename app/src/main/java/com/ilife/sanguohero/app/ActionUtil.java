package com.ilife.sanguohero.app;

import android.content.Context;

import com.ilife.sanguohero.R;
import com.ilife.sanguohero.db.SystemActor;

public final class ActionUtil {
	public enum Action {
		MERCHANDISE(1, "交易"), REST(2, "休息"), INQUIRE(3, "打听"), ENTRUST(4, "委托");

		String s;
		int v;

		Action(int value, String desc) {
			s = desc;
			v = value;
		}
	}

	public static String show(Context c, String action) {
		if (action.equalsIgnoreCase("merchandise"))
			return c.getString(R.string.action_merchandise);
		else if (action.equalsIgnoreCase("rest"))
			return c.getString(R.string.action_rest);
		else if (action.equalsIgnoreCase("inquire"))
			return c.getString(R.string.action_inquire);
		else if (action.equalsIgnoreCase("entrust"))
			return c.getString(R.string.action_entrust);
		else if(action.equalsIgnoreCase("entrust"))
			return c.getString(R.string.action_make);
		else
			return action;
	}
	
	public static void handleAction(SystemActor actor, String action) {
		handleAction(actor, Action.valueOf(action));
	}

	public static void handleAction(SystemActor actor, Action action) {
		switch (action) {
		case MERCHANDISE:

			break;
		case REST:

			break;
		case INQUIRE:

			break;
		case ENTRUST:

			break;
		}
	}
}
