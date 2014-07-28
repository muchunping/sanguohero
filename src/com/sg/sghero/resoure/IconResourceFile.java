/**
 * 
 */
package com.sg.sghero.resoure;

import com.sg.sghero.util.Size;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class IconResourceFile {
	private String name;
	private int resourceId;
	private Size numSize;
	private int iconWidth;
	private int iconHeight;
	
	public IconResourceFile(Resources r, int resId, Size number, Size iconSize){
		this.name = r.getResourceName(resId);
		resourceId = resId;
		this.numSize = number;
		this.iconWidth = iconSize.width;
		this.iconHeight = iconSize.height;
	}
	
	public Bitmap createSourceBitmap(Resources r){
		Bitmap result ;
		Options o = new Options();
		o.inScaled = false;
		result = BitmapFactory.decodeResource(r, resourceId, o);
		result.setDensity(Bitmap.DENSITY_NONE);
		return result;
	}

	public String getName() {
		return name;
	}

	public int getResourceId() {
		return resourceId;
	}

	public Size getNumSize() {
		return numSize;
	}

	public int getIconWidth() {
		return iconWidth;
	}

	public int getIconHeight() {
		return iconHeight;
	}
}
