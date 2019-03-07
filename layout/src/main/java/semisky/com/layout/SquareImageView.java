package semisky.com.layout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by chenhongrui on 2019/3/5
 * <p>
 * 内容摘要: 测绘顺序 onMeasure -> onLayout -> layout
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class SquareImageView extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = "SquareImageView";

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        Log.d(TAG, "layout: " + l);
        Log.d(TAG, "layout: " + t);
        Log.d(TAG, "layout: " + r);
        Log.d(TAG, "layout: " + b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: changed " + changed);
        Log.d(TAG, "onLayout: " + left);
        Log.d(TAG, "onLayout: " + top);
        Log.d(TAG, "onLayout: " + right);
        Log.d(TAG, "onLayout: " + bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: widthMeasureSpec " + widthMeasureSpec);
        Log.d(TAG, "onMeasure: heightMeasureSpec " + heightMeasureSpec);

        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();

        int max = Math.max(measuredHeight, measuredWidth);

        int i = resolveSize(max, widthMeasureSpec);
        int j = resolveSize(max, heightMeasureSpec);

        setMeasuredDimension(i, j);

    }
}
