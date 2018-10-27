package com.yeyuan.hencoder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by chenhongrui on 2018/10/25
 * <p>
 * 内容摘要:
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class TestView extends View {

    private static final String TAG = "TestView";


    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
    private PathMeasure pathMeasure;

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: ");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: ");

        path.reset();//重置
        path.addRect(getWidth() / 2 - 200, getHeight() / 2 - 300, getWidth() / 2 + 200,
                getHeight() / 2, Path.Direction.CCW);
        //Path.Direction.CCW 逆时针 CW 顺时针 绘画方向
        path.addCircle(getWidth() / 2, getHeight() / 2, 200, Path.Direction.CCW);
        path.addCircle(getWidth() / 2, getHeight() / 2, 400, Path.Direction.CCW);

        pathMeasure = new PathMeasure(path, false);
        Log.d(TAG, "onSizeChanged: " + pathMeasure.getLength());//周长
    }

    {
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //镂空 Path.FillType.EVEN_ODD
        //不考虑方向。穿插奇数次则为内部，偶数次则为外部：
        path.setFillType(Path.FillType.EVEN_ODD);
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
    }
}
