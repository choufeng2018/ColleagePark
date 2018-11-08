package com.nacity.college.circle.interactor.impl;


import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.CircleApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.circle.CircleJoinParam;
import com.college.common_libs.domain.circle.CircleJoinTo;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.circle.interactor.JoinInteractor;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/7/2.
 **/

public class JoinInteractorImpl implements JoinInteractor {
    @Override
    public void getJoinList(String ownerSid, int pageIndex, LoadingCallback<List<CircleJoinTo>> callback) {
        CircleJoinParam param=new CircleJoinParam();
        param.setCreateUserId(ownerSid);
        param.setPageIndex(pageIndex+1);
        CircleApi api= ApiClient.create(CircleApi.class);
        api.getMyNeighborJoin(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<List<CircleJoinTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.error(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<CircleJoinTo>> msg) {
                        if (msg.getSuccess() == 0)
                            callback.success(msg.getData());
                        else
                            callback.message(msg.getMessage());
                    }
                }
        );
    }
}
