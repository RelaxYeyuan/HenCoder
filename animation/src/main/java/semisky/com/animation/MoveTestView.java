package semisky.com.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by chenhongrui on 2019/3/4
 * <p>
 * 内容摘要:
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class MoveTestView extends View {
    private Paint paint;

    public MoveTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    float lastX = 0, lastY = 0;

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float rawX = event.getRawX();
        float rawY = event.getRawY();

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = rawX;
                lastY = rawY;
                break;

            case MotionEvent.ACTION_MOVE:
                int offsetX = (int) (rawX - lastX);
                int offsetY = (int) (rawY - lastY);

                offsetLeftAndRight(offsetX);
                offsetTopAndBottom(offsetY);

                lastX = rawX;
                lastY = rawY;
                break;

            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(100, 100, 500, 500, paint);
    }
}
