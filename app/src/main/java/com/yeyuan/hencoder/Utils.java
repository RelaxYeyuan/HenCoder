package com.yeyuan.hencoder;

import android.content.res.Resources;
import android.util.TypedValue;

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

}
