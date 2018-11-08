package com.nacity.college.circle.interactor.impl;


import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.CircleApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.circle.CirclePostListParam;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.circle.interactor.LifePersonalInteractor;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/7/1.
 **/

public class LifePersonalInteractorImpl extends BasePresenter implements LifePersonalInteractor {
    @Override
    public void getPersonalPostList(String apartmentSid, String otherSid, String userSid, int pageIndex, LoadingCallback<MessageTo<List<NeighborPostTo>>> callback) {
        CircleApi api = ApiClient.create(CircleApi.class);
        CirclePostListParam param = new CirclePostListParam();
        param.setCurrentUserId(userInfo.getSid());
        param.setPageIndex(pageIndex + 1);
        param.setCreateUserId(otherSid);
        api.getAllPost(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<NeighborPostTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.error(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<NeighborPostTo>> msg) {
                        if (msg.getSuccess() == 0)
                            callback.success(msg);
                        else
                            callback.message(msg.getMessage());
                    }
                }
        );
    }
}
