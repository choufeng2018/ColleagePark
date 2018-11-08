package com.nacity.college.circle.presenter.impl;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.CircleApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.circle.NeighborCommentTo;
import com.college.common_libs.domain.circle.NeighborMessageParam;
import com.college.common_libs.domain.circle.NeighborPostTypeTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.utils.DateUtil;
import com.nacity.college.base.utils.SpUtil;
import com.nacity.college.circle.interactor.LifeInteractor;
import com.nacity.college.circle.interactor.impl.LifeInteractorImpl;
import com.nacity.college.circle.presenter.LifePresenter;
import com.nacity.college.circle.view.LifeView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by xzz on 2017/6/29.
 **/

public class LifePresenterImpl extends BasePresenter implements LifePresenter, LifeInteractor.CategoryCallback {

    private LifeView lifeView;
    private LifeInteractor interactor;
    private List<Fragment>fragmentList=new ArrayList<>();
    private ArrayList<String>typeList=new ArrayList<>();

    public LifePresenterImpl(LifeView lifeView){
        this.lifeView=lifeView;
        interactor=new LifeInteractorImpl();

    }



    @Override
    public void getLifeCategory() {
        interactor.getLifeCategory(this);
    }



    @Override
    public void message(String message) {

    }

    @Override
    public void error(Throwable error) {
        System.out.println(error+"error---");
    }

    /**
     *获取生活种类成功
     */
    @Override
    public void getCateGorySuccess(List<NeighborPostTypeTo> postTypeList) {

    }

    @Override
    public void getNewMessage() {
        CircleApi api= ApiClient.create(CircleApi.class);
        NeighborMessageParam param=new NeighborMessageParam();
        param.setApartmentSid(apartmentInfo.getSid());
        param.setUserSid(userInfo.getSid());
        param.setLastTime(TextUtils.isEmpty(SpUtil.getString("MessageDate"))?new Date(): DateUtil.getFormatDateLongTime(SpUtil.getString("MessageDate")));
        param.setPageIndex(0);
        api.findNeighborPostUserNewLike(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<List<NeighborCommentTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        lifeView.loadingDismiss();
                    }

                    @Override
                    public void onNext(MessageTo<List<NeighborCommentTo>> msg) {
                      if (msg.getSuccess()==0)
                      {
                          if (msg.getData()!=null)
                              getNewComment(msg.getData(),param);

                      }

                    }
                }
        );
    }

    private void getNewComment(List<NeighborCommentTo> userCommentTos, NeighborMessageParam param){
        CircleApi api=ApiClient.create(CircleApi.class);
        api.findNeighborPostUserNewComment(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<List<NeighborCommentTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MessageTo<List<NeighborCommentTo>> msg) {
                        if (msg.getSuccess()==0) {

                            if (msg.getData() == null)
                                lifeView.getNewMessageSuccess(userCommentTos);
                            else {
                                userCommentTos.addAll(msg.getData());
                                lifeView.getNewMessageSuccess(userCommentTos);
                            }
                        }
                    }
                }
        );
    }
}
