package com.mu.sanguo.heroes.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.mu.sanguo.heroes.R;

/**
 * !Created by muchunping on 2018/4/11.
 */
public class PlayInfoFragment extends Fragment {
    private View rootView;
    private ValueAnimator valueAnimator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide enterTransition = new Slide(Gravity.TOP);
            enterTransition.setInterpolator(new AccelerateInterpolator(2));
            Slide exitTransition = new Slide(Gravity.TOP);
            exitTransition.setInterpolator(new DecelerateInterpolator(2));
            setEnterTransition(enterTransition);
            setExitTransition(exitTransition);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_player_info_1, container, false);
        rootView.findViewById(R.id.blankArea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().hide(PlayInfoFragment.this).commit();
            }
        });
        return rootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (valueAnimator == null) {
            valueAnimator = ObjectAnimator.ofFloat(rootView, "y", 0, 1);
        }
        int height = rootView.getMeasuredHeight();
        if (hidden) {
            valueAnimator.setFloatValues(0, -height);
        } else {
            valueAnimator.setFloatValues(-height, 0);
        }
        valueAnimator.start();
    }
}
