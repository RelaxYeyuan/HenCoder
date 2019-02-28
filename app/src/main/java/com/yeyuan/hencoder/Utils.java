package com.yeyuan.hencoder;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by chenhongrui on 2018/10/24
 * <p>
 * 内容摘要:
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class Utils {
    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }


    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case View.MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }
}
