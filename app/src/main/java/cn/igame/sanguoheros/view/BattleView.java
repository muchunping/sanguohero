package cn.igame.sanguoheros.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 战斗界面
 * Created by MOUCHUNPING018 on 2016/2/4.
 */
public class BattleView extends View{
    private Paint mPaint = new Paint();
    private Paint mPaint2 = new Paint();


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

    private void init(){
        mPaint2.setColor(0xFF00FFCC);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(new RectF(0, 100, 100, 130), mPaint);
        canvas.drawOval(new RectF(20, 30, 80, 120), mPaint2);
    }
}
