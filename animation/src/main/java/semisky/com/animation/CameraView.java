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

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        //沿X轴翻转
        camera.rotateX(45);
        //如果目标图片过大，可能翻转效果会不理想，所以动态设置Z轴的值(拉远Z轴的位置)
        camera.setLocation(0, 0, Utils.getZForCamera());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制上半部分
        canvas.save();
        canvas.translate(100 + 300 / 2, 100 + 300 / 2);
        canvas.rotate(-20);
        canvas.clipRect(-300, -300, 300, 0);
        canvas.rotate(20);
        canvas.translate(-(100 + 300 / 2), -(100 + 300 / 2));
        canvas.drawBitmap(Utils.getAvatar(getResources(), 300), 100, 100, paint);
        canvas.restore();

        //绘制下半部分
        canvas.save();
        //5.移动图片到原来的位置
        canvas.translate(100 + 300 / 2, 100 + 300 / 2);
        canvas.rotate(-20);
        //4.加载camera旋转效果
        camera.applyToCanvas(canvas);
        //3.切割上半部分
        canvas.clipRect(-300, 0, 300, 300);
        canvas.rotate(20);
        //2.先移动图片到原点位置
        canvas.translate(-(100 + 300 / 2), -(100 + 300 / 2));
        //1.绘制目标图片
        canvas.drawBitmap(Utils.getAvatar(getResources(), 300), 100, 100, paint);
        canvas.restore();
    }
}
