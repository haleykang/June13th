package com.haley.test.june13th;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.toTotalAsyncTask:
                sendIntent(TotalAsyncTaskActivity.class);
                break;
            case R.id.toThread :
                sendIntent(ThreadActivity.class);
                break;
            case R.id.toCountDown :
                sendIntent(Thread2Activity.class);
                break;
            case R.id.toTestPD :
                sendIntent(TestPDATActivity.class);
                break;
            case R.id.toTestLayout :
                sendIntent(TestLayoutActivity.class);
                break;
        }

    }

    private void sendIntent(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
