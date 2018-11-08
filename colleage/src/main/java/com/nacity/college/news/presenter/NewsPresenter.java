package com.nacity.college.news.presenter;

import android.util.Log;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.NewsApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.news.NewListParam;
import com.college.common_libs.domain.news.NewsTo;
import com.nacity.college.news.model.NewsModel;
import com.nacity.college.news.view.NewsFragmentView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  Created by usb on 2017/9/4.
 */

public class NewsPresenter implements NewsModel {
    private NewsFragmentView mNewsFragmentView;

    public NewsPresenter(NewsFragmentView mNewsFragmentView){
        this.mNewsFragmentView=mNewsFragmentView;
    }


    @Override
    public void getNews(int index, String parkId, int type, String noticeType) {
        NewListParam param =new NewListParam();
        param.setLimit("20");
        param.setNextPage(index);
        param.setGardenId(parkId);
        param.setNoticeType(noticeType);
        param.setType(type);
        Log.i("2222", "onNext: 0");
        NewsApi api= ApiClient.create(NewsApi.class);
        api.findNewsList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<NewsTo>>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        mNewsFragmentView.loadError(e);
                    }
                    @Override
                    public void onNext(MessageTo<List<NewsTo>> msg) {
                        if (msg.getSuccess()==0) {

                                mNewsFragmentView.getNewsList(msg.getData());
                                Log.i("33333", "onNext: 2"+msg.getData().toString());


                        }
                    }
                }
        );
    }
}
