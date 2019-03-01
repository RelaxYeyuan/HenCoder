package semisky.com.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by chenhongrui on 2018/11/12
 * <p>
 * 内容摘要:
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class ShadeTextView extends android.support.v7.widget.AppCompatTextView {

    private static final String TAG = "ShadeTextView";

    LinearGradient linearGradient;
    Paint paint;
    Matrix matrix;
    int translate;
    int viewWidth;

    public ShadeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        setTextSize(25);
        Log.d(TAG, "instance initializer: ");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: ");
        paint = getPaint();
        viewWidth = getMeasuredWidth();
        linearGradient = new LinearGradient(0, 0, viewWidth, 0,
                new int[]{Color.BLUE, 0xffffff, Color.BLUE}, null, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        translate += viewWidth / 5;
        if (translate > 2 * viewWidth) {
            translate = -viewWidth;
        }

        matrix.setTranslate(translate, 0);
        linearGradient.setLocalMatrix(matrix);
        postInvalidateDelayed(100);
    }
}
