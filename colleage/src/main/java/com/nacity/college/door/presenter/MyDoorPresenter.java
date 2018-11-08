package com.nacity.college.door.presenter;


import com.college.common_libs.api.DoorApi;
import com.college.common_libs.api.DoorApiClient;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.door.ApplyDoorRecordTo;
import com.college.common_libs.domain.door.DoorApplyRecordParam;
import com.college.common_libs.domain.door.DoorListParam;
import com.college.common_libs.domain.door.OpenDoorListTo;
import com.college.common_libs.domain.door.OpenDoorTo;
import com.college.common_libs.domain.door.OpenDoorTypeTo;
import com.nacity.college.base.BaseView;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2018/1/27.
 **/

public class MyDoorPresenter {
    private BaseView baseView;

    public MyDoorPresenter(BaseView baseView) {
        this.baseView = baseView;
    }

    /**
     *拥有的门禁列表
     */
    public void getMyDoorList(String apartmentSid, String userSid) {
        DoorListParam param = new DoorListParam();
        param.setApartmentSid(apartmentSid);
        param.setOwnerSid(userSid);

        DoorApiClient.create(DoorApi.class).getDoorList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new Observer<MessageTo<OpenDoorTo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                baseView.loadError(e);
            }

            @Override
            public void onNext(MessageTo<OpenDoorTo> msg) {

             if (msg!=null){
                 if (msg.getSuccess()==0) {
                     if (msg.getData()==null){
                         baseView.getDataSuccess(new ArrayList<OpenDoorListTo>());
                     }else
                     baseView.getDataSuccess(msg.getData().getOpenDoorDetailVos());
                 }
                 else
                     baseView.showMessage(msg.getMessage());
             }
            }
        });

    }

    /**
     *申请门禁记录
     */
    public void getApplyDoorList(String apartmentSid, String userSid) {
        DoorApplyRecordParam param = new DoorApplyRecordParam();
        param.setApartmentSid(apartmentSid);
        param.setOwnerSid(userSid);
        System.out.println(param+"param========");
        DoorApiClient.create(DoorApi.class).getApplyRecord(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new Observer<MessageTo<List<ApplyDoorRecordTo>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                baseView.loadError(e);
            }

            @Override
            public void onNext(MessageTo<List<ApplyDoorRecordTo>> msg) {

                if (msg!=null){
                    if (msg.getSuccess()==0)
                        baseView.getDataSuccess(msg.getData());
                    else
                        baseView.showMessage(msg.getMessage());
                }
            }
        });

    }

    /**
     *是否显示远程开门
     */
    public void displayRemoteDoor(String apartmentSid, String userSid) {
        DoorListParam param = new DoorListParam();
        param.setApartmentSid(apartmentSid);
        param.setOwnerSid(userSid);

        DoorApiClient.create(DoorApi.class).openDoorType(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new Observer<MessageTo<OpenDoorTypeTo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                baseView.loadError(e);
            }

            @Override
            public void onNext(MessageTo<OpenDoorTypeTo> msg) {

                if (msg!=null){

                    if (msg.getData() != null && msg.getData().getOpenSupplier() != null && msg.getData().getOpenSupplier().contains(1))
                        baseView.getDataSuccess(true);
                    else
                        baseView.getDataSuccess(false);
                }
            }
        });

    }
}
