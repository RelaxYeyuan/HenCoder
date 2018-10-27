package com.yeyuan.hencoder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenhongrui on 2018/10/27
 * <p>
 * 内容摘要:
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class PicChart extends View {

    private static final int RADIUS = (int) Utils.dp2px(120);
    private static final int LENGTH = (int) Utils.dp2px(10);

    private int[] sweepAngle = {110, 90, 90, 70};
    private int[] angleColor = {Color.BLACK, Color.BLUE, Color.RED, Color.GREEN};
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    RectF bounds = new RectF();

    public PicChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int currentAngle = 0;
        for (int i = 0; i < sweepAngle.length; i++) {
            paint.setColor(angleColor[i]);
            canvas.save();
            if (i == 2) {
                //为什么饼图的扇形偏移是扇形角度的一半 currentAngle + sweepAngle[i] / 2
                //应该和三角函数有关系
                canvas.translate((float) Math.cos(Math.toRadians(currentAngle + sweepAngle[i] / 2)) * LENGTH,
                        (float) Math.sin(Math.toRadians(currentAngle + sweepAngle[i] / 2)) * LENGTH);
            }
            canvas.drawArc(bounds, currentAngle, sweepAngle[i], true, paint);
            canvas.restore();
            currentAngle += sweepAngle[i];
        }
    }
}
