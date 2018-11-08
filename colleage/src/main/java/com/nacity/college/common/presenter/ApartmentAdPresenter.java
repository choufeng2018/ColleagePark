package com.nacity.college.common.presenter;

import com.college.common_libs.api.ApartmentApi;
import com.college.common_libs.api.ApiClient;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.apartment.AdvertiseParam;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.nacity.college.base.impl.SuccessCallback;
import com.nacity.college.common.model.ApartmentAdModel;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/19.
 **/

public class ApartmentAdPresenter  implements ApartmentAdModel {
    @Override
    public void getApartmentAd(int type, String parkName, SuccessCallback<List<AdvertiseTo>> callback) {
        ApartmentApi api = ApiClient.create(ApartmentApi.class);
        AdvertiseParam param = new AdvertiseParam();
        param.setAdType(type);
        param.setGardenId(parkName);
        api.getParkAd(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<AdvertiseTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(MessageTo<List<AdvertiseTo>> msg) {
                        if (msg.getSuccess() == 0)
                            callback.adSuccess(msg.getData());

                    }
                }
        );
    }
}
