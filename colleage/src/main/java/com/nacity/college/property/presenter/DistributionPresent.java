package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.property.ServiceTypeParam;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.impl.SuccessCallback;
import com.nacity.college.common.model.ApartmentAdModel;
import com.nacity.college.common.presenter.ApartmentAdPresenter;
import com.nacity.college.property.model.DistributionModel;
import com.nacity.college.property.view.DistributionView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by usb on 2017/10/26.
 */

public class DistributionPresent  extends BasePresenter implements DistributionModel,SuccessCallback<List<AdvertiseTo>> {
    private DistributionView distributionView;

    public DistributionPresent(DistributionView distributionView) {
        ApartmentAdModel model=new ApartmentAdPresenter();
        model.getApartmentAd(8,apartmentInfo.getSid(),this);
        this.distributionView = distributionView;
    }

    @Override
    public void getWaterCategory() {
        PropertyApi api = ApiClient.create(PropertyApi.class);
        ServiceTypeParam param = new ServiceTypeParam();
        param.setCategoryType("3");
        param.setGardenId(apartmentInfo.getSid());
        param.setLoginUserId(userInfo.getSid());
        api.findTypeByCategory(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<ServiceTypeTo>>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        distributionView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<ServiceTypeTo>> msg) {
                        if (msg.getSuccess() == 0)
                            distributionView.getServiceTypeSuccess(msg.getData());
                        else
                            distributionView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }

    @Override
    public void adSuccess(List<AdvertiseTo> advertiseTos) {
        distributionView.getAdSuccess(advertiseTos);

    }
}
