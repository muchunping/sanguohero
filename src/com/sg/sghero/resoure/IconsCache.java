/**
 * 
 */
package com.sg.sghero.resoure;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.SparseIntArray;

/**
 * @author Administrator
 *
 */
public class IconsCache {

	private LruCache<Integer, Bitmap> cache = new LruCache<Integer, Bitmap>(500);
	private HashMap<String, SparseIntArray> gidPerFiles = new HashMap<String, SparseIntArray>();
	private HashMap<String, IconResourceFile> files = new HashMap<String, IconResourceFile>();
	private AtomicInteger counter = new AtomicInteger();
	
	private static IconsCache instance = new IconsCache();
	private IconsCache(){}
	public static IconsCache getInstance(){
		return instance;
	}
	
	/**œÚª∫¥Ê÷–¥ÊÕº∆¨*/
	public void cacheIconsFromResourceFile(Resources r, IconResourceFile iconFile){
		files.put(iconFile.getName(), iconFile);
		
		Bitmap source = iconFile.createSourceBitmap(r);
		if(source == null) return;
		
		SparseIntArray array = new SparseIntArray(iconFile.getNumSize().quadrature());
		for (int i = 0; i < iconFile.getNumSize().height; i++) {
			for (int j = 0; j < iconFile.getNumSize().width; j++) {
				int globalId = counter.getAndIncrement();
				int x = j * iconFile.getIconWidth();
				int y = i * iconFile.getIconHeight();
				Bitmap icon = Bitmap.createBitmap(source, x, y, iconFile.getIconWidth(), iconFile.getIconHeight());
				cache.put(globalId, icon);
				
				int localId = j + i * iconFile.getNumSize().width;
				array.put(localId, globalId);
			}
		}
		gidPerFiles.put(iconFile.getName(), array);
		source.recycle();
	}
	
	/**¥”ª∫¥Ê÷–»°Õº∆¨*/
	public Bitmap getIconByFileAndLocalId(Resources r, String name, int localId){
		int globalId = gidPerFiles.get(name).get(localId);
		Bitmap result = cache.get(globalId);
		if(result == null){
			result = createSingleIcon(r, files.get(name), localId);
			cache.put(globalId, result);
		}
		return result;
	}

	private Bitmap createSingleIcon(Resources r, IconResourceFile iconFile, int localId) {
		Bitmap source = iconFile.createSourceBitmap(r);
		if(source == null) return null;
		
		final int x = localId % iconFile.getNumSize().width;
		final int y = (localId - x) / iconFile.getNumSize().width;
		return Bitmap.createBitmap(source, x, y, iconFile.getIconWidth(), iconFile.getIconHeight());
	}
}
