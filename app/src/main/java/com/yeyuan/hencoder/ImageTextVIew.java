package com.yeyuan.hencoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
public class ImageTextVIew extends View {

    private Paint paint = new Paint();
    private Bitmap bitmap;
    private float[] cutWidth = new float[1];
    private Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    private static final float IMAGE_WIDTH = Utils.dp2px(100);
    private static final float IMAGE_OFFSET = Utils.dp2px(80);

    public ImageTextVIew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(Utils.dp2px(16));
        bitmap = getAvatar((int) Utils.dp2px(100));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, getWidth() - IMAGE_WIDTH, 30 +IMAGE_OFFSET, paint);

        String text = "下节会讲这节没讲完的文字的测量,文字的测量对于自定义绘制非常重要,真正完全掌握的人非常少,所以上课的时候一定要好好听," +
                "下节会把 HenCoder 讲到的 Camera 的原理进一步阐明，包括会把 HenCoder 提到的「Canvas 的几何变换是反的」解释清楚." +
                "动画和硬件加速 Bitmap 和 Drawable";
//        int index = paint.breakText(text, true, getWidth(), cutWidth);
//        canvas.drawText(text, 0, index, 0, 50, paint);
//        int oldIndex = index;
//        index = paint.breakText(text, index, text.length(), true, getWidth(), cutWidth);
//        canvas.drawText(text, oldIndex, oldIndex + index, 0, 50 + paint.getFontSpacing(), paint);
//        oldIndex = index;
//        index = paint.breakText(text, index, text.length(), true, getWidth()
//                - Utils.dp2px(100), cutWidth);
//        canvas.drawText(text, oldIndex, oldIndex + index, 0, 50 + paint.getFontSpacing() * 2, paint);

        int length = text.length();
        float verticalOffset = -fontMetrics.top;
        for (int start = 0; start < length; ) {
            int maxWidth;
            float textTop = verticalOffset + fontMetrics.top;
            float textBottom = verticalOffset + fontMetrics.bottom;
            if (textTop > IMAGE_OFFSET && textTop < IMAGE_OFFSET + IMAGE_WIDTH
                    || textBottom > IMAGE_OFFSET && textBottom < IMAGE_OFFSET + IMAGE_WIDTH) {
                // 文字和图片在同一行
                maxWidth = (int) (getWidth() - IMAGE_WIDTH);
            } else {
                // 文字和图片不在同一行
                maxWidth = getWidth();
            }
            int count = paint.breakText(text, start, length, true, maxWidth, cutWidth);
            canvas.drawText(text, start, start + count, 0, 30 + verticalOffset, paint);
            start += count;
            verticalOffset += paint.getFontSpacing();
        }
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
