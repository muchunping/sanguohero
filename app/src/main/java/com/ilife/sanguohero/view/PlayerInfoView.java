package com.ilife.sanguohero.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ilife.sanguohero.R;
import com.ilife.sanguohero.model.Player;

public class PlayerInfoView extends RelativeLayout {
	private TextView nameView;
	private TextView levelView;
	private TextView loacationView;
	private TextView experienceView;
	private TextView attackView;
	private TextView defenseView;
	private TextView accuracyView;
	private TextView dodgeView;
	private TextView speedView;

	public PlayerInfoView(Context context) {
		super(context);
		init(context);
	}

	public PlayerInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PlayerInfoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context c){
		LayoutInflater.from(c).inflate(R.layout.layout_palyerinfo, this);
	}
	
	public void setPlayer(Player player){
	}
}
