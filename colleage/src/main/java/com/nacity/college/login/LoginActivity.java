package com.nacity.college.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.college.common_libs.domain.user.UserInfoTo;
import com.nacity.college.R;
import com.nacity.college.base.ActivityUitl;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.utils.SoftKeyBoardUtil;
import com.nacity.college.login.model.LoginModel;
import com.nacity.college.login.presenter.LoginPresenter;
import com.nacity.college.login.view.LoginView;
import com.nacity.college.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import rx.Observable;

/**
 * Created by xzz on 2017/8/29.
 **/

public class LoginActivity extends BaseActivity implements LoginView {
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    @BindView(R.id.verification_code)
    EditText verificationCode;
    @BindView(R.id.get_verification)
    TextView getVerification;
    private LoginModel model;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setTitle(Constant.PHONE_LOGIN);
        model = new LoginPresenter(this);
        addVerificationListener();
        EventBus.getDefault().register(this);

    }


    @OnClick({R.id.get_verification, R.id.register, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                loadingShow();
                model.login(phoneNumber.getText().toString(), verificationCode.getText().toString());
                break;
            case R.id.register:
                Intent intent = new Intent(appContext, RegisterActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.get_verification:
                loadingShow();
                model.getVerificationCode("2", phoneNumber.getText().toString(),getVerification);

                break;
        }
    }

    //获取验证码成功
    @Override
    public void getVerificationCodeSuccess(String code) {
        loadingDismiss();
        loadingDismiss();
        showSuccess(Constant.GET_VERIFICATION_SUCCESS);

    }

    @Override
    public void registerSuccess() {

    }
    //登录成功

    @Override
    public void loginSuccess(UserInfoTo userInfoTo) {

        loadingDismiss();

        userInfo.updateUser(userInfoTo);
        apartmentInfo.updateApartment(userInfoTo.getCommonGarden());
        Intent intent;
        if (!TextUtils.isEmpty(userInfoTo.getNickname())) {
            intent = new Intent(appContext, MainActivity.class);
            Observable.from(ActivityUitl.activityList).subscribe(Activity::finish);
            userInfo.updateLogin(true);
        }
        else
            intent = new Intent(appContext, FinishDataActivity.class);
        startActivity(intent);
        goToAnimation(1);
        finish();
        model.upLoadDevice(userInfo.getSid());


    }

    @Override
    public void loadingDismissPhone() {
      loadingDismiss();
    }

    @Override
    public void setCountTime(int second) {
        runOnUiThread(() -> {
            if (second > 0)
                getVerification.setText(second + Constant.AFTER_VERIFICATION_CODE);
            else {
                getVerification.setText(Constant.RE_SEND_VERIFICATION_CODE);
                getVerification.setEnabled(true);
            }
        });
    }

    @Subscribe
    public void getVerificationTime(Event<Integer> event) {
        runOnUiThread(() -> {
            if ("VerificationTimeLogin".equals(event.getType())) {
                getVerification.setText(event.getMode() + Constant.AFTER_VERIFICATION_CODE);
            } else if ("VerificationTimeEndLogin".equals(event.getType())) {
                getVerification.setEnabled(true);
                getVerification.setText(Constant.RE_SEND_VERIFICATION_CODE);
            }
        });


    }


    //输入6位验证码后隐藏键盘
    private void addVerificationListener() {
        verificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6)
                    SoftKeyBoardUtil.hideInputWindow(LoginActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
