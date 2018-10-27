package com.yeyuan.hencoder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by chenhongrui on 2018/10/24
 * <p>
 * 内容摘要:
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class Dashboard extends View {

    private static final String TAG = "Dashboard";

    private static final int ANGLE = 120;
    private static final int RADIUS = (int) Utils.dp2px(150);
    private static final int LENGTH = (int) Utils.dp2px(100);

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
    private PathMeasure pathMeasure;
    private PathDashPathEffect pathDashPathEffect;

    public Dashboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(6));

        path.addRect(0, 0, Utils.dp2px(5), Utils.dp2px(12), Path.Direction.CW);

        Path arc = new Path();
        arc.addArc(-RADIUS, -RADIUS, RADIUS, RADIUS,
                90 + ANGLE / 2, 360 - ANGLE);
        pathMeasure = new PathMeasure(arc, false);

        pathDashPathEffect = new PathDashPathEffect(path, (pathMeasure.getLength() -
                Utils.dp2px(5)) / 15, 0, PathDashPathEffect.Style.ROTATE);

        Log.d(TAG, "instance initializer: " + getWidth());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: " + getWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS,
                90 + ANGLE / 2, 360 - ANGLE, false, paint);

        paint.setPathEffect(pathDashPathEffect);

        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS,
                90 + ANGLE / 2, 360 - ANGLE, false, paint);

        paint.setPathEffect(null);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 6, paint);

        Log.d(TAG, "onDraw: " + getWidth());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getWidth();
        Log.d(TAG, "onMeasure: " + width);
    }
}
