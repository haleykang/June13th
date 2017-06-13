package com.haley.test.june13th;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ThreadActivity extends Activity {

    TextView mDownloadStateTextView = null;
    FileDownloadTask mFileDownloadTask = null;


    private class FileDownloadTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            // 최초 화면에 내려받기 시도를 알리는 텍스트 출력

            mDownloadStateTextView.setText("FileDownLoad...");
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... downloadInfos) {

            int totalCount = downloadInfos.length;
            // 전달받은 파일 url 개수 만큼 반복하면서 파일 내려받기
            for(int i = 1; i <= totalCount; ++i) {
                // 1. 파일 내려받기 처리 상태를 표시하기 위해 호출
                publishProgress(i, totalCount);

                // 2. 아래를 파일을 내려받는 과정이랑 가정
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException e) {
                    return false;
                }
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... downloadInfos) {

            int currentCount = downloadInfos[0];
            int totalCount = downloadInfos[1];

            // 현재 파일 내려받기 상태 표시. 에) Downloading : 3/10
            mDownloadStateTextView.setText("Downloading : " + currentCount + "/" + totalCount);

            super.onProgressUpdate(downloadInfos);
        }

        @Override
        protected void onCancelled() {

            // 화면에 내려받기가 취소되었다는 텍스트 출력
            mDownloadStateTextView.setText("Download cancel");
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // 화면에 내려받기 성공/실패 여부를 텍스트로 출력
            if(true == result) {
                mDownloadStateTextView.setText("Download finish");
            } else {
                mDownloadStateTextView.setText("Download Fail");
            }
            super.onPostExecute(result);
        }
    } // end of FileDownloadTask

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        mDownloadStateTextView = (TextView)findViewById(R.id.download_state_textview);

        mFileDownloadTask = new FileDownloadTask();
        mFileDownloadTask.execute("FileUrl-1", "FileUrl-2", "FileUrl-3", "FileUrl-4",
                "FileUrl-5", "FileUrl-6", "FileUrl-7", "FileUrl-8", "FileUrl-9",
                "FileUrl-10");
    } // end of onCreate()

    public void onClick(View v) {
        // 만일 파일 내려받기가 종료 상태가 아니면 진행 취소
        if(mFileDownloadTask != null &&
                mFileDownloadTask.getStatus() != AsyncTask.Status.FINISHED) {
            mFileDownloadTask.cancel(true);
        }
    }
} // end of ThreadActivity
