package com.nacity.college.circle.presenter.impl;

import com.college.common_libs.domain.circle.DeletePraiseParam;
import com.college.common_libs.domain.circle.NeighborHandleParam;
import com.college.common_libs.domain.circle.NeighborLikeTo;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.circle.interactor.CommonLifeInteractor;
import com.nacity.college.circle.interactor.LifeInteractor;
import com.nacity.college.circle.interactor.impl.CommonLifeInteractorImpl;
import com.nacity.college.circle.interactor.impl.LifeInteractorImpl;
import com.nacity.college.circle.presenter.LifeFragmentPresenter;
import com.nacity.college.circle.view.LifeFragmentView;

import java.util.List;


/**
 * Created by xzz on 2017/6/30.
 **/

public class LifeFragmentPresenterImpl extends BasePresenter implements LifeFragmentPresenter, LoadingCallback<List<NeighborPostTo>>, CommonLifeInteractor.AddPraiseCallback, CommonLifeInteractor.DeletePostCallback {
    private LifeFragmentView fragmentView;

    private LifeInteractor interactor;

    private CommonLifeInteractor commonInteractor;

    public LifeFragmentPresenterImpl(LifeFragmentView fragmentView) {
        this.fragmentView = fragmentView;
        interactor = new LifeInteractorImpl();
        commonInteractor = new CommonLifeInteractorImpl();
    }


    @Override
    public void getLifePostData(String typeSid, int pageIndex) {
        interactor.getLifePostList(userInfo.getSid(), typeSid, pageIndex, this);
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

    @Override
    public void deletePraise(String postSid) {
        DeletePraiseParam param=new DeletePraiseParam();
        param.setId(postSid);
        commonInteractor.deletePraise(param, this);
    }

    @Override
    public void success(List<NeighborPostTo> postList) {
        fragmentView.refreshRecycleView(postList);
    }

    @Override
    public void message(String message) {
        fragmentView.showErrorMessage(message);
    }

    @Override
    public void error(Throwable error) {
        fragmentView.loadError(error);
    }

    /**
     * 点赞或取消赞成功
     */
    @Override
    public void AddPraiseSuccess(NeighborLikeTo likeTo) {

        fragmentView.addPraiseSuccess(null);
    }

    @Override
    public void deletePostSuccess() {
        fragmentView.deletePostSuccess();
    }
}
