package com.nacity.college.rent.interactor.Impl;


import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.HouseRentApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.rent.HouseListParam;
import com.college.common_libs.domain.rent.HouseRentTo;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.rent.interactor.GreenUnLimitInteractor;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/6/23.
 **/

public class GreenUnLimitInteractorImpl implements GreenUnLimitInteractor {
    @Override
    public void getSaleHouseList(HouseListParam param, LoadingCallback<List<HouseRentTo>> loadingCallback) {

        HouseRentApi api= ApiClient.create(HouseRentApi.class);
        api.findAllHouseInfo(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MessageTo<List<HouseRentTo>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e+"eeeeeeeeee");
            loadingCallback.error(e);
            }

            @Override
            public void onNext(MessageTo<List<HouseRentTo>> msg) {
             if (msg.getSuccess()==0)
                    loadingCallback.success(msg.getData());
                else
                    loadingCallback.message(msg.getMessage());
            }
        });
    }
}
