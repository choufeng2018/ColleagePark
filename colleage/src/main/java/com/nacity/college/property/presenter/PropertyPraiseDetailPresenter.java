package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.FeedbackTo;
import com.college.common_libs.domain.property.PraiseDetailParam;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.BaseView;
import com.nacity.college.property.model.PropertyPraiseDetailModel;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/20.
 **/

public class PropertyPraiseDetailPresenter extends BasePresenter implements PropertyPraiseDetailModel {

    private BaseView<FeedbackTo> detailView;

    public PropertyPraiseDetailPresenter(BaseView<FeedbackTo> detailView) {
        this.detailView = detailView;
    }


    @Override
    public void getPraiseDetail(String feedbackId) {
        PropertyApi api = ApiClient.create(PropertyApi.class);
        PraiseDetailParam param = new PraiseDetailParam();
        param.setFeedbackId(feedbackId);
        api.getPraiseDetail(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<FeedbackTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        detailView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<FeedbackTo> msg) {
                        if (msg.getSuccess() == 0)
                            detailView.getDataSuccess(msg.getData());
                        else
                            detailView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }
}
