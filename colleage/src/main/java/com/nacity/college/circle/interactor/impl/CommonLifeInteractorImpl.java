package com.nacity.college.circle.interactor.impl;


import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.CircleApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.circle.DeletePostParam;
import com.college.common_libs.domain.circle.DeletePraiseParam;
import com.college.common_libs.domain.circle.NeighborCommentParam;
import com.college.common_libs.domain.circle.NeighborCommentTo;
import com.college.common_libs.domain.circle.NeighborHandleParam;
import com.college.common_libs.domain.circle.NeighborLikeTo;
import com.college.common_libs.domain.circle.NeighborhoodUserConnectParam;
import com.college.common_libs.domain.circle.NeighborhoodUserConnectTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.circle.interactor.CommonLifeInteractor;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/7/2.
 **/

public class CommonLifeInteractorImpl extends BasePresenter implements CommonLifeInteractor {
    @Override
    public void careOther(NeighborhoodUserConnectParam param, CareOtherCallback callback) {
        CircleApi api= ApiClient.create(CircleApi.class);
        api.careOther(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<NeighborhoodUserConnectTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MessageTo<NeighborhoodUserConnectTo> msg) {
                        if (msg.getSuccess()==0)
                            callback.careOtherSuccess(msg.getData());
                        else
                            callback.message(msg.getMessage());

                    }
                }
        );
    }

    @Override
    public void cancelCareOther(String ownerSid, String otherSid, CancelCareOtherCallback callback) {
        CircleApi api=ApiClient.create(CircleApi.class);
        api.deleteCare(ownerSid,otherSid).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<Boolean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                      callback.error(e);
                    }

                    @Override
                    public void onNext(MessageTo<Boolean> msg) {
                      if (msg.getSuccess()==0)
                         callback.cancelCareOtherSuccess();
                        else
                            callback.message(msg.getMessage());
                    }
                }
        );
    }

    @Override
    public void addPraise(NeighborHandleParam param, AddPraiseCallback callback) {
        CircleApi api=ApiClient.create(CircleApi.class);
        api.addNeighborLike(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<NeighborLikeTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                      callback.error(e);
                    }

                    @Override
                    public void onNext(MessageTo<NeighborLikeTo> msg) {
                       if (msg.getSuccess()==0)
                           callback.AddPraiseSuccess(msg.getData());
                        else
                            callback.message(msg.getMessage());
                    }
                }
        );
    }

    @Override
    public void deletePraise(DeletePraiseParam param, AddPraiseCallback callback) {

        CircleApi api=ApiClient.create(CircleApi.class);
        api.deleteNeighborLike(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<Boolean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.error(e);
                    }

                    @Override
                    public void onNext(MessageTo<Boolean> msg) {
                        if (msg.getSuccess()==0)
                            callback.AddPraiseSuccess(null);
                        else
                            callback.message(msg.getMessage());
                    }
                }
        );
    }

    @Override
    public void deletePost(String postSid, DeletePostCallback callback) {
        CircleApi api=ApiClient.create(CircleApi.class);
        DeletePostParam param=new DeletePostParam();
        param.setId(postSid);
        param.setModUserId(userInfo.getSid());
        api.delNeighborPost(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<Boolean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.error(e);
                    }

                    @Override
                    public void onNext(MessageTo<Boolean> msg) {
                       if (msg.getSuccess()==0)
                           callback.deletePostSuccess();
                        else
                            callback.message(msg.getMessage());
                    }
                }
        );
    }

    @Override
    public void deleteComment(String commentSid, DeleteCommentCallback callback) {
        CircleApi api=ApiClient.create(CircleApi.class);
        DeletePostParam param=new DeletePostParam();
        param.setModUserId(userInfo.getSid());
        param.setId(commentSid);
        api.deleteComment(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<Boolean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MessageTo<Boolean> msg) {
                      if (msg.getSuccess()==0)
                          callback.deleteCommentSuccess();
                    }
                }
        );
    }

    @Override
    public void addComment(NeighborCommentParam param, AddCommentCallback callback) {
        CircleApi api=ApiClient.create(CircleApi.class);
        api.addNeighborComment(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<NeighborCommentTo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                         callback.error(e);
                    }

                    @Override
                    public void onNext(MessageTo<NeighborCommentTo> msg) {
                      if (msg.getSuccess()==0)
                          callback.addCommentSuccess(msg.getData());
                        else
                          callback.message(msg.getMessage());
                    }
                }
        );
    }
}
