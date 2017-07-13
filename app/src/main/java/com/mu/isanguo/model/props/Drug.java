package com.mu.isanguo.model.props;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.mu.isanguo.utils.XmlResourceParserUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * 药品
 * !Created by muchunping on 2017/7/12.
 */

public class Drug extends Props {
    private int regainHealth;
    private int regainMana;

    public Drug(int id, String name, String description) {
        super(id, name, description, TYPE_DRUG);
    }

    @Override
    public void buildFromXml(Resources resources, XmlResourceParser xrp) throws IOException, XmlPullParserException {
        XmlResourceParserUtils.readCurrentTagUntilEnd(xrp, new XmlResourceParserUtils.TagHandler() {
            @Override
            public void handleTag(XmlResourceParser xrp, String tagName) throws XmlPullParserException, IOException {
                if (tagName.equals("health-regain")) {
                    regainHealth = Integer.parseInt(xrp.nextText());
                }
                if (tagName.equals("mana-regain")) {
                    regainMana = Integer.parseInt(xrp.nextText());
                }
            }
        });
    }
}
