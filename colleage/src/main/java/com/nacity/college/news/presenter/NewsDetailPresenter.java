package com.nacity.college.news.presenter;

import android.util.Log;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.NewsApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.news.NewsDetailParam;
import com.college.common_libs.domain.news.NewsTo;
import com.nacity.college.news.model.NewsDetailModel;
import com.nacity.college.news.view.NewsDetailView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  Created by usb on 2017/9/18.
 */

public class NewsDetailPresenter implements NewsDetailModel {
    private NewsDetailView mNewsDetailView;
    public NewsDetailPresenter(NewsDetailView mNewsDetailView){
        this.mNewsDetailView=mNewsDetailView;
    }

    @Override
    public void getNews(String id) {
        NewsDetailParam param =new NewsDetailParam();
        Log.i("2222", "id356498817282998272: "+id);
        param.setId(id);
        NewsApi api= ApiClient.create(NewsApi.class);
        api.findNewsDetail(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<NewsTo>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("4433", e.toString());
                        mNewsDetailView.loadError(e);

                    }
                    @Override
                    public void onNext(MessageTo<NewsTo> msg) {
                        if (msg.getSuccess()==0) {
                            if (msg.getData()!=null){
                                Log.i("11111", "onNext: ");
                                mNewsDetailView.getNewsDetail(msg.getData());
                            }
                        }
                    }
                }
        );
    }
}
