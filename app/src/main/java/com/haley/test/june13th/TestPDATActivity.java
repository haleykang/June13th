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
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class TestPDATActivity extends Activity {

    // 새로 만들 파일 이름을 상수로 정하기
    // -> 나중에는 사용자한테 입력 받아서 파일이름 정하기
    public static final String MY_NEW_FILE = "my_new_file.txt";

    // 사용자한테 파일 이름을 넘겨받아보자 -> 다이얼로그창 생성해서(전면 수정)
    private String mNewFileName = "";

    // 1. 전역 변수
    private Button mShowDialogBt;
    private Context mContext;
    private MyAsyncTask mMyAsyncTask = null;
    // 프로그레스 다이얼로그 객체 참조 변수
    private ProgressDialog mProgressDialog = null;
    private Button mCreateBt;
    private EditText mFileNameEt;

    // 2. 재정의 함수
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pdat);
        myLog("onCreate() 실행");

        // 방금 만든 createNewFile() 함수 실행


        // ctrl+shift+화살표 : 화살표 방향으로 한 단어 선택
        // ctrl+backspace : 커서 오른쪽 단어 삭제

        this.mContext = this;
        this.mShowDialogBt = (Button)this.findViewById(R.id.showDialogBt);
        this.mCreateBt = (Button)this.findViewById(R.id.BtCreateNewFile);
        this.mFileNameEt = (EditText)this.findViewById(R.id.EtInputFileName);

        this.mShowDialogBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLog("작업 진행 상황 출력 버튼 클릭");
                mMyAsyncTask = new MyAsyncTask();
                mMyAsyncTask.execute();
            }
        });

        this.mCreateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLog("파일 생성 버튼 클릭");

                try {
                    createNewFile();
                } catch(IOException e) {
                    e.printStackTrace();
                }

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

    // 새로운 파일을 생성해주는 함수 만들기
    public void createNewFile() throws IOException {

        // throws로 예외상황을 넘긴 경우, createNewFile() 함수를 호출하는 쪽에서
        // 꼭 try~catch() 블럭을 만들어야함

        // 1. 파일을 만들 디렉토리 패스(상대 경로)를 읽어와서 변수에 저장
        File relativePath = this.getFilesDir();

        // 2. 절대 경로를 읽어와서 변수에 저장
        String absolutePath = relativePath.getAbsolutePath();
        myLog("파일이 저장될 디렉토리의 절대 경로 : " + absolutePath);

        mNewFileName = mFileNameEt.getText().toString();

        if(mNewFileName != null && mNewFileName.trim().length() != 0) {

            // 3. 파일을 만들때 사용할 파일 객체 생성 : 위에서 만든 절대 경로 사용
            File myNewFile = new File(absolutePath + "/" + mNewFileName);

            // 4.File 클래스가 갖고 있는 createNewFile() 함수를 실행하면 파일 생성
            // createNewFile() 함수의 반환 값을 보관할 임시 변수
            boolean temp;

            // createNewFile() 함수를 실행해서 절대 경로에 새로운 파일 생성
            temp = myNewFile.createNewFile();

            // createNewFile() 함수가 true인 경우 - 파일을 새로 생성함
            // false 값을 준 경우 - 파일이 이미 있는 경우
            if(temp == true) {
                myLog(mNewFileName + " 파일 생성 성공");
                Toast.makeText(TestPDATActivity.this, mNewFileName + " - 파일 생성"
                        , Toast.LENGTH_SHORT).show();

            } else {
                myLog(mNewFileName + " 파일이 이미 존재합니다.");
                Toast.makeText(TestPDATActivity.this, mNewFileName + " - 이미 존재하는 파일입니다."
                        , Toast.LENGTH_SHORT).show();

            }


        } else {
            Toast.makeText(TestPDATActivity.this, "파일 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
        }


    }
}
