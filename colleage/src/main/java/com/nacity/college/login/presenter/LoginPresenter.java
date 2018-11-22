package com.nacity.college.login.presenter;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.LoginApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.login.DeviceParam;
import com.college.common_libs.domain.login.LoginParam;
import com.college.common_libs.domain.login.RegisterParam;
import com.college.common_libs.domain.login.VerificationCodeParam;
import com.college.common_libs.domain.user.UserInfoTo;
import com.nacity.college.MainApp;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.Constant;
import com.nacity.college.base.utils.AppUtil;
import com.nacity.college.base.utils.CheckPhoneUtil;
import com.nacity.college.login.model.LoginModel;
import com.nacity.college.login.view.LoginView;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/17.
 **/

public class LoginPresenter extends BasePresenter implements LoginModel {
    private LoginView loginView;
    private LoginApi api = ApiClient.create(LoginApi.class);
    private EventBus eventBus = EventBus.getDefault();

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    /**
     * 获取注册验证的验证码
     */
    @Override
    public void getVerificationCode(String type, String phoneNumber, TextView getVerification) {
        if (CheckPhoneUtil.checkPhoneNumber(phoneNumber, MainApp.mContext)){
            loginView.loadingDismissPhone();
            return;
        }

        VerificationCodeParam param = new VerificationCodeParam();
        param.setUserPhone(phoneNumber);
        param.setValidcodeType(type);
        api.getVerificationCode(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getSuccess() == 0) {
                            loginView.getVerificationCodeSuccess(msg.getTag()+"");
                            verificationCountTime(type,getVerification);
                        } else
                            loginView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }
//更新设备信息
    @Override
    public void upLoadDevice(String userId) {
        DeviceParam param = new DeviceParam();
        param.setDeviceApp("10");
        param.setUserId(userId);
        param.setDeviceType("android");
        param.setRegistrationId(JPushInterface.getRegistrationID(MainApp.mContext));
        Log.i("3333", "upLoadDevice: " + param.toString());
        Log.i("3333", "upLoadDevice: " + JPushInterface.getRegistrationID(MainApp.mContext));
        api.updateOwnerDeviceInfo(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo>() {
                    @Override
                    public void onCompleted() {
                        Log.i("3333", "onCompleted: ");

                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("3333", "onNext:上传设备成功 2"+e.toString());

                        loginView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo msg) {
                        if       (msg.getSuccess() == 0) {
//                            loginView.showErrorMessage("");
                            Log.i("3333", "onNext:上传设备成功3 ");
                        }
                        else
                            Log.i("3333", "onNext:上传设备成功4 ");

//                        loginView.showErrorMessage(msg.getMessage());
                }
                }
        );
    }

    /**
     * 园区注册
     */

    @Override
    public void parkRegister(String cityName, String parkSid, String phoneNumber, String verificationCode) {
//        if (TextUtils.isEmpty(cityName)) {
//            loginView.showErrorMessage(Constant.PLEASE_SELECT_CITY);
//            return;
//        }
         if (TextUtils.isEmpty(parkSid)) {
            loginView.showErrorMessage(Constant.PLEASE_SELECT_APARTMENT);
            return;
        } else if (CheckPhoneUtil.checkPhoneNumber(phoneNumber, MainApp.mContext)){
            loginView.loadingDismissPhone();
            return;
        }

        else if (TextUtils.isEmpty(verificationCode)) {
            loginView.showErrorMessage(Constant.PLEASE_INPUT_VERIFICATION_CODE);
            return;
        }
        RegisterParam param = new RegisterParam();
        param.setGardenId(parkSid);
        param.setUserMobile(phoneNumber);
        param.setValidCode(verificationCode);
        param.setRegistrationId(JPushInterface.getRegistrationID(MainApp.mContext));
        api.register(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<UserInfoTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<UserInfoTo> msg) {
                        if (msg.getSuccess() == 0) {
                            userInfo.updateUser(msg.getData());
                            loginView.registerSuccess();
                        } else
                            loginView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }

    /**
     * 园区登录
     */

    @Override
    public void login(String phoneNumber, String verificationCode) {
        if (CheckPhoneUtil.checkPhoneNumber(phoneNumber, MainApp.mContext)){
            loginView.loadingDismissPhone();
            return;}
        else if (TextUtils.isEmpty(verificationCode)) {
            loginView.showErrorMessage(Constant.PLEASE_INPUT_VERIFICATION_CODE);
            return;
        }
        LoginParam param = new LoginParam();
        param.setRegistrationId(JPushInterface.getRegistrationID(MainApp.mContext));
        param.setValidCode(verificationCode);
        param.setUserMobile(phoneNumber);
        param.setAppVersion(AppUtil.getVersionName(MainApp.mContext));
        api.login(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<UserInfoTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<UserInfoTo> msg) {
                        if (msg.getSuccess() == 0) {
                            loginView.loginSuccess(msg.getData());


                        }
                        else
                            loginView.showErrorMessage(msg.getMessage());
                    }
                }

        );

    }

    //验证码按钮倒计时
    private void verificationCountTime(String type,TextView getVerification) {
        getVerification.setEnabled(false);
        new Thread(() -> {
            for (int i = 60; i >= 0; i--) {
                SystemClock.sleep(1000);

                    loginView.setCountTime(i);



            }
        }).start();
    }
}
