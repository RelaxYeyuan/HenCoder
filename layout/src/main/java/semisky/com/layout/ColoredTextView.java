package semisky.com.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.Random;

/**
 * Created by chenhongrui on 2018/11/6
 * <p>
 * 内容摘要:
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class ColoredTextView extends android.support.v7.widget.AppCompatTextView {

    private static final int[] COLORS = {
            Color.parseColor("#E91E63"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#009688"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#795548")
    };

    private Random random = new Random();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final int[] TEXT_SIZES = {
            16, 22, 28
    };

    private static final int CORNER_RADIUS = (int) Utils.dp2px(4);
    private static final int X_PADDING = (int) Utils.dp2px(16);
    private static final int Y_PADDING = (int) Utils.dp2px(8);

    public ColoredTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setColor(COLORS[random.nextInt(COLORS.length)]);
        setTextColor(Color.WHITE);
        setTextSize(TEXT_SIZES[random.nextInt(TEXT_SIZES.length)]);
        setPadding(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS, paint);
        super.onDraw(canvas);
    }
}
