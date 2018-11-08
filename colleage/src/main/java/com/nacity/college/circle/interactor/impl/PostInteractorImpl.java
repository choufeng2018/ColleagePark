package com.nacity.college.circle.interactor.impl;


import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.CircleApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.circle.NeighborPostParam;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.circle.interactor.PostInteractor;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/7/1.
 **/

public class PostInteractorImpl implements PostInteractor {
    @Override
    public void lifePost(NeighborPostParam param, LoadingCallback<NeighborPostTo> callback) {
        CircleApi api= ApiClient.create(CircleApi.class);
        api.addNeighborPost(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<NeighborPostTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                           callback.error(e);
                    }

                    @Override
                    public void onNext(MessageTo<NeighborPostTo> msg) {
                        if (msg.getSuccess()==0)
                            callback.success(msg.getData());
                        else
                            callback.message(msg.getMessage());
                    }
                }
        );
    }
}
