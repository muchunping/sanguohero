package cn.igame.sanguoheros.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.igame.sanguoheros.util.Logger;

/**
 * 三国全图
 * Created by Administrator on 2015/11/25.
 */
public class WorldMapView extends View {
    private final float ratio = 0.75f;
    private Paint paint = new Paint();

    private List<Path> regionList = new ArrayList<>();

    public WorldMapView(Context context) {
        super(context);
        init();
    }

    public WorldMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WorldMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = (int) (width * ratio);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Path path : regionList) {
            canvas.drawPath(path, paint);
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Logger.dL(newConfig.toString());
    }

    public void init() {
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void setRegionList() {
        if (!regionList.isEmpty())
            regionList.clear();

        Path path = new Path();
        path.moveTo(0, 0);
        path.rLineTo(100, 100);
        path.lineTo(500, 50);
        path.quadTo(150, 50, 0, 0);
        regionList.add(path);

        invalidate();
    }
}
