package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.DecorateParam;
import com.college.common_libs.domain.property.RoomNumTo;
import com.college.common_libs.domain.property.RoomParam;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.property.model.DecorationModel;
import com.nacity.college.property.view.DecorationView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  Created by usb on 2017/10/17.
 */

public class DecorationPresenter extends BasePresenter implements DecorationModel {
    private DecorationView decorationView;

    public DecorationPresenter(DecorationView decorationView) {
        this.decorationView = decorationView;
    }
    @Override
    public void submitDecoration(String userName, String userTel, String companyName, String roomId, String havePicture, String explains) {
        PropertyApi api = ApiClient.create(PropertyApi.class);
        DecorateParam param=new DecorateParam();
        param.setGardenId(apartmentInfo.getSid());
        param.setCreateUserId(userInfo.getSid());
        param.setUserName(userName);
        param.setUserTel(userTel);
        param.setCompanyName(companyName);
        param.setRoomId(roomId);
        param.setHavePicture(havePicture);
        param.setExplains(explains);
        param.setApplyStatus("1");
        api.saveDecorateApply(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        decorationView.loadError(e);

                    }

                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getSuccess() == 0) {
                            decorationView.submit();

//                            if (msg.getData() != null) {
//                            }
                        }
                        else
                            decorationView.showErrorMessage(msg.getMessage());

                    }
                }
        );

    }

    @Override
    public void getRoomNo() {
        PropertyApi api = ApiClient.create(PropertyApi.class);
        RoomParam param=new RoomParam();
        param.setGardenId(apartmentInfo.getSid());
        param.setUserId(userInfo.getSid());
        api.getRoomNum(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<List<RoomNumTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        decorationView.loadError(e);

                    }

                    @Override
                    public void onNext(MessageTo<List<RoomNumTo>> msg) {
                        if (msg.getSuccess() == 0) {
                            int a = msg.getData().size();
                            String[] vars= new String[a];
                            String[] roomId= new String[a];
                            for (int i = 0; i < msg.getData().size(); i++) {
                                RoomNumTo roomNumTo = msg.getData().get(i);
                                vars[i] = roomNumTo.getRoomNoDesc();
                                roomId[i] = roomNumTo.getId();
                            }
                            decorationView.getRoomNum(vars,roomId);
                        }
                        else
                            decorationView.showErrorMessage(msg.getMessage());

                    }
                }
        );

    }
}
