package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.CancelReportParam;
import com.college.common_libs.domain.property.ServiceIdParam;
import com.college.common_libs.domain.property.ServiceMainTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.property.model.ServiceDetailModel;
import com.nacity.college.property.view.ServiceDetailView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/10/11.
 **/

public class ServiceDetailPresenter extends BasePresenter implements ServiceDetailModel {

    private ServiceDetailView detailView;
    private PropertyApi api = ApiClient.create(PropertyApi.class);

    public ServiceDetailPresenter(ServiceDetailView detailView) {
        this.detailView = detailView;
    }

    @Override
    public void getServiceDetail(String serviceId) {
        ServiceIdParam param = new ServiceIdParam();
        param.setServiceId(serviceId);
        api.getServiceDetail(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<ServiceMainTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        detailView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<ServiceMainTo> msg) {
                        if (msg.getSuccess() == 0) {

                                detailView.getServiceDetailSuccess(msg.getData());
                        } else
                            detailView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }

    @Override
    public void cancelReport(String serviceId) {
        CancelReportParam param = new CancelReportParam();
        param.setServiceId(serviceId);
        param.setLoginUserId(userInfo.getUserInfoTo().getUserId());
        api.cancelReport(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<Boolean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        detailView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<Boolean> msg) {
                        if (msg.getSuccess() == 0)
                            detailView.cancelReportSuccess();
                        else
                            detailView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }
}
