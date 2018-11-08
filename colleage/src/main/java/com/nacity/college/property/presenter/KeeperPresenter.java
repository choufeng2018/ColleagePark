package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.ServiceTypeParam;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.property.model.KeeperModel;
import com.nacity.college.property.view.KeeperView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/7.
 **/

public class KeeperPresenter extends BasePresenter implements KeeperModel {
    private KeeperView keeperView;

    public KeeperPresenter(KeeperView keeperView) {
        this.keeperView = keeperView;
    }

    @Override
    public void getKeeperCategory() {
        PropertyApi api = ApiClient.create(PropertyApi.class);
        ServiceTypeParam param = new ServiceTypeParam();
        param.setCategoryType("9098ED29-072D-4653-A37D-3C2F6DF80861");
        param.setLoginUserId(apartmentInfo.getSid());
        api.findTypeByCategory(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<ServiceTypeTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        keeperView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<ServiceTypeTo>> msg) {
                        if (msg.getSuccess() == 0)
                            keeperView.getServiceTypeSuccess(msg.getData());
                        else
                            keeperView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }
}
