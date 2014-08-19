package com.sg.sghero.widget;

import com.sg.sghero.util.Range;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

/**
 * 当设置了背景图片和进度图片以及初始进度后，必须调用{@link #setup()}使之生效。
 */
public class VerticalProgressBar extends View {
	private Drawable backgroundDrawable;
	private ClipDrawable progressDrawable;
	private Range progress;
	private Paint paint;

	public VerticalProgressBar(Context context) {
		super(context);
		init();
	}

	public VerticalProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public VerticalProgressBar(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setTextSize(16);
		progress = new Range(1000, 1000);
	}

	public Drawable getBackgroundDrawable() {
		return backgroundDrawable;
	}

	public void setBackgroundDrawable(Drawable backgroundDrawable) {
		this.backgroundDrawable = backgroundDrawable;
	}

	public Drawable getProgressDrawable() {
		return progressDrawable;
	}

	public void setProgressDrawable(Drawable progressDrawable) {
		this.progressDrawable = new ClipDrawable(progressDrawable, Gravity.BOTTOM, 
				ClipDrawable.VERTICAL);
	}

	public Range getProgress() {
		return progress;
	}

	public void setProgress(Range progress) {
		this.progress = progress;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getPaddingLeft() + getPaddingRight();
		int height = MeasureSpec.getSize(heightMeasureSpec);
		
		String text = progress.toString();
		int textWidth = (int) paint.measureText(text);
		if(backgroundDrawable != null){
			final int dw = backgroundDrawable.getIntrinsicWidth();
			width += Math.max(textWidth, dw);
		}else{
			width += textWidth;
		}
		
		setMeasuredDimension(width, height);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int width = getWidth() - getPaddingLeft() - getPaddingRight();
		int height = getHeight() - getPaddingTop() - getPaddingBottom();
		
		if(backgroundDrawable != null){
			int dw = backgroundDrawable.getIntrinsicWidth();
			int left = getPaddingLeft() + (width - dw) / 2;
			int top = getPaddingTop();
			int right = left + dw;
			int bottom = height + getPaddingTop();
			backgroundDrawable.setBounds(left, top, right, bottom);
			backgroundDrawable.draw(canvas);
		}
		
		if(progressDrawable != null){
			int dw = progressDrawable.getIntrinsicWidth();
			int left = getPaddingLeft() + (width - dw) / 2;
			int top = getPaddingTop();
			int right = left + dw;
			int bottom = height + getPaddingTop();
			progressDrawable.setBounds(left, top, right, bottom);
			progressDrawable.setLevel((int)(10000 * progress.percentage()));
			progressDrawable.draw(canvas);
		}
		
		int textWidth = (int) paint.measureText(progress.toString());
		int x = (width - textWidth) / 2 + getPaddingLeft();
		int y = height / 2 + getPaddingTop() - (int)(paint.getTextSize() / 2);
		canvas.drawText(progress.toString(), x, y, paint);
	}
}
