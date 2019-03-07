package semisky.com.animation;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {

    CameraView cameraView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imageView = findViewById(R.id.image);
        cameraView = findViewById(R.id.cameraView);

        ObjectAnimator bottomFlipAnimator = ObjectAnimator
                .ofFloat(cameraView, "topFlip", 45);
        bottomFlipAnimator.setStartDelay(1000);

        ObjectAnimator flipRotationAnimator = ObjectAnimator
                .ofFloat(cameraView, "flipRotation", 270);
        flipRotationAnimator.setStartDelay(1000);

        ObjectAnimator topFlipAnimator = ObjectAnimator
                .ofFloat(cameraView, "bottomFlip", -45);
        topFlipAnimator.setStartDelay(1000);

        //组合动画 按顺序做动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(bottomFlipAnimator, flipRotationAnimator, topFlipAnimator);
        animatorSet.setDuration(1000);
        animatorSet.start();

        //对于同一个view同时做动画
        PropertyValuesHolder bottomFlipHolder = PropertyValuesHolder.ofFloat("topFlip"
                , 45);
        PropertyValuesHolder flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation"
                , 270);
        PropertyValuesHolder topFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip"
                , -45);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(cameraView, bottomFlipHolder,
                flipRotationHolder, topFlipHolder);
        objectAnimator.setStartDelay(1000);
        objectAnimator.setDuration(1000);
//        objectAnimator.start();

        //可以自定义关键帧
        float length = Utils.dp2px(300);
        Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
        Keyframe keyframe2 = Keyframe.ofFloat(0.2f, 1.4f * length);
        Keyframe keyframe3 = Keyframe.ofFloat(0.6f, 0.8f * length);
        Keyframe keyframe4 = Keyframe.ofFloat(1, 1 * length);

        PropertyValuesHolder viewHolder = PropertyValuesHolder.ofKeyframe("translationX",
                keyframe1, keyframe2, keyframe3, keyframe4);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, viewHolder);
        animator.setStartDelay(1000);
        animator.setDuration(1000);
//        animator.start();


    }
}
