package com.mu.sanguo.heroes;

import android.content.res.Resources;
import android.os.AsyncTask;

import com.mu.sanguo.heroes.context.ControllerContext;
import com.mu.sanguo.heroes.context.WorldContext;
import com.mu.sanguo.heroes.resource.ResourceLoader;

import java.lang.ref.WeakReference;

/**
 * !Created by muchunping on 2018/4/8.
 */
public class WorldSetup {
    private int initStatus= 0; //未开始，1进行中，2已完成
    private WeakReference<OnResourcesLoadedListener> onResourcesLoadedListener;
    private WorldContext world;

    public WorldSetup(WorldContext world, ControllerContext controllers, SgApplication application) {
        this.world = world;
    }

    public void setOnResourcesLoadedListener(OnResourcesLoadedListener listener) {
        synchronized (this) {
            onResourcesLoadedListener = null;
            if (initStatus == 2) {
                if (listener != null) listener.onResourcesLoaded();
                return;
            }
            onResourcesLoadedListener = new WeakReference<>(listener);
        }
    }

    public void startResourceLoader(final Resources r) {
        if (initStatus != 0) return;

        synchronized (this) {
            initStatus = 1;
        }

        //And the rest asynchronously
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... arg0) {
                ResourceLoader.loadResourcesAsync(world, r);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                synchronized (WorldSetup.this) {
                    initStatus = 2;

                    if (onResourcesLoadedListener == null) return;
                    WorldSetup.OnResourcesLoadedListener listener = onResourcesLoadedListener.get();
                    onResourcesLoadedListener = null;
                    if (listener == null) return;
                    listener.onResourcesLoaded();
                }
            }
        }.execute();
    }

    public interface OnSceneLoadedListener {
        void onSceneLoaded();
        void onSceneLoadFailed();
    }
    public interface OnResourcesLoadedListener {
        void onResourcesLoaded();
    }
}
