package com.nacity.college.main.presenter;

import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.impl.LoadCallback;
import com.nacity.college.common.model.UpdateUserInfoModel;
import com.nacity.college.common.model.UploadImageModel;
import com.nacity.college.common.presenter.UpdateUserInfoPresenter;
import com.nacity.college.common.presenter.UploadImagePresenter;
import com.nacity.college.main.model.PersonCenterModel;
import com.nacity.college.main.view.PersonCenterView;

import java.util.ArrayList;

/**
 * Created by xzz on 2017/9/11.
 **/

public class PersonCenterPresenter extends BasePresenter implements PersonCenterModel, LoadCallback<String> {
    private PersonCenterView personView;
    private final UploadImageModel model;

    public PersonCenterPresenter(PersonCenterView personView) {
        this.personView = personView;
        model = new UploadImagePresenter();
        UpdateUserInfoModel userInfoModel=new UpdateUserInfoPresenter(null);
    }

    @Override
    public void uploadHeadImage(String imagePath, int photoNumber) {
        ArrayList<String> imageList = new ArrayList<>();
        imageList.add(imagePath);
        model.uploadImage(imageList, 1, this);
    }

    @Override
    public void changeUserName(String newUsername) {

    }

    //上传图片接口回调
    @Override
    public void success(String s) {
        personView.uploadHeadImageSuccess(s);
    }

    @Override
    public void fail(Throwable e) {
        personView.loadError(e);
    }

    @Override
    public void showErrorMessage(String message) {
      personView.showErrorMessage(message);
    }
}
