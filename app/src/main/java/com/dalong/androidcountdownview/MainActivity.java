package com.dalong.androidcountdownview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.view.View;
import com.dalong.countdownview.CountDownView;

public class MainActivity extends AppCompatActivity {

    private CountDownView countDownView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        countDownView=(CountDownView)findViewById(R.id.countDownView);
        countDownView.setOnCountDownListener(new CountDownView.OnCountDownListener() {
            @Override
            public void start() {
                Toast.makeText(MainActivity.this, "倒计时开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void finish() {
                Toast.makeText(MainActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 开始
     * @param view
     */
    public void onStart(View view) {
        countDownView.startCountdown();
    }

    /**
     * 停止
     * @param view
     */
    public void onStop(View view) {
        countDownView.stopCountdown();
    }
}
