package com.nacity.college.door.presenter;

import com.college.common_libs.api.DoorApi;
import com.college.common_libs.api.DoorApiClient;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.door.ApplyDoorParam;
import com.college.common_libs.domain.door.CanApplyDoorTo;
import com.college.common_libs.domain.door.DoorListParam;
import com.nacity.college.base.BaseView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2018/1/27.
 **/

public class ApplyDoorPresenter {

    private BaseView baseView;

    public ApplyDoorPresenter(BaseView baseView) {
        this.baseView = baseView;
    }
    /**
     * 获取可申请的门禁
     */

    public void getCanApplyDoor(String apartmentSid, String userSid) {
        DoorListParam param = new DoorListParam();
        param.setApartmentSid(apartmentSid);
        param.setOwnerSid(userSid);
        DoorApiClient.create(DoorApi.class).getApplyDoorList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new Observer<MessageTo<List<CanApplyDoorTo>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                baseView.loadError(e);
            }

            @Override
            public void onNext(MessageTo<List<CanApplyDoorTo>> msg) {
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
     * 提交门禁申请
     */

    public void submitApplyDoor(String apartmentSid, String userSid,String doorSid) {
        ApplyDoorParam param = new ApplyDoorParam();
        param.setApartmentSid(apartmentSid);
        param.setOwnerSid(userSid);
        param.setDoorIds(doorSid);
        DoorApiClient.create(DoorApi.class).applyOpenDoor(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new Observer<MessageTo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                baseView.loadError(e);
            }

            @Override
            public void onNext(MessageTo msg) {
                if (msg!=null){
                    if (msg.getSuccess()==0)
                        baseView.submitDataSuccess(msg.getData());
                    else
                        baseView.showMessage(msg.getMessage());
                }
            }
        });
    }
}
