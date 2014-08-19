package com.sg.sghero.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class AnalysisChart extends View {
	private Rect rect;
	private Point p0;
	private Paint mPaint;

	public AnalysisChart(Context context) {
		super(context);
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);
	}

	public AnalysisChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
	}

	public AnalysisChart(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		rect = new Rect(0, 0, getWidth(), getHeight());
		p0 = new Point(rect.centerX(), rect.centerY());
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int count = 5;
		int[] aa = new int[]{100, 200, 300, 400, 500};
		int[] bb = new int[]{50, 100, 150, 200, 250};
		int[] cc = new int[]{25, 50, 75, 100, 125};
		int[] dd = new int[]{25, 50, 75, 100, 125};
		int[] ee = new int[]{80, 160, 240, 320, 400};
		
		
		canvas.drawLine(p0.x, p0.y, rect.centerX(), 0, mPaint);
	}
}
