package semisky.com.animation;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenhongrui on 2019/3/5
 * <p>
 * 内容摘要: 没搞懂TypeEvaluator
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class ColorChangeCircle extends View {

    private Paint paint;
    private int color = Color.RED;

    public ColorChangeCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "color",
                0xff00ff00);
        objectAnimator.setEvaluator(new ColorTypeEvaluator());
        objectAnimator.start();
    }

    public void setColor(int color) {
        this.color = color;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(color);
        canvas.drawCircle(300, 300, Utils.dp2px(50), paint);
    }

    private class ColorTypeEvaluator implements TypeEvaluator<Integer> {

        private float[] startHsv = new float[3];
        private float[] endHsv = new float[3];
        private float[] outHsv = new float[3];

        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            Color.colorToHSV(startValue, startHsv);
            Color.colorToHSV(endValue, endHsv);

            // 计算当前动画完成度（fraction）所对应的颜色值
            if (endHsv[0] - startHsv[0] > 180) {
                endHsv[0] -= 360;
            } else if (endHsv[0] - startHsv[0] < -180) {
                endHsv[0] += 360;
            }
            outHsv[0] = startHsv[0] + (endHsv[0] - startHsv[0]) * fraction;
            if (outHsv[0] > 360) {
                outHsv[0] -= 360;
            } else if (outHsv[0] < 0) {
                outHsv[0] += 360;
            }
            outHsv[1] = startHsv[1] + (endHsv[1] - startHsv[1]) * fraction;
            outHsv[2] = startHsv[2] + (endHsv[2] - startHsv[2]) * fraction;

            // 计算当前动画完成度（fraction）所对应的透明度
            int alpha = startValue >> 24 + (int) ((endValue >> 24 - startValue >> 24) * fraction);

            // 把 HSV 转换回 ARGB 返回
            return Color.HSVToColor(alpha, outHsv);
        }
    }
}
