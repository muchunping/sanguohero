package com.ilife.sanguohero.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import com.ilife.sanguohero.R;


public class AnimatorLayout extends FrameLayout {
	public interface OnAnimatorPost{
		public void animate(int index);
	}
	
	private List<View> cacheViews = new ArrayList<View>();
	private int cacheAmount = 2;
	private int indexOfCacheShowing = 0;
	private int currentIndex = 0;
	private BaseAdapter adapter;
	private OnAnimatorPost onAnimatorPost;

	public AnimatorLayout(Context context) {
		super(context);
	}

	public AnimatorLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AnimatorLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setAdapter(BaseAdapter adapter){
		this.adapter = adapter;
		if(adapter == null) {
			cacheViews.clear();
			return;
		}
		
		int count = adapter.getCount();
		if(count > 1) count = 2;
		for (int i = 0; i < count; i++) {
			View v = adapter.getView(i, null, this);
			cacheViews.add(v);
			addView(v);
		}
	}
	
	public void showNext(){
		if(currentIndex == adapter.getCount() - 1) return;

		currentIndex ++;
		
		View currentView = cacheViews.get(indexOfCacheShowing);
		currentView = adapter.getView(currentIndex - 1, currentView, this);
		currentView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_top));
		currentView.setVisibility(View.GONE);
		currentView.bringToFront();
		
		View nextView = cacheViews.get((indexOfCacheShowing + 1) % cacheAmount);
		nextView = adapter.getView(currentIndex, nextView, this);
		nextView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom));
		nextView.setVisibility(View.VISIBLE);
		
		if(onAnimatorPost != null)
			onAnimatorPost.animate(currentIndex);
	}
	
	public void showPrevious(){
		if(currentIndex == 0) return;

		currentIndex --;
		
		View currentView = cacheViews.get(indexOfCacheShowing);
		currentView = adapter.getView(currentIndex - 1, currentView, this);
		currentView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom));
		currentView.setVisibility(View.GONE);
		
		View preView = cacheViews.get((indexOfCacheShowing + 1) % cacheAmount);
		preView = adapter.getView(currentIndex, preView, this);
		preView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_top));
		preView.setVisibility(View.INVISIBLE);
	}
	
	public void setOnAnimatorPost(OnAnimatorPost onAnimatorPost) {
		this.onAnimatorPost = onAnimatorPost;
	}
}
