package semisky.com.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by chenhongrui on 2019/3/8
 *
 * 内容摘要:
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class BezierView extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = "BezierView";

    private Paint paint;
    private Path path;
    private PointF start, end, cross;
    private PathMeasure pathMeasure;
    private float scale;
    // 贝塞尔曲线中间过程点坐标
    private float[] mCurrentPosition = new float[2];

    public void setScale(float scale) {
        this.scale = scale;
    }

    {
        pathMeasure = new PathMeasure();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        start = new PointF();
        end = new PointF();
        cross = new PointF();
    }

    public BezierView(Context context) {
        super(context);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setStartPosition(PointF start, PointF end, PointF cross) {
        this.start = start;
        this.end = end;
        this.cross = cross;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void startAnim() {
        path.moveTo(start.x, start.y);
        path.quadTo(cross.x, cross.y, end.x, end.y);
        pathMeasure.setPath(path, false);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "scale",
                0f, pathMeasure.getLength());
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                pathMeasure.getPosTan(animatedValue, mCurrentPosition, null);
                setTranslationX(mCurrentPosition[0]);
                setTranslationY(mCurrentPosition[1]);
            }
        });

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animation.cancel();
                viewGroup.removeView(bezierView);
            }
        });

        objectAnimator.start();
    }

    private ViewGroup viewGroup;
    private View bezierView;

    public void setViewGroup(RelativeLayout relativeLayout, BezierView bezierView) {
        this.viewGroup = relativeLayout;
        this.bezierView = bezierView;
    }
}
