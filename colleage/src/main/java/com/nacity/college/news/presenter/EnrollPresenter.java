package com.nacity.college.news.presenter;

import android.util.Log;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.NewsApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.news.EnrollParam;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.news.model.EnrollModel;
import com.nacity.college.news.view.EnrollView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  Created by usb on 2017/12/11.
 */

public class EnrollPresenter extends BasePresenter implements EnrollModel {
private EnrollView enrollView;
    public EnrollPresenter(EnrollView enrollView){
        this.enrollView=enrollView;
    }
    @Override
    public void enrollActivity(String salonId, String name, String phone, String address) {
        EnrollParam param =new EnrollParam();
        param.setUserId(userInfo.getSid());
        param.setSalonId(salonId);
        param.setAddress(address);
        param.setName(name);
        param.setPhone(phone);
//        param.setNoticeType(noticeType);
//        param.setType(type);
//        Log.i("2222", "onNext: 0");
        NewsApi api= ApiClient.create(NewsApi.class);
        api.signUpSalon(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo>() {
                    @Override
                    public void onCompleted() {
                        Log.i("2222", "onNext: 11111");

                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("2222", "onNext: 2222"+e.toString());
                        enrollView.loadError(e);
                    }
                    @Override
                    public void onNext(MessageTo msg) {
                        Log.i("2222", "onNext: 3333 "+msg.getData().toString());

                        if (msg.getSuccess()==0) {

                            enrollView.enrollSuccess();
                            Log.i("2222", "onNext: 2"+msg.getData().toString());
                        }else{
                            enrollView.showErrorMessage(msg.getMsg());
                        }
                    }
                }
        );
    }
}
