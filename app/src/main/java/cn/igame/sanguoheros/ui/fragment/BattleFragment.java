package cn.igame.sanguoheros.ui.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.model.Actor;
import cn.igame.sanguoheros.view.BattleView;

/**
 * 战斗场景
 * Created by Mouchunping018 on 2016/9/16.
 */
public class BattleFragment extends DialogFragment {
    private BattleView battleView;
    private Actor[] attacker;
    private Actor[] defender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.custom_dialog_style);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_battle, container, false);
        battleView = (BattleView) view.findViewById(R.id.battleView);
        battleView.setAttacker(attacker);
        battleView.setDefender(defender);
        return view;
    }

    public void setAttacker(Actor... actors){
        this.attacker = actors;
    }

    public void setDefender(Actor... actors){
        this.defender = actors;
    }
}
