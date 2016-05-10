package cn.igame.sanguoheros.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import cn.igame.sanguoheros.R;
import cn.igame.sanguoheros.util.Logger;

/**
 * 战斗界面
 * Created by MOUCHUNPING018 on 2016/2/4.
 */
public class BattleView extends View {
    private static final int COLUMN_NUMBER = 3;
    private static final int ROW_NUMBER = 3;

    private Paint mPaint = new Paint();
    private Paint mPaint2 = new Paint();
    private int roundSize;
    private Bitmap bitmap;

    private RectF[] allActorPosition = new RectF[COLUMN_NUMBER * ROW_NUMBER * 2];

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
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int validWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int validHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int gapWidth = (validWidth - roundSize * COLUMN_NUMBER) / (COLUMN_NUMBER + 1);
        int gapHeight = (int) ((validHeight * 0.4 - roundSize * ROW_NUMBER) / (COLUMN_NUMBER + 1));
        measureActorPosition(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + validWidth, getPaddingTop() + validHeight, gapWidth, gapHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAllActorPosition(canvas);
    }

    private void measureActorPosition(int left, int top, int right, int bottom, int gapWidth, int gapHeight) {
        //measure above
        for (int i = 0; i < ROW_NUMBER; i++) {
            int topAbove = top + gapHeight + i * (roundSize + gapHeight);
            for (int j = 0; j < COLUMN_NUMBER; j++) {
                int leftAbove = left + gapWidth + j * (roundSize + gapWidth);
                allActorPosition[i * COLUMN_NUMBER + j] = new RectF(leftAbove, topAbove, leftAbove + roundSize, topAbove + roundSize);
                Logger.dL(allActorPosition[i * COLUMN_NUMBER + j].toString());
            }
        }

        for (int i = ROW_NUMBER - 1; i >= 0; i--) { //2,1,0
            int bottomBelow = bottom - gapHeight - i * (roundSize + gapHeight);
            for (int j = COLUMN_NUMBER - 1; j >= 0; j--) {
                int rightBelow = right - gapWidth - j * (roundSize + gapWidth);
                int index = (2 - i) * COLUMN_NUMBER + 2 - j + COLUMN_NUMBER * ROW_NUMBER;
                allActorPosition[index] = new RectF(rightBelow - roundSize, bottomBelow - roundSize, rightBelow, bottomBelow);
                Logger.dL(allActorPosition[index].toShortString());
            }
        }
    }

    private void drawAllActorPosition(Canvas canvas) {
        for (int i = 0; i < allActorPosition.length; i++) {
            RectF rect = allActorPosition[i];
            if (rect == null)
                continue;
            RectF rectCopy = new RectF(rect.left, rect.top + rect.height() * 0.7f, rect.right, rect.bottom);
            canvas.drawOval(rectCopy, mPaint);
            canvas.drawBitmap(bitmap, null, rect, mPaint);
            canvas.drawText(String.valueOf(i), rect.centerX(), rect.centerY(), mPaint);
        }
    }
}
