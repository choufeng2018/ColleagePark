package com.nacity.college.common.presenter;

import android.util.Log;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.LoginApi;
import com.college.common_libs.api.UserApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.login.DeviceParam;
import com.college.common_libs.domain.user.UserFansTo;
import com.college.common_libs.domain.user.UserIdParam;
import com.college.common_libs.domain.user.UserInfoTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.BaseView;
import com.nacity.college.common.model.UserInfoModel;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  Created by xzz on 2017/9/29.
 **/

public class UserInfoPresenter extends BasePresenter implements UserInfoModel {
    private BaseView<UserInfoTo> userView;

    public UserInfoPresenter(BaseView<UserInfoTo> userView) {
        this.userView = userView;
    }

    @Override
    public void getUserInfo() {
        getUserFans(userInfo.getSid());
        UserIdParam param = new UserIdParam();
        param.setUserId(userInfo.getSid());
        UserApi api = ApiClient.create(UserApi.class);
        api.getUserInfo(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<UserInfoTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MessageTo<UserInfoTo> msg) {
                        if (msg.getSuccess() == 0)
                            Log.i("4444", "UserInfoTo: "+msg.getData().toString());
                            userView.getDataSuccess(msg.getData());
                    }
                }
        );
    }



    @Override
    public void logOut(String userId) {
         LoginApi api = ApiClient.create(LoginApi.class);

        DeviceParam param = new DeviceParam();
        param.setUserId(userId);
        api.cancelUser(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("3333", "onNext:下线设备成功 222"+e.toString());

                        userView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo msg) {
//                        if       (msg.getSuccess() == 0) {
////                            loginView.showErrorMessage("上传设备成功3");
//                            Log.i("3333", "onNext:下线设备成功3 ");
//                        }
//                        else
//                            Log.i("3333", "onNext:下线设备成功4 ");

//                        loginView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }

    @Override
    public void getUserFans(String userId) {
        UserIdParam param = new UserIdParam();
        param.setUserId(userInfo.getSid());
        UserApi api = ApiClient.create(UserApi.class);
        api.getfansCount(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<UserFansTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MessageTo<UserFansTo> msg) {

                          userView.submitDataSuccess(msg.getData());

                    }
                }
        );
    }
}
