package com.nacity.college.circle.presenter.impl;


import com.college.common_libs.domain.circle.CareListTo;
import com.college.common_libs.domain.circle.NeighborhoodUserConnectParam;
import com.college.common_libs.domain.circle.NeighborhoodUserConnectTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.circle.interactor.CommonLifeInteractor;
import com.nacity.college.circle.interactor.FansInteractor;
import com.nacity.college.circle.interactor.impl.CommonLifeInteractorImpl;
import com.nacity.college.circle.interactor.impl.FansInteractorImpl;
import com.nacity.college.circle.presenter.FansPresenter;
import com.nacity.college.circle.view.FansView;

import java.util.List;


/**
 * Created by xzz on 2017/7/2.
 **/

public class FansPresenterImpl extends BasePresenter implements FansPresenter, LoadingCallback<List<CareListTo>>,CommonLifeInteractor.CareOtherCallback {

    private FansView<CareListTo> fansView;
    private FansInteractor interactor;
    private CommonLifeInteractor commonInteractor;

    public FansPresenterImpl(FansView<CareListTo> fansView){
        this.fansView=fansView;
        interactor=new FansInteractorImpl();
        commonInteractor=new CommonLifeInteractorImpl();
    }
    @Override
    public void getFansList(int pageIndex) {
        interactor.getFansList(userInfo.getSid(),pageIndex,this);
    }

    @Override
    public void careOther(CareListTo ownerTo) {
        NeighborhoodUserConnectParam param=new NeighborhoodUserConnectParam();
        param.setUserId(userInfo.getSid());
        param.setFollowUserId(ownerTo.getUserId());
        commonInteractor.careOther(param,this);
    }

    @Override
    public void success(List<CareListTo> postList) {
        fansView.refreshRecycleView(postList);
    }

    @Override
    public void message(String message) {
        fansView.showMessage(message);

    }

    @Override
    public void error(Throwable error) {
        System.out.println(error+"error");
    }

    /**
     *关注成功
     */
    @Override
    public void careOtherSuccess(NeighborhoodUserConnectTo userConnectTo) {
        fansView.careSuccess();
    }
}
