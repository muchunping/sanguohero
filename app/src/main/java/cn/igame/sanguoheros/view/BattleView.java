package cn.igame.sanguoheros.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import cn.igame.sanguoheros.util.Logger;

/**
 * 战斗界面
 * Created by MOUCHUNPING018 on 2016/2/4.
 */
public class BattleView extends View {
    public static final int MAX_NUMBER_BATTLE_ACTOR = 9;
    private static final int COLUMN_NUMBER = 3;
    private static final int ROW_NUMBER = 3;

    private Paint mPaint = new Paint();
    private Paint mPaint2 = new Paint();
    private int roundSize;

    private RectF[] allActorPosition = new RectF[MAX_NUMBER_BATTLE_ACTOR * 2];

    public BattleView(Context context) {
        super(context);
        init();
    }

    public BattleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BattleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint2.setColor(0xFF00FFCC);
        roundSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int validWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int validHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int gapWidth = (validWidth - roundSize * COLUMN_NUMBER) / (COLUMN_NUMBER + 1);
        int gapHeight = (int) ((validHeight * 0.8 - roundSize * ROW_NUMBER) / (COLUMN_NUMBER + 1));
        measureActorPosition(getPaddingLeft(), getPaddingTop(), getRight() - getPaddingRight(), getBottom() - getPaddingBottom(), gapWidth, gapHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAllActorPosition(canvas);
    }

    private void measureActorPosition(int left, int top, int right, int bottom, int gapWidth, int gapHeight) {
        //measure above
        int leftAbove = left + gapWidth;
        int topAbove = top + gapHeight;
        for (int i = 0; i < ROW_NUMBER; i++) {
            topAbove += i * (roundSize + gapHeight);
            for (int j = 0; j < COLUMN_NUMBER; j++) {
                leftAbove += j * (roundSize + gapWidth);
                allActorPosition[i * COLUMN_NUMBER + j] = new RectF(leftAbove, topAbove, left + roundSize, topAbove + roundSize);
                Logger.dL(allActorPosition[i * COLUMN_NUMBER + j].toString());
            }
        }

        int rightBelow = right - gapWidth;
        int bottomBelow = bottom - gapHeight;
        for (int i = ROW_NUMBER - 1; i >= 0; i--) { //2,1,0
            bottomBelow -= i * (roundSize + gapHeight);
            for (int j = COLUMN_NUMBER; j >= 0; j--) {
                rightBelow -= j * (roundSize + gapWidth);
                allActorPosition[i * COLUMN_NUMBER + j + 9] = new RectF(rightBelow - roundSize, bottomBelow - roundSize, rightBelow, bottomBelow);
                Logger.dL(allActorPosition[i * COLUMN_NUMBER + j].toShortString());
            }
        }
    }

    private void drawAllActorPosition(Canvas canvas) {
        for (RectF rect : allActorPosition) {
            if (rect == null)
                continue;
            canvas.drawOval(rect, mPaint);
            canvas.drawOval(rect, mPaint2);
        }
    }
}
