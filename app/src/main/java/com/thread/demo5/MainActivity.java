package com.thread.demo5;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        progress_bar.setMax(4000);

        //属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 4000);
        valueAnimator.setDuration(2000) ;
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                progress_bar.setProgress((int) progress);
            }
        });
    }
}
