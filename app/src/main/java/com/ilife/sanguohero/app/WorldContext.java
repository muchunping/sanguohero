package com.ilife.sanguohero.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;

import com.ilife.sanguohero.R;
import com.ilife.sanguohero.db.Actor;
import com.ilife.sanguohero.db.DataProvider;
import com.ilife.sanguohero.db.Scene;
import com.ilife.sanguohero.model.Player;
import com.ilife.sanguohero.resoure.IconResourceFile;
import com.ilife.sanguohero.resoure.IconsCache;
import com.ilife.sanguohero.util.Size;

public class WorldContext {
	public interface OnLoadingListener{
		/**
		 * @param progress The progress value, between 0 and 100
		 * @param doing the thing doing.
		 */
		void load(int progress, String doing);
	}

	
	private List<OnLoadingListener> loadingListeners = new ArrayList<>();
	private boolean isLoading = false;
	private boolean hasLoaded = false;
	/**
	 * @return true if has loaded, otherwise false.
	 */
	public boolean addOnLoadingListener(OnLoadingListener l){
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
		iconFile = new IconResourceFile(r, R.drawable.monsters_tometik6, new Size(7, 6), iconSize);
		iconsCache.cacheIconsFromResourceFile(r, iconFile);
		
		notifyLoadingListener(0, "Begin parse props");
		
		
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
	public Player player;
	private OnSceneChangeListener sceneChangeListener;
	public void initWorld(Context c, Scene scene, String name, String family, String idear, String style){
		currentScene = scene;
		currentScene.rebuild(new DataProvider(c));
		
		player = new Player(name);
		player.setLocation(currentScene);
	}
	
	public Scene getCurrentScene(){
		return currentScene;
	}
	
	public void enterNewScene(Context c, Scene newScene){
		currentScene = newScene;
		currentScene.rebuild(new DataProvider(c));
		player.setLocation(currentScene);
		if(sceneChangeListener != null)
			sceneChangeListener.sceneChanged(newScene);
	}
	public void setSceneChangeListener(OnSceneChangeListener sceneChangeListener) {
		this.sceneChangeListener = sceneChangeListener;
	}
	
	private State state = State.Normal;
	public enum State{
		Normal, Battle,xn8wn4,ovown4
	}
	
	public void setState(State newState){
		if(newState == state) return;
		
		this.state = newState;
		if(newState != State.Battle){
			attackers = null;
			defenders = null;
		}
	}
	public State getState(){
		return state;
	}
	
	public Actor[] getAttackers() {
		return attackers;
	}
	public void setAttackers(Actor[] attackers) {
		this.attackers = attackers;
	}

	public Actor[] getDefenders() {
		return defenders;
	}
	public void setDefenders(Actor[] defenders) {
		this.defenders = defenders;
	}

	private Actor[] attackers;
	private Actor[] defenders;
}
