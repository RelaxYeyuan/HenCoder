package semisky.com.scalableimageview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

/**
 * Created by chenhongrui on 2018/11/8
 * <p>
 * 内容摘要:
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class ScalableImageView extends View {

    private static final String TAG = "ScalableImageView";

    private Bitmap bitmap;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final int PIC_WIDTH = (int) Utils.dp2px(300);
    //大图放大比例
    private static final float OVER_SCALE_FACTOR = 1.5f;

    //手指拖动显示X坐标
    float offsetX;
    //手指拖动显示Y坐标
    float offsetY;

    //默认原点为中心点
    float originalOffsetX;
    float originalOffsetY;

    //当前是大图显示还是小图显示
    boolean isBigScale;
    //小图比例
    float smallScale;
    //大图比例
    float bigScale;

    //动画 从0~1
//    float scaleFraction;
    float currentScale;

    GestureDetectorCompat detector;
    ObjectAnimator objectAnimator;
    OverScroller scroller;
    //手势监听器
    MyGestureDetector myGestureDetector;
    MyRunnable myRunnable;
    //缩放手势监听器
    ScaleGestureDetector scaleDetector;
    MyScaleListener myScaleListener;

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        myRunnable = new MyRunnable();
        myGestureDetector = new MyGestureDetector();
        bitmap = Utils.getAvatar(getResources(), PIC_WIDTH);
        detector = new GestureDetectorCompat(getContext(), myGestureDetector);
        detector.setOnDoubleTapListener(myGestureDetector);
        scroller = new OverScroller(getContext());
        myScaleListener = new MyScaleListener();
        scaleDetector = new ScaleGestureDetector(getContext(), myScaleListener);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originalOffsetX = (getWidth() - bitmap.getWidth()) / 2f;
        originalOffsetY = (getHeight() - bitmap.getHeight()) / 2f;

        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;
        }

        currentScale = smallScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //放大到大图后，手动拖动移动
        float scaleFraction = (currentScale - smallScale) / (bigScale - smallScale);
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);

        //计算出当前图片尺寸缩放进度 0 - 1
//        float scale = smallScale + (bigScale - smallScale) * scaleFraction;

        //大小尺寸缩放
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);

        //默认居中显示
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }

    private float getCurrentScale() {
        return currentScale;
    }

    private void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    private ObjectAnimator getObjectAnimator() {
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0, 1);
        }

        objectAnimator.setFloatValues(smallScale, bigScale);

        return objectAnimator;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = scaleDetector.onTouchEvent(event);
        //判断当前在滑动中是否发生缩放动作，否则就按滑动处理
        if (!scaleDetector.isInProgress()) {
            result = detector.onTouchEvent(event);
        }
        return result;
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent down, MotionEvent event, float distanceX, float distanceY) {
            //distanceX 默认为0 已手指最初点下的位置，向右划为负，向左划为正 (旧位置减去新位置！！)
            //手指拖动回调
            if (isBigScale) {
                //图片和手势一个方向移动 负负得正
                offsetX -= distanceX;
                offsetY -= distanceY;
                fixOffset();
                invalidate();
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent down, MotionEvent event, float velocityX, float velocityY) {
            //快速一划
            if (isBigScale) {
                //起点 & 速度
                scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        //类似于边界判断
                        -(int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        -(int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                        (int) (bitmap.getHeight() * bigScale - getHeight()) / 2);

                //快速一划动画
                postOnAnimation(myRunnable);
            }
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            //双击回调
            isBigScale = !isBigScale;
            if (isBigScale) {
                //以点击的地方放大
                //点击起点的位置 - 放大之前的位置
                offsetX = (event.getX() - getWidth() / 2f) - (event.getX() - getWidth() / 2f) * bigScale / smallScale;
                offsetY = (event.getY() - getHeight() / 2f) - (event.getY() - getHeight() / 2f) * bigScale / smallScale;
                Log.d(TAG, "onScale:offsetX " + offsetX);
                Log.d(TAG, "onScale:offsetX " + offsetY);

                //防止超出最大值
                fixOffset();
                //做悬浮动画
                getObjectAnimator().start();
            } else {
                //返回重置
                getObjectAnimator().reverse();
            }
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }

    /**
     * 修复边界，防止超出图片范围
     */
    private void fixOffset() {
        //(bitmap.getWidth() * bigScale - getWidth()) / 2 -> 图片宽度 - view宽度 / 2
        //最大不能大于offsetX当前值
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
        //最小不能小于-offsetX当前值
        offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            //每一帧执行一次动画
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.getCurrX();
                offsetY = scroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

    class MyScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
        float initialScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            //常用到的两个方法
            //detector.getScaleFactor()获取缩放倍数
            //手势变化时，如果假设当前是1，那缩小就是0.9 0.8 0.7，放大就是1.1 1.2 1.3
            //detector.getFocusX()  detector.getFocusY() 分别获取两指手指的中心点
            currentScale = initialScale * detector.getScaleFactor();

            //缩放范围判断
            currentScale = Math.min(currentScale, bigScale);
            currentScale = Math.max(currentScale, smallScale);

            //以两指中心点进行缩放
//            offsetX = detector.getFocusX() - detector.getFocusX() * currentScale;
//            offsetY = detector.getFocusX() - detector.getFocusX() * currentScale;

            Log.d(TAG, "onScale:offsetX " + offsetX);
            Log.d(TAG, "onScale:offsetY " + offsetY);

            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initialScale = currentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
