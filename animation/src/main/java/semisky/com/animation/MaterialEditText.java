package semisky.com.animation;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * Created by chenhongrui on 2019/3/4
 * <p>
 * 内容摘要:
 * 版权所有：Semisky
 * 修改内容：
 * 修改日期
 */
public class MaterialEditText extends android.support.v7.widget.AppCompatEditText {

    private static final String TAG = "MaterialEditText";

    private Paint mPaint;

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private static final float TEXT_SIZE = Utils.dp2px(12);
    private static final float TEXT_MARGIN = Utils.dp2px(8);
    private static final int TEXT_VERTICAL_OFFSET = (int) Utils.dp2px(22);
    private static final int TEXT_HORIZONTAL_OFFSET = (int) Utils.dp2px(5);
    private static final int TEXT_ANIMATION_OFFSET = (int) Utils.dp2px(16);
    float floatingLabelFraction;
    boolean floatingLabelShown = true;

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(TEXT_SIZE);
        mPaint.setColor(Color.WHITE);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!floatingLabelShown && TextUtils.isEmpty(s)) {
                    getAnim().reverse();
                    floatingLabelShown = true;
                } else if (floatingLabelShown && !TextUtils.isEmpty(s)) {
                    getAnim().start();
                    floatingLabelShown = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        setPadding(getPaddingLeft(), (int) (getPaddingTop() + TEXT_SIZE + TEXT_MARGIN),
                getPaddingRight(), getPaddingBottom());
    }

    private ObjectAnimator getAnim() {
        return ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0, 1);
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAlpha((int) (0xff * floatingLabelFraction));
        float extraOffset = TEXT_ANIMATION_OFFSET * (1 - floatingLabelFraction);
        canvas.drawText(getHint().toString(), TEXT_HORIZONTAL_OFFSET, TEXT_VERTICAL_OFFSET + extraOffset, mPaint);
    }

}
