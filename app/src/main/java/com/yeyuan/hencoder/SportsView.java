package com.yeyuan.hencoder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2018/10/28
 * <p>
 * 内容摘要：要点1.想让文字纵向居中的两种方法
 * 2.左对齐 getTextBounds获取left计算
 * 3.换行
 * 版权所有: YEYUAN
 * 修改内容：
 * 修改日期
 */
public class SportsView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float RING_WIDTH = Utils.dp2px(10);
    private static final int RADIUS = (int) Utils.dp2px(150);
    private Rect rect = new Rect();
    private Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //圆环
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, paint);

        //进度条
        paint.setColor(Color.YELLOW);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS
                , -90, 120, false, paint);

        paint.setTextSize(Utils.dp2px(100));
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        //如果需要文字纵向居中
        //方法一：算出需要偏移的值 运用于文字固定的情况
        paint.getTextBounds("abjg", 0, "abjg".length(), rect);
        //不需要考虑正负，直接算平均值即可
        int offset = (rect.top + rect.bottom) / 2;
//        canvas.drawText("abjg", getWidth() / 2f, getHeight() / 2f - offset, paint);
        //方法二：fontMetrics 运用于文字变化的情况
        paint.getFontMetrics(fontMetrics);
        float fontOffset = (fontMetrics.ascent + fontMetrics.descent) / 2;
        canvas.drawText("abjg", getWidth() / 2f, getHeight() / 2f - fontOffset, paint);


        //如果需要文字左对齐
        //getTextBounds获取left计算
        paint.setTextSize(Utils.dp2px(100));
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds("aaaa", 0, "aaaa".length(), rect);
        canvas.drawText("aaaa", -rect.left, 200, paint);
    }
}
