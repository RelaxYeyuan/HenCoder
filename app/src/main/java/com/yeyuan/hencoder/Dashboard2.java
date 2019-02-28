package com.yeyuan.hencoder;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by chenhongrui on 2018/10/24
 * <p>
 * 内容摘要: 绘画仪表盘
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class Dashboard2 extends View {

    private static final String TAG = "Dashboard2";

    private Paint mAcePaint, mPointerPaint, mScalePaint;

    private float mRADIUS = Utils.dp2px(150);
    private float mPointerLength = Utils.dp2px(120);
    private float ANGLE = 120;
    private PathEffect mPathEffect;
    private Path mPath = new Path();
    private Path mArcPath = new Path();
    private PathMeasure mPathMeasure;

    //刻度个数
    private float markNumber;
    //刻度间距
    private int markSpace = 10;
    //刻度宽度
    private int markWidth = 5;
    //刻度高度
    private int markHeight = 12;

    private float pointerWaggleAngle = 150;

    public Dashboard2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        mAcePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath.addRect(0, 0, Utils.dp2px(markWidth), Utils.dp2px(markHeight), Path.Direction.CW);

        mArcPath.addArc(-mRADIUS, -mRADIUS, mRADIUS, mRADIUS,
                90 + ANGLE / 2f, 360 - ANGLE);
        mPathMeasure = new PathMeasure(mArcPath, false);

        markNumber = (mPathMeasure.getLength() - Utils.dp2px(markWidth)) / markSpace;
        Log.d(TAG, "instance initializer: " + markNumber);

        mPathEffect = new PathDashPathEffect(mPath, markNumber, 0, PathDashPathEffect.Style.MORPH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mAcePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, 10, mAcePaint);

        mPointerPaint.setStrokeWidth(10);
        canvas.drawLine(getWidth() / 2f, getHeight() / 2f,
                (float) Math.cos(Math.toRadians(pointerWaggleAngle)) * mPointerLength + getWidth() / 2f,
                (float) Math.sin(Math.toRadians(pointerWaggleAngle)) * mPointerLength + getHeight() / 2f,
                mPointerPaint);

        mAcePaint.setStyle(Paint.Style.STROKE);
        mAcePaint.setStrokeWidth(12);
        canvas.drawArc(getWidth() / 2f - mRADIUS, getHeight() / 2f - mRADIUS,
                getWidth() / 2f + mRADIUS, getHeight() / 2f + mRADIUS
                , 90 + ANGLE / 2f, 360 - ANGLE, false, mAcePaint);

        mScalePaint.setPathEffect(mPathEffect);
        mScalePaint.setStrokeWidth(12);
        canvas.drawArc(getWidth() / 2f - mRADIUS, getHeight() / 2f - mRADIUS,
                getWidth() / 2f + mRADIUS, getHeight() / 2f + mRADIUS
                , 90 + ANGLE / 2f, 360 - ANGLE, false, mScalePaint);

    }

    public void startAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "pointerWaggle", 150, 390);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new FastOutSlowInInterpolator());
        objectAnimator.start();
    }

    private void setPointerWaggle(float angle) {
        this.pointerWaggleAngle = angle;
        postInvalidate();
    }

    /**
     * 获取刻度的度数
     *
     * @param mark 刻度
     * @return 度数
     */
    private double getAngleFromMark(int mark) {
        float spaceAngle = (360 - ANGLE) / markSpace;
        float markAngle = ((90 + ANGLE / 2f) + (spaceAngle * mark));
        Log.d(TAG, "getAngleFromMark: " + markAngle);
        return markAngle;
    }
}
