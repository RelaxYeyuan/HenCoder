package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图


        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        canvas.drawLine(70, 400, 600, 400, paint);//横坐标直线
        canvas.drawLine(70, 400, 70, 100, paint);//纵坐标直线

        paint1.setTextSize(30);
        paint1.setColor(Color.WHITE);
        canvas.drawText("直方图", 300, 470, paint1);

        paint3.setColor(Color.WHITE);
        paint3.setTextSize(20);
        paint2.setColor(Color.GREEN);

        canvas.drawText("Froyo", 90, 420, paint3);
        canvas.drawRect(90, 400, 160, 395, paint2);

        canvas.drawText("GB", 190, 420, paint3);
        canvas.drawRect(170, 400, 240, 385, paint2);

        canvas.drawText("IC S", 270, 420, paint3);
        canvas.drawRect(250, 400, 320, 385, paint2);

        canvas.drawText("JB", 350, 420, paint3);
        canvas.drawRect(330, 400, 400, 260, paint2);

        canvas.drawText("KitKat", 420, 420, paint3);
        canvas.drawRect(410, 400, 480, 200, paint2);

        canvas.drawText("L", 520, 420, paint3);
        canvas.drawRect(490, 400, 560, 100, paint2);

        canvas.drawText("M", 600, 420, paint3);
        canvas.drawRect(570, 400, 640, 265, paint2);
    }
}
