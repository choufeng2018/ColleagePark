package com.nacity.college.circle.presenter.impl;


import com.college.common_libs.domain.circle.CareListTo;
import com.college.common_libs.domain.circle.NeighborhoodUserConnectParam;
import com.college.common_libs.domain.circle.NeighborhoodUserConnectTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.circle.interactor.CareInteractor;
import com.nacity.college.circle.interactor.CommonLifeInteractor;
import com.nacity.college.circle.interactor.impl.CareInteractorImpl;
import com.nacity.college.circle.interactor.impl.CommonLifeInteractorImpl;
import com.nacity.college.circle.presenter.CarePresenter;
import com.nacity.college.circle.view.CareView;

import java.util.List;


/**
 * Created by xzz on 2017/7/2.
 **/

public class CarePresenterImpl extends BasePresenter implements CarePresenter, LoadingCallback<List<CareListTo>>, CommonLifeInteractor.CareOtherCallback {

    private CareView<CareListTo> careView;
    private CareInteractor interactor;
    private CommonLifeInteractor commonInteractor;


    public CarePresenterImpl(CareView<CareListTo> careView){
        this.careView=careView;
        interactor=new CareInteractorImpl();
        commonInteractor=new CommonLifeInteractorImpl();
    }
    @Override
    public void getCareList(int pageIndex) {
        interactor.getCareList(userInfo.getSid(),pageIndex,this);
    }

    @Override
    public void cancelCare(CareListTo ownerTo) {
        NeighborhoodUserConnectParam param=new NeighborhoodUserConnectParam();
        param.setUserId(userInfo.getSid());
        param.setFollowUserId(ownerTo.getFollowUserId());
        commonInteractor.careOther(param,this);
    }

    @Override
    public void success(List<CareListTo> postList) {
        careView.refreshRecycleView(postList);
    }

    @Override
    public void message(String message) {

    }

    @Override
    public void error(Throwable error) {
        System.out.println(error+"error");
    }



    @Override
    public void careOtherSuccess(NeighborhoodUserConnectTo userConnectTo) {
        careView.cancelCareSuccess();
    }
}
