package cn.igame.sanguoheros.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.igame.sanguoheros.R;

public class EquipmentFragment extends Fragment {
    private ImageView hatView;
    private ImageView necklaceView;
    private ImageView clothesView;
    private ImageView trousersView;
    private ImageView shoesView;
    private ImageView leftBraceletView;
    private ImageView rightBraceletView;
    private ImageView leftRingView;
    private ImageView rightRingView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_equipment, container, false);
        hatView = root.findViewById(R.id.hatView);
        necklaceView = root.findViewById(R.id.necklaceView);
        clothesView = root.findViewById(R.id.clothesView);
        trousersView = root.findViewById(R.id.trousersView);
        shoesView = root.findViewById(R.id.shoesView);
        leftBraceletView = root.findViewById(R.id.leftBraceletView);
        rightBraceletView = root.findViewById(R.id.rightBraceletView);
        leftRingView = root.findViewById(R.id.leftRingView);
        rightRingView = root.findViewById(R.id.rightRingView);
        return root;
    }
}
