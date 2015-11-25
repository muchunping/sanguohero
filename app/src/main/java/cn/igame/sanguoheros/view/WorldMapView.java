package cn.igame.sanguoheros.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.igame.sanguoheros.model.City;
import cn.igame.sanguoheros.util.Logger;

/**
 * 三国全图
 * Created by Administrator on 2015/11/25.
 */
public class WorldMapView extends View {
    private Paint paint = new Paint();
    PathEffect effects = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);

    private List<Path> regionList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();

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
        int height = getMeasuredHeight();
        float ratio = 0.75f;
        if (width > height) { // 横屏
            width = (int) (height / ratio);
        } else { //竖屏
            height = (int) (width * ratio);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setARGB(0x33, 0xFF, 0x00, 0x00);
        paint.setPathEffect(effects);
        for (Path path : regionList) {
            canvas.drawPath(path, paint);
        }

        paint.setPathEffect(null);
        paint.setTextSize(16);
        for (City city : cityList) {
            paint.setARGB(0xFF, 0xFF, 0xFF, 0x00);
            canvas.drawCircle(city.getLocation().x, city.getLocation().y, 4, paint);
            float textWidth = paint.measureText(city.getName());
            paint.setARGB(0xFF, 0xFF, 0x00, 0xFF);
            canvas.drawText(city.getName(), city.getLocation().x - textWidth / 2, city.getLocation().y - (4 + 5), paint);
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

        final float ratioH = getMeasuredWidth() / 100f;
        final float ratioV = getMeasuredHeight() / 100f;
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(40 * ratioH, 0);
        path.lineTo(45 * ratioH, 25 * ratioV);
        path.lineTo(35 * ratioH, 26 * ratioV);
        path.lineTo(36 * ratioH, 45 * ratioV);
        path.lineTo(0, 20 * ratioV);
        path.close();
        regionList.add(path);

        path = new Path();
        path.moveTo(40 * ratioH, 0);
        path.lineTo(80 * ratioH, 0);
        path.lineTo(75 * ratioH, 22 * ratioV);
        path.lineTo(45 * ratioH, 25 * ratioV);
        path.close();
        regionList.add(path);

        path = new Path();
        path.moveTo(80 * ratioH, 0);
        path.lineTo(100 * ratioH, 0);
        path.lineTo(100 * ratioH, 30 * ratioV);
        path.lineTo(90 * ratioH, 18 * ratioV);
        path.lineTo(75 * ratioH, 22 * ratioV);
        path.close();
        regionList.add(path);

        path = new Path();
        path.moveTo(35 * ratioH, 26 * ratioV);
        path.lineTo(45 * ratioH, 25 * ratioV);
        path.lineTo(65 * ratioH, 23 * ratioV);
        path.lineTo(55 * ratioH, 50 * ratioV);
        path.lineTo(36 * ratioH, 45 * ratioV);
        path.close();
        regionList.add(path);

        path = new Path();
        path.moveTo(65 * ratioH, 23 * ratioV);
        path.lineTo(75 * ratioH, 22 * ratioV);
        path.lineTo(90 * ratioH, 18 * ratioV);
        path.lineTo(100 * ratioH, 30 * ratioV);
        path.lineTo(100 * ratioH, 55 * ratioV);
        path.lineTo(55 * ratioH, 50 * ratioV);
        path.close();
        regionList.add(path);

        path = new Path();
        path.moveTo(55 * ratioH, 50 * ratioV);
        path.lineTo(100 * ratioH, 55 * ratioV);
        path.lineTo(100 * ratioH, 100 * ratioV);
        path.lineTo(80 * ratioH, 100 * ratioV);
        path.lineTo(65 * ratioH, 65 * ratioV);
        path.close();
        regionList.add(path);

        path = new Path();
        path.moveTo(30 * ratioH, 57 * ratioV);
        path.lineTo(36 * ratioH, 45 * ratioV);
        path.lineTo(55 * ratioH, 50 * ratioV);
        path.lineTo(65 * ratioH, 65 * ratioV);
        path.lineTo(40 * ratioH, 78 * ratioV);
        path.close();
        regionList.add(path);

        path = new Path();
        path.moveTo(40 * ratioH, 78 * ratioV);
        path.lineTo(65 * ratioH, 65 * ratioV);
        path.lineTo(80 * ratioH, 100 * ratioV);
        path.lineTo(30 * ratioH, 100 * ratioV);
        path.close();
        regionList.add(path);

        path = new Path();
        path.moveTo(0 * ratioH, 20 * ratioV);
        path.lineTo(36 * ratioH, 45 * ratioV);
        path.lineTo(30 * ratioH, 57 * ratioV);
        path.lineTo(40 * ratioH, 78 * ratioV);
        path.lineTo(30 * ratioH, 100 * ratioV);
        path.lineTo(0 * ratioH, 100 * ratioV);
        path.close();
        regionList.add(path);

        City city = new City("武威", (int) (31 * ratioH), (int) (19 * ratioV));
        cityList.add(city);
        city = new City("长安", (int) (50 * ratioH), (int) (34 * ratioV));
        cityList.add(city);
        city = new City("洛阳", (int) (60 * ratioH), (int) (35 * ratioV));
        cityList.add(city);
        city = new City("襄平", (int) (92 * ratioH), (int) (4 * ratioV));
        cityList.add(city);

        invalidate();
    }
}
