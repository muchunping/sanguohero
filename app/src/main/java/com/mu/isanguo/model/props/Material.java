package com.mu.isanguo.model.props;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * 材料
 * !Created by muchunping on 2017/7/12.
 */

public class Material extends Props {
    public Material(int id, String name, String description) {
        super(id, name, description, TYPE_MATERIAL);
    }

    @Override
    public void buildFromXml(Resources resources, XmlResourceParser xrp) throws IOException, XmlPullParserException {

    }
}
