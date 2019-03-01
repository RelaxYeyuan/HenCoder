package semisky.com.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);

        CameraView2 cameraView2 = findViewById(R.id.cameraView2);
        cameraView2.startAnim();
    }
}
