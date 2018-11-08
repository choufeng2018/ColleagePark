package com.nacity.college.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nacity.college.R;
import com.nacity.college.base.ActivityUitl;
import com.nacity.college.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xzz on 2017/8/29.
 **/

public class LoginOrRegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        ButterKnife.bind(this);
        ActivityUitl.activityList.add(this);

    }

    @OnClick({R.id.login, R.id.register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                Intent intent = new Intent(appContext, LoginActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.register:
                intent = new Intent(appContext, RegisterActivity.class);
                startActivity(intent);
                goToAnimation(1);

                break;
        }
    }

}
