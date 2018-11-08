package com.nacity.college.door.presenter;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.college.common_libs.api.DoorApi;
import com.college.common_libs.api.DoorApiClient;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.door.DoorListParam;
import com.college.common_libs.domain.door.OpenDoorListTo;
import com.college.common_libs.domain.door.OpenDoorLogParam;
import com.college.common_libs.domain.door.OpenDoorTo;
import com.college.common_libs.domain.door.OpenDoorTypeTo;
import com.nacity.college.base.utils.ACache;
import com.nacity.college.door.view.OpenDoorView;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2018/1/27.
 **/

public class OpenDoorPresenter {
    private OpenDoorView openDoorView;

    public OpenDoorPresenter(OpenDoorView openDoorView) {
        this.openDoorView = openDoorView;
    }


    public void getOpenDoorType(String apartmentSid, String userSid) {
        DoorListParam param = new DoorListParam();
        param.setApartmentSid(apartmentSid);
        param.setOwnerSid(userSid);
        DoorApiClient.create(DoorApi.class).openDoorType(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<OpenDoorTypeTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e+"e=======");
                        openDoorView.loadError(e);
                        if (new ACache().getAsString(userSid + "OpenDoorType") != null){
                            OpenDoorTypeTo doorTypeTo = JSON.parseObject(new ACache().getAsString(userSid + "OpenDoorType"),OpenDoorTypeTo.class);
                            openDoorView.getApartmentDoorStatueSuccess(doorTypeTo);
                        }



                    }

                    @Override
                    public void onNext(MessageTo<OpenDoorTypeTo> msg) {
                        System.out.println(msg+"===========");
                        if (msg != null) {
                            if (msg.getSuccess() == 0) {
                                openDoorView.getApartmentDoorStatueSuccess(msg.getData());
                                if (msg.getData()!=null)
                                new ACache().put(userSid + "OpenDoorType", JSON.toJSONString(msg.getData()));
                            }
                            else
                                openDoorView.showMessage(msg.getMessage());
                        }
                    }
                }
        );

    }

    /**
     * 拥有的门禁列表
     */
    public void getOpenDoorList(String apartmentSid, String userSid) {
        DoorListParam param = new DoorListParam();
        param.setApartmentSid(apartmentSid);
        param.setOwnerSid(userSid);

        DoorApiClient.create(DoorApi.class).getDoorList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new Observer<MessageTo<OpenDoorTo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e+"e************");
                openDoorView.loadError(e);
                if (new ACache().getAsString(userSid + "OpenDoorList")!=null){
                    openDoorView.getDoorListSuccess(JSON.parseArray(new ACache().getAsString(userSid + "OpenDoorList"), OpenDoorListTo.class));
                }
            }

            @Override
            public void onNext(MessageTo<OpenDoorTo> msg) {
                System.out.println(msg+"msg*************");
                if (msg != null) {
                    if (msg.getSuccess() == 0) {
                        if (msg.getData()==null){
                            openDoorView.toApplyDoor();
                            return;
                        }
                        openDoorView.getDoorListSuccess(msg.getData().getOpenDoorDetailVos());
                        if (msg.getData()!=null&&msg.getData().getOpenDoorDetailVos()!=null){
                            new ACache().put(userSid + "OpenDoorList", JSON.toJSONString(msg.getData().getOpenDoorDetailVos()));
                        }
                    }
                    else
                        openDoorView.showMessage(msg.getMessage());
                }
            }
        });

    }


    /**
     * 发送开门日志
     */
    public void sendOpenDoorLog(String apartmentSid, String userSid, String type, String supplyId, String doorId, int success,String openTime,String desc) {
        OpenDoorLogParam param = new OpenDoorLogParam();
        param.setApartmentSid(apartmentSid);
        param.setOwnerSid(userSid);
        param.setLogType(type);
        param.setSupplierId(supplyId);
        param.setOpenContentDesc(TextUtils.isEmpty(desc)?"联网开门":desc);
        param.setDoorId(doorId);
        param.setOpenDoorTime(openTime);
        param.setIsOpenSuccess(success);
        DoorApiClient.create(DoorApi.class).sendOpenDoorLog(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new Observer<MessageTo<Boolean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e+"eeeeeeeeeeee");
                openDoorView.loadError(e);
                param.setOpenContentDesc("离线开门");
                List<OpenDoorLogParam> logParamList;
                if (new ACache().getAsString(userSid + "OpenDoorLog") != null)
                    logParamList = JSON.parseArray(new ACache().getAsString(userSid + "OpenDoorLog"), OpenDoorLogParam.class);
                else
                    logParamList = new ArrayList<>();
                for (OpenDoorLogParam logParam : logParamList) {
                    if (logParam.getOpenDoorTime().equals(param.getOpenDoorTime())) {
                        return;
                    }
                }
                System.out.println(e+"eeeeeeeeeee");
                logParamList.add(param);
                new ACache().put(userSid + "OpenDoorLog", JSON.toJSONString(logParamList));

            }

            @Override
            public void onNext(MessageTo<Boolean> msg) {
                System.out.println(msg+"成功了");
                if (msg.getSuccess() == 0) {
                    if (new ACache().getAsString(userSid + "OpenDoorLog") != null) {
                        List<OpenDoorLogParam> logParamList = JSON.parseArray(new ACache().getAsString(userSid + "OpenDoorLog"), OpenDoorLogParam.class);
                        for (int i = 0; i < logParamList.size(); i++) {
                            if (logParamList.get(i).getOpenDoorTime().equals(param.getOpenDoorTime())) {
                                logParamList.remove(i);
                                break;
                            }
                        }
                        new ACache().put(userSid + "OpenDoorLog", JSON.toJSONString(logParamList));
                    }
                }
            }
        });

    }

    /**
     * 在有网的时候发送缓存日志
     */
    public void sendlogInNet(String userSid) {
        if (new ACache().getAsString(userSid + "OpenDoorLog") != null) {
            List<OpenDoorLogParam> logParamList = JSON.parseArray(new ACache().getAsString(userSid + "OpenDoorLog"), OpenDoorLogParam.class);
            System.out.println(logParamList+"logParam====");
            for (OpenDoorLogParam param : logParamList) {
                sendOpenDoorLog(param.getApartmentSid(), param.getOwnerSid(), param.getLogType(), param.getSupplierId(), param.getDoorId(), param.getIsOpenSuccess(),param.getOpenDoorTime(),param.getOpenContentDesc());
            }
        }
    }

}
