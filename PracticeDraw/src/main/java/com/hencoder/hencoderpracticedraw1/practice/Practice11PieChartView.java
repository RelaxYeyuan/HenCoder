package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice11PieChartView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paint5 = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice11PieChartView(Context context) {
        super(context);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图


        paint.setColor(Color.RED);
        canvas.drawArc(100, 50, 450, 400, -180, 120, true, paint);

        paint1.setColor(Color.YELLOW);
        canvas.drawArc(130, 70, 450, 400, -60, 60, true, paint1);

        paint2.setColor(Color.MAGENTA);
        canvas.drawArc(130, 80, 450, 400, 0, 10, true, paint2);

        paint3.setColor(Color.LTGRAY);
        canvas.drawArc(130, 85, 450, 400, 10, 10, true, paint3);

        paint4.setColor(Color.GREEN);
        canvas.drawArc(130, 90, 450, 400, 10, 60, true, paint4);

        paint5.setColor(Color.BLUE);
        canvas.drawArc(100, 90, 470, 400, 75, 100, true, paint5);
    }
}
