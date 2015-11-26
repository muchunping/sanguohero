package cn.igame.sanguoheros.model;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.app.SgApplication;

/**
 * NPC
 * Created by Administrator on 2015/11/9.
 */
public class SystemActor {
    private String name;
    private String action;

    public void createFromXml(XmlResourceParser xrp) {
        Resources resources = SgApplication.getAppContext().getResources();
        name = resources.getString(xrp.getAttributeResourceValue(null, "name", R.string.app_name));
        action = resources.getString(xrp.getAttributeResourceValue(null, "action", R.string.app_name));
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "SystemActor{" +
                "name='" + name + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
