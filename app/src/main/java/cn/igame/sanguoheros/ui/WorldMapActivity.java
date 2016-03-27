package cn.igame.sanguoheros.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.util.Logger;
import cn.igame.sanguoheros.view.WorldMapView;

/**
 * 三国全图页面
 * Created by Administrator on 2015/11/25.
 */
public class WorldMapActivity extends AppCompatActivity{
    private WorldMapView worldMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
//        worldMapView = (WorldMapView) findViewById(R.id.worldMapView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Logger.dL(String.valueOf(hasFocus));
//        worldMapView.setRegionList();
    }
}
