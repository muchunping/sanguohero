package com.sg.sghero.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;

import com.sg.sanguohero.R;
import com.sg.sghero.db.DataProvider;
import com.sg.sghero.db.Scene;
import com.sg.sghero.model.Player;
import com.sg.sghero.resoure.IconResourceFile;
import com.sg.sghero.resoure.IconsCache;
import com.sg.sghero.util.Size;

public class WorldContext {
	public interface OnLoadingListener{
		/**
		 * @param progress The progress value, between 0 and 100
		 * @param doing the thing doing.
		 */
		public void load(int progress, String doing);
	}

	
	private List<OnLoadingListener> loadingListeners = new ArrayList<OnLoadingListener>();
	private boolean isLoading = false;
	private boolean hasLoaded = false;
	/**
	 * @return true if has loaded, otherwise false.
	 */
	public boolean addOnLoadingLinstener(OnLoadingListener l){
		if(hasLoaded) {
//			l.load(100, "Load has done");
			return true;
		}
		
		loadingListeners.add(l);
		return false;
	}
	public void removeOnLoadingListener(OnLoadingListener l){
		loadingListeners.remove(l);
	}
	public void load(Resources r){
		if(isLoading || hasLoaded) return;
		isLoading = true;
		final Size iconSize = new Size(32, 32);
		IconsCache iconsCache = IconsCache.getInstance();

		notifyLoadingListener(0, "Begin loading resource");
		IconResourceFile iconFile = new IconResourceFile(r, R.drawable.items_consumables, new Size(14, 5), iconSize);
		iconsCache.cacheIconsFromResourceFile(r, iconFile);
		
		notifyLoadingListener(100, "Load has done");
		isLoading = false;
		hasLoaded = true;
	}
	
	private void notifyLoadingListener(int progress, String doing){
		for (OnLoadingListener l : loadingListeners) {
			if(l != null)
				l.load(progress, doing);
		}
	}
	
	public interface OnSceneChangeListener{
		public void sceneChanged(Scene newScene);
	}
	private Scene currentScene;
	private Player player;
	private OnSceneChangeListener sceneChangeListener;
	public void initWorld(Context c, Scene scene, String name, String family, String idear, String style){
		currentScene = scene;
		currentScene.rebuild(new DataProvider(c));
		
		player = new Player(name);
		player.setLocation(currentScene.getCode());
	}
	
	public Scene getCurrentScene(){
		return currentScene;
	}
	
	public void enterNewScene(Context c, Scene newScene){
		currentScene = newScene;
		currentScene.rebuild(new DataProvider(c));
		player.setLocation(currentScene.getCode());
		if(sceneChangeListener != null)
			sceneChangeListener.sceneChanged(newScene);
	}
	public void setSceneChangeListener(OnSceneChangeListener sceneChangeListener) {
		this.sceneChangeListener = sceneChangeListener;
	}
}
