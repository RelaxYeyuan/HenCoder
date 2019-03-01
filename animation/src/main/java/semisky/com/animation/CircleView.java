package semisky.com.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenhongrui on 2018/10/26
 * <p>
 * 内容摘要:
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class CircleView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float radius = Utils.dp2px(50);

    public float getRadius() {
        return radius;
    }

    //会通过反射设置值
    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();//设置标记，通知Android刷新这个view
        // 16ms 会去刷新  "1000/60帧"？
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, getRadius(), paint);
    }
}
