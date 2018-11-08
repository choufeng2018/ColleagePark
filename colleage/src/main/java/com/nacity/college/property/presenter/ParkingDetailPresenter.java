package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.ParkingApplyTo;
import com.college.common_libs.domain.property.ParkingDetailParam;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.property.model.ParkingDetailModel;
import com.nacity.college.property.view.ParkingDetailView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  Created by usb on 2017/10/23.
 */

public class ParkingDetailPresenter extends BasePresenter implements ParkingDetailModel {
    private ParkingDetailView parkingDetailView;

    public ParkingDetailPresenter(ParkingDetailView parkingDetailView) {
        this.parkingDetailView = parkingDetailView;
    }
    @Override
    public void getParkingDetail(String parkId) {
        PropertyApi api = ApiClient.create(PropertyApi.class);
        ParkingDetailParam param=new ParkingDetailParam();
        param.setParkId(parkId);
        api.getApplyParkById(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<ParkingApplyTo>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        parkingDetailView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<ParkingApplyTo> msg) {
                        if (msg.getSuccess() == 0) {
                            parkingDetailView.getParkingDetail(msg.getData());
                        }
                        else
                            parkingDetailView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }


}
