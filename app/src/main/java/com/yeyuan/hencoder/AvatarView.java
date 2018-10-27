package com.yeyuan.hencoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2018/10/27
 * <p>
 * 内容摘要：${TODO}
 * 版权所有: YEYUAN
 * 修改内容：
 * 修改日期
 */
public class AvatarView extends View {

    private static final int WIDTH = (int) Utils.dp2px(170);
    private Bitmap bitmap;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private float padding = Utils.dp2px(80);
    private RectF savedArea = new RectF();
    private int edgeWidth = (int) Utils.dp2px(5);

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = getAvatar(WIDTH);
        savedArea.set(padding, padding, padding + WIDTH, padding + WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画黑边
        canvas.drawOval(padding, padding, padding + WIDTH, padding + WIDTH, paint);
        //设置离屏缓冲(提前把圆形矩形图片扣下来做处理)
        int saved = canvas.saveLayer(savedArea, paint);
        canvas.drawOval(padding + edgeWidth, padding + edgeWidth,
                padding + WIDTH - edgeWidth, padding + WIDTH - edgeWidth,
                paint);
        //设置为圆形的原图
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, padding, padding, paint);
        //恢复
        paint.setXfermode(null);
        //把扣下来的图片加上去
        canvas.restoreToCount(saved);
    }

    private Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.burning, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.mipmap.burning, options);
    }
}
