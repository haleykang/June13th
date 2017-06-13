package com.haley.test.june13th;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

// 카운트다운 예제

public class Thread2Activity extends Activity {

    TextView mCountTextView = null;
    CountDownTimer mCountDownTimer = null;

    class TestCountDownTimer extends CountDownTimer {

        // 1.생성자
        public TestCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        // 2.재정의 함수
        @Override
        public void onTick(long l) {

            // 매번 틱마다 남은 초를 출력
            mCountTextView.setText(l / 1000 + " 초");

        }

        @Override
        public void onFinish() {
            // 카운트다운이 완료된 경우 카운트다운의 최종 초를 출력
            mCountTextView.setText("0 초");
        }
    } // end of class TestCountDownTimer extends CountDownTimer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread2);

        mCountTextView = (TextView)findViewById(R.id.countdown_text);

        // 총 60초 동안 1초씩 카운트다운 객체 생성
        mCountDownTimer = new TestCountDownTimer(60000,1000);

        // 카운트다운 초기값 출력
        mCountTextView.setText("60 초");
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.start_countdown_bt :
                // 총 60초 카운트다운 시작
                mCountDownTimer.start();
                break;
            case R.id.reset_countdown_bt :
                // 카운트다운 중단 / 초 리셋
                mCountDownTimer.cancel();
                mCountTextView.setText("60 초");
                break;
        }
    }
}
