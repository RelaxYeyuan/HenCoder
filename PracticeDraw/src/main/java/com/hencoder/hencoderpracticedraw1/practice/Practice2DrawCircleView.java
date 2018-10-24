package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice2DrawCircleView extends View {
    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint4 = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice2DrawCircleView(Context context) {
        super(context);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice2DrawCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawCircle() 方法画圆
//        一共四个圆：1.实心圆 2.空心圆 3.蓝色实心圆 4.线宽为 20 的空心圆

        //1.实心圆
        canvas.drawCircle(200, 120, 100, paint1);
        //2.空心圆
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(2);
        canvas.drawCircle(500, 120, 100, paint2);
        //3.蓝色实心圆
        paint3.setColor(Color.BLUE);
        canvas.drawCircle(200, 340, 100, paint3);
        //4.线宽为 20 的空心圆
        paint4.setStyle(Paint.Style.STROKE);
        paint4.setStrokeWidth(20);
        canvas.drawCircle(500, 340, 100, paint4);
    }
}
