package semisky.com.layout;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by chenhongrui on 2018/11/6
 * <p>
 * 内容摘要:
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class TableLayout extends ViewGroup {

    private ArrayList<Rect> childrenBounds = new ArrayList<>();

    private int padding = 50;

    public TableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //用掉的宽度
        int widthUsed = 0;
        //用掉的高度
        int heightUsed = 0;

        int lineWidthUsed = 0;
        //最高的view
        int lineMaxHeight = 0;
        //父控件的宽度
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);

        //计算出子view的大小
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //显示第一行
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            //判断是一行能显示多少
            if (specMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.getMeasuredWidth() > specWidth) {
                //换行
                lineWidthUsed = 0;
                heightUsed += lineMaxHeight;
                lineMaxHeight = 0;

                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }

            Rect childBound;
            if (childrenBounds.size() <= i) {
                childBound = new Rect();
                childrenBounds.add(childBound);
            } else {
                childBound = childrenBounds.get(i);
            }

            childBound.set(lineWidthUsed, heightUsed, lineWidthUsed + child.getMeasuredWidth(),
                    heightUsed + child.getMeasuredHeight());
            lineWidthUsed += child.getMeasuredWidth();

            widthUsed = Math.max(widthUsed, lineWidthUsed);
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());
        }

        int width = widthUsed;
        int height = heightUsed + lineMaxHeight;

        setMeasuredDimension(width, height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //1.确认子view的大小
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            Rect rect = childrenBounds.get(i);
            childAt.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
