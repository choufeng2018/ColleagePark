package com.nacity.college.property.presenter;

import android.text.TextUtils;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.FeedbackParam;
import com.college.common_libs.domain.property.FeedbackTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.impl.LoadCallback;
import com.nacity.college.common.model.UploadImageModel;
import com.nacity.college.common.presenter.UploadImagePresenter;
import com.nacity.college.property.model.PropertyPraiseModel;
import com.nacity.college.property.view.PropertyPraiseView;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/6.
 **/

public class PropertyPraisePresenter extends BasePresenter implements PropertyPraiseModel, LoadCallback<String> {
    private PropertyPraiseView praiseView;
    private final UploadImageModel imageModel;

    public PropertyPraisePresenter(PropertyPraiseView praiseView) {
        this.praiseView = praiseView;
        imageModel = new UploadImagePresenter();

    }

    @Override
    public void uploadImage(ArrayList<String> imagePathList, int photoNumber) {
        imageModel.uploadImage(imagePathList, 4, this);
    }

    @Override
    public void submit(String imagePath, int praiseType, String contact, String praiseContent) {
        PropertyApi api = ApiClient.create(PropertyApi.class);
        FeedbackParam param = new FeedbackParam();
        param.setGardenId(apartmentInfo.getSid());
        param.setFeedbackContent(praiseContent);
        param.setFeedbackUserId(userInfo.getSid());
        param.setFeedbackType(praiseType);
        param.setFeedbackImages(imagePath);
        param.setFeedbackContact(TextUtils.isEmpty(contact)?userInfo.getUserInfoTo().getUserMobile():contact);
        api.addFeedbackInfo(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<FeedbackTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        praiseView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<FeedbackTo> msg) {
                        if(msg.getSuccess()==0)
                            praiseView.submitSuccess();
                        else
                            praiseView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }

    @Override
    public void success(String s) {
        praiseView.uploadImageSuccess(s);
    }

    @Override
    public void fail(Throwable e) {
        praiseView.loadError(e);
    }

    @Override
    public void showErrorMessage(String message) {
        praiseView.showErrorMessage(message);
    }
}
