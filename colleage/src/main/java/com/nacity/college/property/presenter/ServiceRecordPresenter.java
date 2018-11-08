package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.ServiceMainTo;
import com.college.common_libs.domain.property.ServiceRecordParam;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.property.model.ServiceRecordModel;
import com.nacity.college.property.view.ServiceRecordView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/5.
 **/

public class ServiceRecordPresenter extends BasePresenter implements ServiceRecordModel {

    private ServiceRecordView<ServiceMainTo> recordView;

    public ServiceRecordPresenter(ServiceRecordView<ServiceMainTo> recordView) {
        this.recordView = recordView;

    }

    @Override
    public void getServiceRecord(int pageIndex, String categorySid) {
        PropertyApi api = ApiClient.create(PropertyApi.class);
        ServiceRecordParam param=new ServiceRecordParam();
        param.setLoginUserId(userInfo.getSid());
        param.setPageIndex(pageIndex+1);
        param.setCategoryType(categorySid);
        param.setGardenId(apartmentInfo.getSid());
        api.findServiceInfoByApartmentAndOwner(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<List<ServiceMainTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        recordView.loadError(e);

                    }

                    @Override
                    public void onNext(MessageTo<List<ServiceMainTo>> msg) {
                        if (msg.getSuccess() == 0) {
                            if (msg.getData() != null) {

                                recordView.refreshRecycleView(msg.getData());
                            }
                        } else
                            recordView.showMessage(msg.getMessage());

                    }
                }
        );

    }
}
