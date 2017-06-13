package com.haley.test.june13th;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by 202-18 on 2017-06-13.
 */

public class TestLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout rootLinear = new LinearLayout(this);
        rootLinear.setOrientation(LinearLayout.VERTICAL);

        FrameLayout.LayoutParams rootLp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        rootLp.setMargins(20, 20, 20, 20);

        // =============================


        LinearLayout nameInputLinear = new LinearLayout(this);
        nameInputLinear.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams nameInputLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv = new TextView(this);
        tv.setText("이름을 입력하세요");

        LinearLayout.LayoutParams nameTvLp = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT);
        nameTvLp.weight = 1;

        EditText nameEt = new EditText(this);
        LinearLayout.LayoutParams nameEtLp = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT);
        nameEtLp.weight = 1;

        nameInputLinear.addView(tv, nameTvLp);
        nameInputLinear.addView(nameEt, nameEtLp);

        rootLinear.addView(nameInputLinear, nameInputLp);

        Button saveBt = new Button(this);
        saveBt.setText("저장하기");

        LinearLayout.LayoutParams saveBtLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        rootLinear.addView(saveBt, saveBtLp);

        setContentView(rootLinear, rootLp);
    }
}
