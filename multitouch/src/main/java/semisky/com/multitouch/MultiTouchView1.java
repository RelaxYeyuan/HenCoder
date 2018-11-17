package semisky.com.multitouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by chenhongrui on 2018/11/17
 * <p>
 * 内容摘要:
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class MultiTouchView1 extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Bitmap bitmap;

    float offsetX;
    float offsetY;

    float originalOffsetX;
    float originalOffsetY;

    float downX;
    float downY;

    //记录新手指ID 通过index获取ID
    int trackingPointerId;

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = Utils.getAvatar(getResources(), 800);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                trackingPointerId = event.getPointerId(0);
                //防止跳动
                downX = event.getX();
                downY = event.getY();
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                //通过ID获取index
                int index = event.findPointerIndex(trackingPointerId);
                //图片初试(上次最后)的位置 + 移动的距离 - 按下最初的位置
                //getX 根据最新不同的index获取位置
                offsetX = originalOffsetX + event.getX(index) - downX;
                offsetY = originalOffsetY + event.getY(index) - downY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //拿到新手指的index
                int actionIndex = event.getActionIndex();
                //根据index获取id
                trackingPointerId = event.getPointerId(actionIndex);
                //防止跳动
                //通过id获取新手指点击的位置
                downX = event.getX(trackingPointerId);
                downY = event.getY(trackingPointerId);
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                if (pointerId == trackingPointerId) {
                    int newIndex;
                    if (actionIndex == event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        newIndex = event.getPointerCount() - 1;
                    }
                    trackingPointerId = event.getPointerId(newIndex);
                    downX = event.getX(actionIndex);
                    downY = event.getY(actionIndex);
                    originalOffsetX = offsetX;
                    originalOffsetY = offsetY;
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
