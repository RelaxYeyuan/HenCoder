package semisky.com.animation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * 属性动画主要涉及到两个类
 * 1.ViewPropertyAnimator
 * 使用系统提供的动画
 * 2.ObjectAnimator
 * 可以自定义动画
 */
public class MainActivity extends AppCompatActivity {

    private View view;
    private CircleView circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.view);
        circle = findViewById(R.id.circle);
        Button btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ViewPropertyAnimator 系统提供的方法
                //可以做简单动画
                view.animate()
                        .scaleX(0.1f);
            }
        });

        //属性动画
        //AnimatorSet 组合动画
        //"radius" 通过反射方式去查找这个参数
        //150dp 意思是目标值
        ObjectAnimator animator = ObjectAnimator.ofFloat(circle, "radius", Utils.dp2px(150));

        animator.setStartDelay(1000);//延迟
        animator.setDuration(1000);
//        animator.start();
    }
}
