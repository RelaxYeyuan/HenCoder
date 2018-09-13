package com.yeyuan.hencoder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2018/9/3
 * <p>
 * 内容摘要：${TODO}
 * 版权所有: YEYUAN
 * 修改内容：
 * 修改日期
 * 在 Android 里，每个 View 都有一个自己的坐标系，彼此之间是不影响的。这个坐标系的原点是 View
 * 左上角的那个点；水平方向是 x 轴，右正左负；竖直方向是 y 轴，下正上负
 * （注意，是下正上负，不是上正下负，和上学时候学的坐标系方向不一样）
 */
public class CustomDraw extends View {

    private Paint paint;
    private Paint redPaint;

    public CustomDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setTextSize(28);

        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(12);
        redPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.CYAN);

        //drawText是以文字的左下角开始
        canvas.drawText("坐标系的原点是 View 左上角的那个点", 100, 100, paint);
        canvas.drawPoint(100, 100, redPaint);

        //drawCircle 是以圆心开始
        canvas.drawCircle(200, 200, 100, paint);
        canvas.drawPoint(200, 200, redPaint);

        //drawRect以左上角和右下角 为开始和结束
        canvas.drawRect(350, 200, 550, 300, paint);
        canvas.drawPoint(350, 200, redPaint);
        canvas.drawPoint(550, 300, redPaint);

        canvas.drawLine(200, 350, 400, 400, paint);
        canvas.drawPoint(200, 350, redPaint);
        canvas.drawPoint(400, 400, redPaint);

        canvas.drawRoundRect(100, 450, 400, 600, 50, 50, paint);
        canvas.drawPoint(100, 450, redPaint);
        canvas.drawPoint(400, 600, redPaint);
    }
}
