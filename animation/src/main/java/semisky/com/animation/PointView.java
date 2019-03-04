package semisky.com.animation;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenhongrui on 2019/3/1
 * <p>
 * 内容摘要: 自定义Evaluator
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class PointView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Point point = new Point(0, 0);

    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStrokeWidth(Utils.dp2px(15));
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
        invalidate();
    }

    public void startAnim() {
        Point newPoint = new Point((int) Utils.dp2px(300), (int) Utils.dp2px(300));
        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(this, "point",
                new PointEvaluator(), newPoint);

        objectAnimator.setStartDelay(1000);
        objectAnimator.setDuration(5000);
        objectAnimator.start();
    }

    class PointEvaluator implements TypeEvaluator<Point> {
        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            // (1, 1)   (5, 5)     fraction: 0.2   x: 1 + (5 - 1) * 0.2 y: 1 + (5 - 1) * 0.2
            float x = startValue.x + (endValue.x - startValue.x) * fraction;
            float y = startValue.y + (endValue.y - startValue.y) * fraction;
            return new Point((int) x, (int) y);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPoint(point.x, point.y, paint);
    }
}
