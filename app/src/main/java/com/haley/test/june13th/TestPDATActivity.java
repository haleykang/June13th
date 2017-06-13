package com.haley.test.june13th;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TestPDATActivity extends Activity {

    // 1. 전역 변수
    private Button mShowDialogBt;
    private Context mContext;
    private MyAsyncTask mMyAsyncTask = null;
    // 프로그레스 다이얼로그 객체 참조 변수
    private ProgressDialog mProgressDialog = null;

    // 2. 재정의 함수
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pdat);
        myLog("onCreate() 실행");

        // ctrl+shift+화살표 : 화살표 방향으로 한 단어 선택
        // ctrl+backspace : 커서 오른쪽 단어 삭제

        this.mContext = this;
        this.mShowDialogBt = (Button)this.findViewById(R.id.showDialogBt);

        this.mShowDialogBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLog("버튼 클릭");
                mMyAsyncTask = new MyAsyncTask();
                mMyAsyncTask.execute();
            }
        });

    }

    // AsyncTask 클래스를 상속 받는 자식 클래스를 내부 클래스로 만들기
    private class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        // 필수 재정의 함수 - onPreExecute(), onPostExecute(), doInBackground()
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myLog("onPreExecute() 실행");

            mProgressDialog = new ProgressDialog(mContext);
            // 스타일 변경 : 디폴트는 원형
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // 작업 최대 정수 값
            mProgressDialog.setMax(10);
            // 타이틀
            mProgressDialog.setTitle("고구마 수확률");
            // 아이콘 정하기
            mProgressDialog.setIcon(R.mipmap.ic_launcher_round);
            // 취소 버튼 넣기
            mProgressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE,
                    "취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            myLog("취소 버튼 클릭");
                            mMyAsyncTask.cancel(false);
                            // false : 현재 실행 중인 스레드를 잠시 대기 상태로 둠
                            mProgressDialog.dismiss();
                        }
                    });

            // 다이얼로그 출력
            mProgressDialog.show();

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            myLog("onPostExecute() 실행");
            mProgressDialog.dismiss();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            myLog("doInBackground() 실행");

            // 다이얼로그 작업 최대 정수 값 읽어오기
            int maxProgressDialog = mProgressDialog.getMax();

            // for 반복문을 작성해서 0 ~ max 까지 프로그레스 바를 변화 시킴
            for(int i = 0; i <= maxProgressDialog; ++i) {

                // 사용자가 취소 버튼을 눌렀을지 여부 검사
                if(isCancelled() == true) {

                    // isCancelled() : cancel() 함수를 실행하면 true, 실행 안했으면 false
                    myLog("if(isCancelled() == true)");
                    // break; : 반복문만 벗어남
                    // return 명령문 써서 함수 전체 벗어나기
                    return null;

                }

                publishProgress(i);
                // onProgressUpdate() 함수를 실행
                try {
                    Thread.sleep(500);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            } // end of for
            return maxProgressDialog;
        }

        // 선택 재정의 함수
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            myLog("onProgressUpdate() 실행");

            mProgressDialog.setProgress(values[0]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            myLog("onCancelled() 실행");
        }
    } // end of MyAsyncTask


    // 3. 사용자 정의 함수
    public void myLog(String ob) {
        Log.v("*TestPDATActivity*", ob);
    }
}
