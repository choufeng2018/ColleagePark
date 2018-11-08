package com.nacity.college.circle.interactor.impl;


import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.CircleApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.circle.CareListTo;
import com.college.common_libs.domain.circle.FansListParam;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.circle.interactor.FansInteractor;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/7/2.
 **/

public class FansInteractorImpl implements FansInteractor {

    @Override
    public void getFansList(String ownerSid, int pageIndex, LoadingCallback<List<CareListTo>> callback) {
        CircleApi api= ApiClient.create(CircleApi.class);
        FansListParam param=new FansListParam();
        param.setPageIndex(pageIndex+1);
        param.setCurrentUserId(ownerSid);
        param.setFollowUserId(ownerSid);
        api.getFansList(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<List<CareListTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.error(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<CareListTo>> msg) {
                        if (msg.getSuccess() == 0)
                            callback.success(msg.getData());
                        else
                            callback.message(msg.getMessage());
                    }
                }
        );

    }
}
