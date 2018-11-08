package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.DecorateApplyTo;
import com.college.common_libs.domain.property.DecorateDetailParam;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.property.model.DecorateDetailModel;
import com.nacity.college.property.view.DecorationDetailView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by usb on 2017/10/18.
 */

public class DecorateDetailPresenter extends BasePresenter implements DecorateDetailModel {
    private DecorationDetailView decorationDetailView;

    public DecorateDetailPresenter(DecorationDetailView decorationDetailView) {
        this.decorationDetailView = decorationDetailView;
    }
    @Override
    public void getDecorationDetail(String decorationId) {
        PropertyApi api = ApiClient.create(PropertyApi.class);
        DecorateDetailParam param=new DecorateDetailParam();
        param.setDecorateId(decorationId);
        api.findDecorateApplyById(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<DecorateApplyTo>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        decorationDetailView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<DecorateApplyTo> msg) {
                        if (msg.getSuccess() == 0) {
                            decorationDetailView.getDecorationDetail(msg.getData());
                        }
                        else
                            decorationDetailView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }
}
