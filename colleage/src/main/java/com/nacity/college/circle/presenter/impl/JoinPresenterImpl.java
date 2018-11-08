package com.nacity.college.circle.presenter.impl;


import com.college.common_libs.domain.circle.CircleJoinTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.BaseRecycleView;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.circle.interactor.JoinInteractor;
import com.nacity.college.circle.interactor.impl.JoinInteractorImpl;
import com.nacity.college.circle.presenter.JoinPresenter;

import java.util.List;


/**
 * Created by xzz on 2017/7/2.
 **/

public class JoinPresenterImpl extends BasePresenter implements JoinPresenter, LoadingCallback<List<CircleJoinTo>> {

    private BaseRecycleView<CircleJoinTo> careView;
    private JoinInteractor interactor;


    public JoinPresenterImpl(BaseRecycleView<CircleJoinTo> careView){
        this.careView = careView;
        interactor=new JoinInteractorImpl();

    }
    @Override
    public void getJoinList(int pageIndex) {
        interactor.getJoinList(userInfo.getSid(),pageIndex,this);
    }



    @Override
    public void success(List<CircleJoinTo> postList) {
        careView.refreshRecycleView(postList);
    }

    @Override
    public void message(String message) {

    }

    @Override
    public void error(Throwable error) {
        System.out.println(error+"error");
    }

}
