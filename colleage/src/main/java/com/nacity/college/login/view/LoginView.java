package com.nacity.college.login.view;

import com.college.common_libs.domain.user.UserInfoTo;
import com.nacity.college.base.BaseView;

/**
 * Created by xzz on 2017/9/17.
 **/

public interface LoginView extends BaseView {
    void getVerificationCodeSuccess(String code);

    void registerSuccess();

    void loginSuccess(UserInfoTo userInfoTo);

    void loadingDismissPhone();

    void setCountTime(int second);
}
