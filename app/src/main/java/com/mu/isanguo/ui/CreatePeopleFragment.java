package com.mu.isanguo.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mu.isanguo.R;
import com.mu.isanguo.SgApplication;
import com.mu.isanguo.core.SgWorld;
import com.mu.isanguo.model.player.PeoplePlayer;

/**
 * !Created by muchunping on 2017/7/10.
 */

public class CreatePeopleFragment extends Fragment {
    private TextView nameView;
    private ImageView zhanshiView, fashiView, daoshiView;

    private int type = 0;
    private boolean male = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_people, container, false);
        nameView = (TextView) view.findViewById(R.id.edit_name);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameView.getText().toString();
                PeoplePlayer people = new PeoplePlayer(name, male, type);
                SgWorld world = ((SgApplication)getActivity().getApplication()).getWorld();
                people.save();
                people.joinWorld(world);
            }
        });
        zhanshiView = (ImageView) view.findViewById(R.id.imageView0);
        fashiView = (ImageView) view.findViewById(R.id.imageView1);
        daoshiView = (ImageView) view.findViewById(R.id.imageView2);
        zhanshiView.setOnClickListener(clickListener);
        fashiView.setOnClickListener(clickListener);
        daoshiView.setOnClickListener(clickListener);
        ((RadioGroup) view.findViewById(R.id.radioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.radioButton0) {
                    male = true;
                    zhanshiView.setImageResource(R.mipmap.nan_zhan_shi);
                    fashiView.setImageResource(R.mipmap.nan_fa_shi);
                    daoshiView.setImageResource(R.mipmap.nan_dao_shi);
                } else {
                    male = false;
                    zhanshiView.setImageResource(R.mipmap.nv_zhan_shi);
                    fashiView.setImageResource(R.mipmap.nv_fa_shi);
                    daoshiView.setImageResource(R.mipmap.nv_dao_shi);
                }
            }
        });

        return view;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view == zhanshiView) {
                type = 0;
                fashiView.setSelected(false);
                daoshiView.setSelected(false);
            } else if (view == fashiView) {
                type = 1;
                zhanshiView.setSelected(false);
                daoshiView.setSelected(false);
            } else {
                type = 2;
                zhanshiView.setSelected(false);
                fashiView.setSelected(false);
            }
            view.setSelected(true);
        }
    };
}
