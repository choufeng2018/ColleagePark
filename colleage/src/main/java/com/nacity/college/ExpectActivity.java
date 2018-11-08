package com.nacity.college;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nacity.college.base.BaseActivity;


/**
 * Created by xzz on 2017/7/4.
 **/

public class ExpectActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.nacity.college.R.layout.activity_expect);
        setTitle(getIntent().getStringExtra("Title"));
    }
}
