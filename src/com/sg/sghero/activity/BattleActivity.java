package com.sg.sghero.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.sg.sanguohero.R;

public class BattleActivity extends Activity{
//	private Actor actorA, actorB;
	private ImageView imageA, imageB;
	private ImageView animView;
	private Animation largerAnim, smallerAnim;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		actorA = getIntent().getParcelableExtra("actor1");
//		actorB = getIntent().getParcelableExtra("actor2");
		largerAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_big);
		smallerAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_small);
		largerAnim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
				
			}
			@Override
			public void onAnimationRepeat(Animation arg0) {
				
			}
			@Override
			public void onAnimationEnd(Animation arg0) {
//				animView.startAnimation(smallerAnim);
			}
		});
		smallerAnim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
				
			}
			@Override
			public void onAnimationRepeat(Animation arg0) {
				
			}
			@Override
			public void onAnimationEnd(Animation arg0) {
//				animView.setVisibility(View.GONE);
			}
		});
		
		setContentView(R.layout.activity_battle);
		imageA = (ImageView) findViewById(R.id.imageView1);
		imageB = (ImageView) findViewById(R.id.imageView2);
		animView = (ImageView) findViewById(R.id.floatImageView);
	}
	
	public void attackAnimation(final View v){
		Rect startRect = new Rect();
		v.getGlobalVisibleRect(startRect);
		Log.i("startRect.toString()", startRect.toString());
		Rect finalRect = new Rect();
		finalRect.left = startRect.left - (int)(startRect.width() * 0.1);
		finalRect.right = startRect.right + (int)(startRect.width() * 0.1);
		finalRect.top = startRect.top - (int)(startRect.width() * 0.2);
		finalRect.bottom = startRect.bottom;
		Log.i("finalRect.toString()", finalRect.toString());
		View parentView = (View) animView.getParent();
		Rect parentRect = new Rect();
		parentView.getGlobalVisibleRect(parentRect);

		animView.setX(startRect.left);
		animView.setY(startRect.top - parentRect.top);
		animView.setVisibility(View.VISIBLE);
//		TranslateAnimation moveAnim = new TranslateAnimation(
//				Animation.ABSOLUTE, startRect.left,
//				Animation.ABSOLUTE, startRect.left,
//				Animation.ABSOLUTE, startRect.top - parentRect.top,
//				Animation.ABSOLUTE, startRect.top - parentRect.top);
		ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, 0.5f, 1.2f);
		AnimationSet set = new AnimationSet(false);
//		set.setFillEnabled(true);
//		set.setFillAfter(true);
		scaleAnim.setRepeatCount(1);
		scaleAnim.setRepeatMode(Animation.REVERSE);
//		moveAnim.setRepeatCount(1);
//		moveAnim.setRepeatMode(Animation.REVERSE);
		set.setDuration(300);
//		set.addAnimation(moveAnim);
		set.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.GONE);
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.VISIBLE);
				animView.setVisibility(View.GONE);
			}
		});
		set.addAnimation(scaleAnim);
		animView.setVisibility(View.VISIBLE);
		animView.setImageDrawable(((ImageView)v).getDrawable());
		animView.startAnimation(set);
	}
}
