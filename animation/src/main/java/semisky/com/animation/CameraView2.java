package semisky.com.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2018/10/28
 * <p>
 * 内容摘要：camera 三维几何变换
 * 流程：要先移动camera坐标系到原点（0,0）,再进行rotate,然后再移动回来
 * 难点：1.对于camera坐标系，绘制顺序要反着来(正常先画图 - 负方向移动 - 旋转 - 正方向移动) - > (实际先正方向移动 - 旋转 - 负方向移动 - 画图)
 * 2.setLocation()
 * 版权所有: YEYUAN
 * 修改内容：
 * 修改日期
 */
public class CameraView2 extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Camera camera = new Camera();

    private static final float PADDING = Utils.dp2px(70);
    private static final float IMAGE_WIDTH = Utils.dp2px(200);

    float leftFlip = 0;
    float rightFlip = 0;
    float flipRotation = 0;


    public CameraView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        //如果目标图片过大，可能翻转效果会不理想，所以动态设置Z轴的值(拉远Z轴的位置)
        camera.setLocation(0, 0, Utils.getZForCamera());
    }

    public void startAnim() {
        ObjectAnimator leftAnim = ObjectAnimator.ofFloat(this, "leftFlip", 45);

        ObjectAnimator rightAnim = ObjectAnimator.ofFloat(this, "rightFlip", -45);

        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(this, "flipRotation", 270);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(rightAnim, rotationAnim, leftAnim);
        animatorSet.setDuration(1500);
        animatorSet.setStartDelay(200);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                resetView();
//                startAnim();
            }
        });
    }

    private void resetView() {
        leftFlip = 0;
        rightFlip = 0;
        flipRotation = 0;
    }

    private void setLeftFlip(float scale) {
        this.leftFlip = scale;
        postInvalidate();
    }

    private void setRightFlip(float scale) {
        this.rightFlip = scale;
        postInvalidate();
    }

    private void setFlipRotation(float scale) {
        this.flipRotation = scale;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //左边
        canvas.save();
        canvas.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2);
        canvas.rotate(-flipRotation);

        camera.save();
        camera.rotateY(leftFlip);
        camera.applyToCanvas(canvas);
        camera.restore();

        canvas.clipRect(-IMAGE_WIDTH, IMAGE_WIDTH, 0, -IMAGE_WIDTH);
        canvas.rotate(flipRotation);
        canvas.translate(-(PADDING + IMAGE_WIDTH / 2), -(PADDING + IMAGE_WIDTH / 2));
        canvas.drawBitmap(Utils.getFlipboard(getResources(), (int) IMAGE_WIDTH), PADDING, PADDING, paint);
        canvas.restore();


        //右边
        canvas.save();
        canvas.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2);
        canvas.rotate(-flipRotation);

        camera.save();
        camera.rotateY(rightFlip);
        camera.applyToCanvas(canvas);
        camera.restore();

        canvas.clipRect(0, -IMAGE_WIDTH, IMAGE_WIDTH, IMAGE_WIDTH);
        canvas.rotate(flipRotation);
        canvas.translate(-(PADDING + IMAGE_WIDTH / 2), -(PADDING + IMAGE_WIDTH / 2));
        canvas.drawBitmap(Utils.getFlipboard(getResources(), (int) IMAGE_WIDTH), PADDING, PADDING, paint);
        canvas.restore();
    }
}
