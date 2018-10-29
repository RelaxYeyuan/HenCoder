package semisky.com.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity {

    CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        cameraView = findViewById(R.id.cameraView);

        ObjectAnimator bottomFlipAnimator = ObjectAnimator
                .ofFloat(cameraView, "bottomFlip", 45);
        bottomFlipAnimator.setStartDelay(1000);

        ObjectAnimator flipRotationAnimator = ObjectAnimator
                .ofFloat(cameraView, "flipRotation", 270);
        flipRotationAnimator.setStartDelay(1000);

        ObjectAnimator topFlipAnimator = ObjectAnimator
                .ofFloat(cameraView, "topFlip", -45);
        topFlipAnimator.setStartDelay(1000);

        //组合动画 按顺序做动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(bottomFlipAnimator, flipRotationAnimator, topFlipAnimator);
        animatorSet.setDuration(1000);
//        animatorSet.start();

        //对于同一个view同时做动画
        PropertyValuesHolder bottomFlipHolder = PropertyValuesHolder.ofFloat("bottomFlip"
                , 45);
        PropertyValuesHolder flipRotationHolder = PropertyValuesHolder.ofFloat("flipRotation"
                , 270);
        PropertyValuesHolder topFlipHolder = PropertyValuesHolder.ofFloat("topFlip"
                , -45);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(cameraView, bottomFlipHolder,
                flipRotationHolder, topFlipHolder);
        objectAnimator.setStartDelay(1000);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
    }
}
