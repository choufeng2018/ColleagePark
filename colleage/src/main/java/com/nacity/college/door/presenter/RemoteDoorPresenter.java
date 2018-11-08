package com.nacity.college.door.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.college.common_libs.api.DoorApi;
import com.college.common_libs.api.DoorApiClient;
import com.college.common_libs.api.RemoteOpenDoorApiClient;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.door.DoorListParam;
import com.college.common_libs.domain.door.OpenDoorLogParam;
import com.college.common_libs.domain.door.OpenDoorTo;
import com.college.common_libs.domain.door.RemoteDoorMessageTo;
import com.college.common_libs.domain.door.RemoteOpenDoorParam;
import com.nacity.college.base.BaseView;
import com.nacity.college.base.Constant;
import com.nacity.college.base.utils.ACache;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2018/2/5.
 **/

public class RemoteDoorPresenter {
   private BaseView baseView;
   public RemoteDoorPresenter(BaseView baseView){
       this.baseView=baseView;
   }
    public void getRemoteOpenDoorList(String userSid,String apartmentSid ){
        DoorListParam param = new DoorListParam();
        param.setOwnerSid(userSid);
        param.setApartmentSid(apartmentSid);
        DoorApiClient.create(DoorApi.class).getRemoteDoorList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new Observer<MessageTo<OpenDoorTo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            baseView.loadError(e);
            }

            @Override
            public void onNext(MessageTo<OpenDoorTo> msg) {
            if (msg.getSuccess()==0){
               baseView.getDataSuccess(msg.getData().getOpenDoorDetailVos());
            }else
                baseView.showMessage(msg.getMessage());
            }
        });
    }

    public void remoteOpenDoor(String key,String doorSid){
        RemoteOpenDoorParam param = new RemoteOpenDoorParam();
        RemoteOpenDoorParam.HeaderBean headerBean = new RemoteOpenDoorParam.HeaderBean();
        RemoteOpenDoorParam.RequestParamBean requestParamBean = new RemoteOpenDoorParam.RequestParamBean();
        headerBean.setSignature("5c27d4ec-427b-4698-a32a-1dca4e813574");
        headerBean.setToken("1509081676726");
        requestParamBean.setSdkKey(key);
        param.setHeader(headerBean);
        param.setRequestParam(requestParamBean);
        RemoteOpenDoorApiClient.create(DoorApi.class).remoteOpenDoor(new Gson().toJson(param)).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(new Observer<RemoteDoorMessageTo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(RemoteDoorMessageTo msg) {
           if ("1".equals(msg.getStatusCode())){
              baseView.submitDataSuccess(doorSid);
           }else
               baseView.showMessage(Constant.OPEN_DOOR_FAIL);
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
}
