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

    /**
     * 刻度个数
     */
    private float markNumber;

    /**
     * 刻度间距
     */
    private int markSpace = 15;

    /**
     * 刻度宽度
     */
    private int markWidth = 5;

    public Dashboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(6));

        path.addRect(0, 0, Utils.dp2px(markWidth), Utils.dp2px(12), Path.Direction.CW);

        Path arc = new Path();
        arc.addArc(-RADIUS, -RADIUS, RADIUS, RADIUS,
                90 + ANGLE / 2f, 360 - ANGLE);
        pathMeasure = new PathMeasure(arc, false);

        //需要减去刻度的宽度
        markNumber = (pathMeasure.getLength() - Utils.dp2px(markWidth)) / markSpace;

        pathDashPathEffect = new PathDashPathEffect(path, markNumber,
                0, PathDashPathEffect.Style.ROTATE);

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

        //画弧线
        canvas.drawArc(getWidth() / 2f - RADIUS, getHeight() / 2f - RADIUS,
                getWidth() / 2f + RADIUS, getHeight() / 2f + RADIUS,
                90 + ANGLE / 2f, 360 - ANGLE, false, paint);

        //画刻度
        paint.setPathEffect(pathDashPathEffect);

        canvas.drawArc(getWidth() / 2f - RADIUS, getHeight() / 2f - RADIUS,
                getWidth() / 2f + RADIUS, getHeight() / 2f + RADIUS,
                90 + ANGLE / 2f, 360 - ANGLE, false, paint);

        paint.setPathEffect(null);

        //画圆心
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, 6, paint);

        //画指针 获取指针终点的绝对位置
        canvas.drawLine(getWidth() / 2f, getHeight() / 2f,
                (float) Math.cos(Math.toRadians(getAngleFromMark(5))) * LENGTH + getWidth() / 2f,
                (float) Math.sin(Math.toRadians(getAngleFromMark(5))) * LENGTH + getHeight() / 2f,
                paint);

        Log.d(TAG, "onDraw: " + getWidth() / 2f);
    }

    /**
     * 获取刻度的对应的角度
     */
    private int getAngleFromMark(int mark) {
        float markAngle = (360 - ANGLE) / markSpace;
        return (int) (90 + (float) ANGLE / 2f + markAngle * mark);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getWidth();
        Log.d(TAG, "onMeasure: " + width);
    }
}
