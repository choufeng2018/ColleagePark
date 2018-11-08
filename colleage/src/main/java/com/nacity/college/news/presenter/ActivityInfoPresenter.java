package com.nacity.college.news.presenter;

import android.util.Log;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.NewsApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.news.EnrollParam;
import com.college.common_libs.domain.news.NewsTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.news.model.ActivityInfoModel;
import com.nacity.college.news.view.ActivityView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by usb on 2017/12/11.
 */

public class ActivityInfoPresenter extends BasePresenter implements ActivityInfoModel {
    private ActivityView activityView;

    public ActivityInfoPresenter(ActivityView activityView){
        this.activityView=activityView;
    }

    @Override
    public void getActivityInfo(String id) {
        EnrollParam param =new EnrollParam();
        param.setUserId(userInfo.getSid());
        param.setId(id);
        NewsApi api= ApiClient.create(NewsApi.class);
        api.OneActivityInfo(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<NewsTo>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        activityView.loadError(e);
                    }
                    @Override
                    public void onNext(MessageTo<NewsTo> msg) {
                        if (msg.getSuccess()==0) {
                            activityView.getActivityInfo(msg.getData());
//                            mNewsFragmentView.getNewsList(msg.getData());
                            Log.i("33333", "onNext: 2"+msg.getData().toString());


                        }
                    }
                }
        );
    }
    }

