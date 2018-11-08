package com.nacity.college.myself.presenter;

import android.text.TextUtils;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.UserApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.FeedbackParam;
import com.college.common_libs.domain.property.FeedbackTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.Constant;
import com.nacity.college.myself.model.FeedBackModel;
import com.nacity.college.myself.view.FeedBackView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/7/6.
 **/

public class FeedBackPresenter extends BasePresenter implements FeedBackModel {

    private FeedBackView backView;
    public FeedBackPresenter(FeedBackView backView){
        this.backView=backView;
    }
    @Override
    public void feedback(String content, String contact) {
        if (TextUtils.isEmpty(content)){
            backView.showErrorMessage(Constant.FEEDBACK_CONTENT_NO_EMPTY);
            return;
        }
        FeedbackParam param=new FeedbackParam();
        param.setGardenId(apartmentInfo.getSid());
        param.setFeedbackUserId(userInfo.getSid());
        param.setFeedbackContent(content);
        param.setFeedbackType(3);
        param.setFeedbackContact(contact);
        UserApi api= ApiClient.create(UserApi.class);
        api.addFeedbackInfo(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<FeedbackTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        backView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<FeedbackTo> msg) {
                        if (msg.getSuccess()==0)
                            backView.feedbackSuccess();
                        else
                            backView.showErrorMessage(msg.getMessage());



                    }
                }
        );
    }
}
