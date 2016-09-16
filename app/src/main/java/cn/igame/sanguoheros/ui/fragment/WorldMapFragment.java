package cn.igame.sanguoheros.ui.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.util.Logger;
import cn.igame.sanguoheros.view.WorldMapView;

/**
 * 世界地图
 * Created by Mouchunping018 on 2016/9/16.
 */
public class WorldMapFragment extends DialogFragment {
    private WorldMapView worldMapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.custom_dialog_style);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_world_map, container, false);
        worldMapView = (WorldMapView) view.findViewById(R.id.worldMapView);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        worldMapView.setRegionList();
    }
}
