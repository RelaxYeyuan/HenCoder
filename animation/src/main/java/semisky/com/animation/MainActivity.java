package semisky.com.animation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private View view;
    private CircleView circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.view);
        circle = findViewById(R.id.circle);

        view.animate()
                .translationX(Utils.dp2px(200))
                .translationY(100)
                .rotation(180)
                .alpha(0.5f)
                .setStartDelay(1500)
                .start();

        //属性动画
        //AnimatorSet 组合动画
        //"radius" 通过反射方式去查找这个参数
        ObjectAnimator animator = ObjectAnimator.ofFloat(circle, "radius", Utils.dp2px(150));
        animator.setStartDelay(1000);//延迟
        animator.start();
    }
}
