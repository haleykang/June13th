package com.haley.test.june13th;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class TotalAsyncTaskActivity extends Activity {

    // 변수 선언
    private TextView mTextView;
    private Context mContext;
    private MyAsyncTask mMyAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_async_task);
        myLog("onCreate() 실행");

        this.mTextView = (TextView)this.findViewById(R.id.TvResultMain);
        this.mContext = this;
        this.mMyAsyncTask = new MyAsyncTask();
        // 사용자가 버튼을 클릭하면 AsyncTask 클래스가 갖고 있는 execute() 함수를 실행
        // -> MyAsyncTask 클래스에서 재정의한 onPreExecute() 함수가 먼저 실행

      /*  this.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLog("onClick() 버튼 클릭");
                mMyAsyncTask.execute();
                myLog("비동기 테스크 클래스 동작 시작");
            }
        });*/

    } // end of onCreate()

    // onClick 재정의
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.BtAddMain:
                myLog("계산 버튼 클릭");
                mMyAsyncTask.execute();
                myLog("비동기 테스크 클래스 동작 시작");
                break;
            case R.id.BtStopMain:
                myLog("중단 버튼 클릭");
                //mMyAsyncTask.onCancelled();
                mMyAsyncTask.cancel(false);
                break;
        }
    }


    // 1. AsyncTask 클래스를 상속받는 자식 클래스를 액티비티 클래스의 내부 클래스로 만들기
    private class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        // 1~100까지 총합 보관할 전역 변수 선언
        private Integer mResultNumber;

        // 1. Integer : doInBackground() 함수의 매개 변수 클래스
        // -> doInBackground(Integer ... params) {}

        // 2. Integer : onProgressUpdate() 함수의 매개 변수로 사용하는 클래스
        // -> onProgressUpdate(Integer) {}

        // 3. Integer : 결과 값의 자료형(클래스) -> doInBackGround() 함수의 반환형
        // -> onPostExecute() 함수의 매개 변수로 사용


        // 1. onPreExecute() 함수 재정의: 다른 함수보다 먼저 실행되는 함수
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myLog("onPreExecute() 실행");
            mResultNumber = 0;
            myLog("변수 mResultNumber 초기화 완료");
        } // end of onPreExecute()

        // 2. onPostExecute() 함수 재정의 : 제일 나중에 실행되는 함수
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            myLog("onPostExecute() 실행");

            myLog("최종 값 : " + integer);
        } // end of onPostExecute()

        // 3. doInBackground() 함수 재정의
        @Override
        protected Integer doInBackground(Integer... integers) {
            myLog("doInBackground() 실행");

            // for 반복문 작성
            for(int i = 1; i <= 100; ++i) {

                if(isCancelled() == true) {
                    return null;
                }
                int tempResult = mResultNumber;

                mResultNumber += i;


                // onProgressUpdate() 함수 실행 : 현재까지 누적된 값을 전달
                publishProgress(i, tempResult, mResultNumber);

                // sleep() 함수 : 0.05초 동안 스레드 잠재우기
                try {
                    Thread.sleep(500);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return mResultNumber;
        } // end of doInBackground()

        // 4. onProgressUpdate() 함수 재정의 : 중간 계산 결과를 출력하는 함수
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            myLog("onProgressUpdate() 실행");

            int i = values[0];
            int tempResult = values[1];
            int result = values[2];

            // doInBackground() 함수로 부터 받은 중간 계산 값을 화면에 출력
            mTextView.setText(i + "+" + tempResult + "=" + result);

        } // end of onProgressUpdate()

        // 5. onCancelled() 함수 재정의 : 사용자가 취소 버튼 누른 경우에 실행되는 함수

        @Override
        protected void onCancelled() {

            myLog("onCancelled() 실행");
            mTextView.setText("계산 중단");
            super.onCancelled();

        }
    } // end of private class MyAsyncTask

    // 재정의 함수


    // 사용자 정의 함수
    public void myLog(String ob) {

        Log.v("*TotalAsyncTaskActivity", ob);
    } // end of myLog()
} // end of TotalAsyncTaskActivity
