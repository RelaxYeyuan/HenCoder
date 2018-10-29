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

        //ViewPropertyAnimator 系统提供的方法
        //可以做简单动画
        view.animate()
                .translationX(Utils.dp2px(200))
                .translationY(100)
                .rotation(180)
                .setDuration(1000)//持续时间
                .alpha(0.5f)//透明度
                .setStartDelay(1500)
                .start();

        //属性动画
        //AnimatorSet 组合动画
        //"radius" 通过反射方式去查找这个参数
        //150dp 意思是目标值
        ObjectAnimator animator = ObjectAnimator.ofFloat(circle, "radius", Utils.dp2px(150));

        animator.setStartDelay(1000);//延迟
        animator.setDuration(1000);
        animator.start();
    }
}
