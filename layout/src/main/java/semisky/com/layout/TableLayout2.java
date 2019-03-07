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
 * 内容摘要:实现流式布局
 * 1.onLayout() 确定子view的布局
 * 2.onMeasure() 确定子View的大小
 *  1.动态设置子View的大小
 *
 *
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class TableLayout2 extends ViewGroup {

    private ArrayList<Rect> childrenBounds = new ArrayList<>();

    private int WIDTH_PADDING = (int) Utils.dp2px(10);
    private int HEIGHT_PADDING = (int) Utils.dp2px(5);

    public TableLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthUsed = 0;
        int heightUsed = 0;
        int lineMaxHeight = 0;
        int lineWidthUsed = 0;
        int lineHeightUsed = 0;

        int specWidth = MeasureSpec.getSize(widthMeasureSpec);

        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);

            //根据父类要求以及子类要求设置子类的大小 调用了child.measure()
            //0 代表子View可以随意设定大小
            measureChildWithMargins(childAt, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);

            //如果当前所有子view的宽度大于父View的宽度，则判断需要换行
            if (lineWidthUsed + childAt.getMeasuredWidth() > specWidth) {
                lineWidthUsed = 0;
                heightUsed += lineMaxHeight;
                lineMaxHeight = 0;

                measureChildWithMargins(childAt, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }

            Rect childBounds = new Rect();
            if (childrenBounds.size() <= i) {
                childrenBounds.add(childBounds);
            } else {
                childBounds = childrenBounds.get(i);
            }

            //记录view的大小
            childBounds.set(lineWidthUsed, heightUsed, lineWidthUsed + childAt.getMeasuredWidth(),
                    heightUsed + childAt.getMeasuredHeight());

            //动态改变所用的宽度 记录用了多少宽度了
            lineWidthUsed += childAt.getMeasuredWidth() + WIDTH_PADDING;
            lineHeightUsed = childAt.getMeasuredHeight() + HEIGHT_PADDING;

            widthUsed = Math.max(widthUsed, lineWidthUsed);
            lineMaxHeight = Math.max(lineMaxHeight, lineHeightUsed);
        }

        int width = widthUsed;
        int height = lineMaxHeight + heightUsed;

        //设定父View的大小
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < childrenBounds.size(); i++) {
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
