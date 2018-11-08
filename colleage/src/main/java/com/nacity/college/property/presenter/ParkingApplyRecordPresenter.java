package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.ParkingApplyListParam;
import com.college.common_libs.domain.property.ParkingApplyListTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.property.model.ParkingApplyRecordModel;
import com.nacity.college.property.view.ParkingApplyRecordView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  Created by usb on 2017/10/23.
 */

public class ParkingApplyRecordPresenter extends BasePresenter implements ParkingApplyRecordModel {
    private ParkingApplyRecordView<ParkingApplyListTo> recordView;

    public ParkingApplyRecordPresenter(ParkingApplyRecordView<ParkingApplyListTo> recordView) {
        this.recordView = recordView;

    }

    @Override
    public void getParkingApplyRecord(int pageIndex) {
        PropertyApi api = ApiClient.create(PropertyApi.class);
        ParkingApplyListParam param=new ParkingApplyListParam();
        param.setUserId(userInfo.getSid());
        param.setNextPage(pageIndex+1);
        param.setLimit(20);
        param.setGardenId(apartmentInfo.getSid());
        api.findParkingApplyList(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<List<ParkingApplyListTo>>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        recordView.loadError(e);
                    }
                    @Override
                    public void onNext(MessageTo<List<ParkingApplyListTo>> msg) {
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