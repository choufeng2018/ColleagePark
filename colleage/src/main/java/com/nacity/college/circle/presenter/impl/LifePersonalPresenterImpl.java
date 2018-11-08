package com.nacity.college.circle.presenter.impl;


import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.circle.NeighborHandleParam;
import com.college.common_libs.domain.circle.NeighborLikeTo;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.college.common_libs.domain.circle.NeighborhoodUserConnectParam;
import com.college.common_libs.domain.circle.NeighborhoodUserConnectTo;
import com.college.common_libs.domain.circle.OwnerMessageTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.circle.interactor.CommonLifeInteractor;
import com.nacity.college.circle.interactor.LifePersonalInteractor;
import com.nacity.college.circle.interactor.impl.CommonLifeInteractorImpl;
import com.nacity.college.circle.interactor.impl.LifePersonalInteractorImpl;
import com.nacity.college.circle.presenter.LifePersonalPresenter;
import com.nacity.college.circle.view.LifePersonView;

import java.util.List;


/**
 * Created by xzz on 2017/7/1.
 **/

public class LifePersonalPresenterImpl extends BasePresenter implements LifePersonalPresenter, LoadingCallback<MessageTo<List<NeighborPostTo>>>, CommonLifeInteractor.CareOtherCallback, CommonLifeInteractor.CancelCareOtherCallback, CommonLifeInteractor.AddPraiseCallback, CommonLifeInteractor.DeletePostCallback {
    private LifePersonView personView;
    private LifePersonalInteractor interactor;
    private CommonLifeInteractor commonInteractor;

    public LifePersonalPresenterImpl(LifePersonView personView) {
        this.personView = personView;
        interactor = new LifePersonalInteractorImpl();
        commonInteractor = new CommonLifeInteractorImpl();
    }

    @Override
    public void getPostList(String otherSid, int pageIndex) {
        interactor.getPersonalPostList(apartmentInfo.getSid(), otherSid, userInfo.getSid(), pageIndex, this);
    }

    @Override
    public void careOther(String userSid, String otherSid) {
        NeighborhoodUserConnectParam param = new NeighborhoodUserConnectParam();
        param.setUserId(userInfo.getSid());
        param.setFollowUserId(otherSid);
        commonInteractor.careOther(param, this);
    }

    @Override
    public void cancelCare(OwnerMessageTo ownerTo) {
        commonInteractor.cancelCareOther(userInfo.getSid(), ownerTo.getOwnerSid(), this);
    }

    @Override
    public void addPraise(String postSid) {
        NeighborHandleParam param = new NeighborHandleParam();
        param.setPostId(postSid);
        param.setCreateUserId(userInfo.getSid());
        commonInteractor.addPraise(param, this);
    }

    @Override
    public void deletePost(String postSid) {
        commonInteractor.deletePost(postSid, this);
    }

    /**
     * 获取个人帖子成功
     */
    @Override
    public void success(MessageTo<List<NeighborPostTo>> postList) {
        personView.refreshRecycleView(postList);
    }

    @Override
    public void message(String message) {

    }

    @Override
    public void error(Throwable error) {
        personView.loadError(error);
    }

    /**
     * 关注成功
     */
    @Override
    public void careOtherSuccess(NeighborhoodUserConnectTo userConnectTo) {
        personView.careOtherSuccess();
    }

    /**
     * 取消关注成功
     */
    @Override
    public void cancelCareOtherSuccess() {
        personView.cancelOtherSuccess();

    }

    @Override
    public void AddPraiseSuccess(NeighborLikeTo likeTo) {
        personView.addPraiseSuccess(null);
    }

    @Override
    public void deletePostSuccess() {
        personView.deletePostSuccess();
    }
}
