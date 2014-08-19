package com.sg.sghero.view;

import com.sg.sanguohero.R;
import com.sg.sghero.model.Player;
import com.sg.sghero.widget.VerticalProgressBar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PlayerInfoView extends RelativeLayout {
	private VerticalProgressBar hpBar;
	private VerticalProgressBar mpBar;
	private TextView nameView;
	private TextView levelView;
	private TextView loacationView;
	private TextView experienceView;
	private ProgressBar experienceBar;
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
		hpBar = (VerticalProgressBar) findViewById(R.id.verticalProgressBar1);
		mpBar = (VerticalProgressBar) findViewById(R.id.verticalProgressBar2);
		nameView = (TextView) findViewById(R.id.textView1);
		levelView = (TextView) findViewById(R.id.textView2);
		loacationView = (TextView) findViewById(R.id.textView3);
		experienceView = (TextView) findViewById(R.id.textView5);
		experienceBar = (ProgressBar) findViewById(R.id.progressBar1);
		attackView = (TextView) findViewById(R.id.textView6);
		defenseView = (TextView) findViewById(R.id.textView7);
		accuracyView = (TextView) findViewById(R.id.textView8);
		dodgeView = (TextView) findViewById(R.id.textView9);
		speedView = (TextView) findViewById(R.id.textView10);
	}
	
	public void setPlayer(Player player){
		hpBar.setProgress(player.getFightTraits().HP);
		hpBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.vertical_oval_write));
		hpBar.setProgressDrawable(getResources().getDrawable(R.drawable.vertical_oval_red));
//		hpBar.setup();
		mpBar.setProgress(player.getFightTraits().MP);
		mpBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.vertical_oval_write));
		mpBar.setProgressDrawable(getResources().getDrawable(R.drawable.vertical_oval_blue));
//		mpBar.setup();
		nameView.setText(player.getName());
		levelView.setText("LV " + player.getLevel());
		loacationView.setText("当前位置 " + player.getLocation().getName());
		experienceView.setText(player.getExperienceString());
		experienceBar.setMax(Player.getRequiredExperienceForNextLevel(player.getLevel()));
		experienceBar.setProgress(player.getExperience());
		attackView.setText(player.getFightTraits().PA + "");
		defenseView.setText(player.getFightTraits().DEF + "");
		accuracyView.setText(player.getFightTraits().ACC + "");
		dodgeView.setText(player.getFightTraits().DOD + "");
		speedView.setText(player.getFightTraits().getAT() + "");
	}
}
