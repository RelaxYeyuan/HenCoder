package semisky.com.layout;

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

    /**
     * 兼容性配置
     */
    public static float getZForCamera() {
        return -8 * Resources.getSystem().getDisplayMetrics().density;
    }

}
