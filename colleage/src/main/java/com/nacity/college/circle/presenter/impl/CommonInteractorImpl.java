package com.nacity.college.circle.presenter.impl;


import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.nacity.college.circle.presenter.CommonInteractor;

import rx.Observer;
import rx.schedulers.Schedulers;


/**
 * Created by xzz on 2017/6/26.
 **/

public class CommonInteractorImpl implements CommonInteractor {
    @Override
    public void getAd(String apartmentSid, String type, AdLoadingFinish loadingFinish) {

    }

    @Override
    public void getToken(String path, TokenFinish tokenFinish) {
        PropertyApi api= ApiClient.create(PropertyApi.class);
        api.getQnToken().subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e+"--------");
                     tokenFinish.error();
                    }

                    @Override
                    public void onNext(MessageTo<String> msg) {
                        System.out.println(msg+"--------");
                     if (msg.getSuccess()==0)
                         tokenFinish.tokenSuccess(msg.getData(),path);
                    }
                }
        );
    }

    @Override
    public void uploadImage(String key, String userSid, UpImageFinish upImageFinish) {


    }




}
