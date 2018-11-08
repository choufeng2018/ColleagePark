package com.nacity.college.circle.interactor.impl;


import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.CircleApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.circle.CirclePostListParam;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.circle.interactor.LifeInteractor;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/6/29.
 **/

public class LifeInteractorImpl implements LifeInteractor {
    @Override
    public void getLifeCategory(CategoryCallback callback) {

    }

    @Override
    public void getLifePostList(String useSid, String typeId, int pageIndex, LoadingCallback<List<NeighborPostTo>> callback) {
        CircleApi api=ApiClient.create(CircleApi.class);
        CirclePostListParam param=new CirclePostListParam();
        param.setTypeId(typeId);
        param.setPageIndex(pageIndex+1);
        param.setCurrentUserId(useSid);
        api.getPostList(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<List<NeighborPostTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                            callback.error(e);
                        System.out.println(e+"lifeMsg");
                    }

                    @Override
                    public void onNext(MessageTo<List<NeighborPostTo>> msg) {
                        System.out.println(msg+"lifeMsg");
                        if (msg.getSuccess()==0)
                            callback.success(msg.getData());
                        else
                            callback.message(msg.getMessage());
                    }
                }
        );
    }


}
