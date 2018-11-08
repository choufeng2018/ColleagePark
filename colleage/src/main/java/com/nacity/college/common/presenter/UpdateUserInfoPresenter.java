package com.nacity.college.common.presenter;

import android.text.TextUtils;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.UserApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.user.UpdateUserParam;
import com.college.common_libs.domain.user.UserInfoTo;
import com.nacity.college.MainApp;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.impl.LoadCallback;
import com.nacity.college.common.model.UpdateUserInfoModel;
import com.nacity.college.common.model.UploadImageModel;
import com.nacity.college.login.view.UpdateUserView;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/20.
 **/

public class UpdateUserInfoPresenter extends BasePresenter implements UpdateUserInfoModel, LoadCallback<String> {
    private UpdateUserView<UserInfoTo> updateView;

    private ArrayList<String> imageList = new ArrayList<>();

    public UpdateUserInfoPresenter(UpdateUserView<UserInfoTo> updateView) {
        this.updateView = updateView;

    }

    @Override
    public void uploadHeadImage(String imagePath, int photoNumber) {
        imageList.clear();
        imageList.add(imagePath);
        UploadImageModel model = new UploadImagePresenter();
        model.uploadImage(imageList, 1, this);
    }


    @Override
    public void updateUserInfo(String userPic, String nickName, String realName, String birthday, String sex, String newParkId) {
        UserApi api = ApiClient.create(UserApi.class);
        UpdateUserParam param = new UpdateUserParam();
        param.setNickname(TextUtils.isEmpty(nickName)?userInfo.getUserInfoTo().getNickname():nickName);
        param.setRealName(TextUtils.isEmpty(realName)?userInfo.getUserInfoTo().getRealName():realName);
        param.setSex(TextUtils.isEmpty(sex)?userInfo.getUserInfoTo().getSex():sex);
        param.setUserId(userInfo.getSid());
        param.setUpdateGardenId(TextUtils.isEmpty(newParkId)?userInfo.getUserInfoTo().getUpdateGardenId():newParkId);
        param.setUserBirth(TextUtils.isEmpty(birthday)?userInfo.getUserInfoTo().getBirthday():birthday);
        param.setUserPic(TextUtils.isEmpty(userPic)? MainApp.getImagePath(userInfo.getUserInfoTo().getUserPic()):(MainApp.getImagePath(userPic)));
        api.updateUserInfo(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<UserInfoTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        updateView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<UserInfoTo> msg) {
                        if (msg.getSuccess() == 0)
                            updateView.getDataSuccess(msg.getData());
                        else
                            updateView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }

    /**
     * 上传图片返回
     */
    @Override
    public void success(String s) {
        updateView.upLoadImageSuccess(s);
    }

    @Override
    public void fail(Throwable e) {
        updateView.loadError(e);
    }

    @Override
    public void showErrorMessage(String message) {
        updateView.showErrorMessage(message);
    }
}
