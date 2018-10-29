package semisky.com.animation;

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
 * 内容摘要：${TODO}
 * 版权所有: YEYUAN
 * 修改内容：
 * 修改日期
 */
public class CameraView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Camera camera = new Camera();

    private static final float PADDING = Utils.dp2px(70);
    private static final float IMAGE_WIDTH = Utils.dp2px(300);

    float topFlip = 0;
    float bottomFlip = 0;
    float flipRotation = 0;

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        //如果目标图片过大，可能翻转效果会不理想，所以动态设置Z轴的值(拉远Z轴的位置)
        camera.setLocation(0, 0, Utils.getZForCamera());
    }

    public float getTopFlip() {
        return topFlip;
    }

    public void setTopFlip(float topFlip) {
        this.topFlip = topFlip;
        invalidate();
    }

    public float getBottomFlip() {
        return bottomFlip;
    }

    public void setBottomFlip(float bottomFlip) {
        this.bottomFlip = bottomFlip;
        invalidate();
    }

    public float getFlipRotation() {
        return flipRotation;
    }

    public void setFlipRotation(float flipRotation) {
        this.flipRotation = flipRotation;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制上半部分
        canvas.save();
        canvas.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2);
        canvas.rotate(-flipRotation);

        //保证camera每次都是重新刷新
        camera.save();
        //沿X轴翻转
        camera.rotateX(topFlip);
        //4.加载camera旋转效果
        camera.applyToCanvas(canvas);
        camera.restore();

        canvas.clipRect(-IMAGE_WIDTH, -IMAGE_WIDTH, IMAGE_WIDTH, 0);
        canvas.rotate(flipRotation);
        canvas.translate(-(PADDING + IMAGE_WIDTH / 2), -(PADDING + IMAGE_WIDTH / 2));
        canvas.drawBitmap(Utils.getAvatar(getResources(), (int) IMAGE_WIDTH), PADDING, PADDING, paint);
        canvas.restore();

        //绘制下半部分
        canvas.save();
        //5.移动图片到原来的位置
        canvas.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2);
        //Canvas 旋转切割的部分
        canvas.rotate(-flipRotation);

        //保证camera每次都是重新刷新
        camera.save();
        //沿X轴翻转
        camera.rotateX(bottomFlip);
        //4.加载camera旋转效果
        camera.applyToCanvas(canvas);
        camera.restore();

        //3.切割上半部分
        canvas.clipRect(-IMAGE_WIDTH, 0, IMAGE_WIDTH, IMAGE_WIDTH);
        //Canvas 旋转切割的部分
        canvas.rotate(flipRotation);
        //2.先移动图片到原点位置
        canvas.translate(-(PADDING + IMAGE_WIDTH / 2), -(PADDING + IMAGE_WIDTH / 2));
        //1.绘制目标图片
        canvas.drawBitmap(Utils.getAvatar(getResources(), (int) IMAGE_WIDTH), PADDING, PADDING, paint);
        canvas.restore();
    }
}
