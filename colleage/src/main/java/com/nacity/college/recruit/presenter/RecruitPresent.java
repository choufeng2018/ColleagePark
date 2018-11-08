package com.nacity.college.recruit.presenter;

import android.util.Log;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.RecruitApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.recruit.CompanyRecruitParam;
import com.college.common_libs.domain.recruit.RecruitItemTo;
import com.college.common_libs.domain.recruit.RecruitParam;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.impl.SuccessCallback;
import com.nacity.college.common.model.ApartmentAdModel;
import com.nacity.college.common.presenter.ApartmentAdPresenter;
import com.nacity.college.recruit.model.RecruitModel;
import com.nacity.college.recruit.view.RecruitView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *  Created by usb on 2017/9/20.
 */

public class RecruitPresent extends BasePresenter implements RecruitModel, SuccessCallback<List<AdvertiseTo>> {
    private RecruitView recruitView;

    public RecruitPresent(RecruitView recruitView){
        this.recruitView=recruitView;
        ApartmentAdModel model=new ApartmentAdPresenter();
        model.getApartmentAd(6,apartmentInfo.getSid(),this);
    }
    @Override
    public void getRecruitList(int index, String limit) {
        RecruitParam param =new RecruitParam();
        param.setPageNumber(index);
        param.setPageSize(limit);
        RecruitApi api= ApiClient.create(RecruitApi.class);
        api.findRecruitList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<RecruitItemTo>>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("5555", "Throwable: "+e.toString());
                        recruitView.loadError(e);
                    }
                    @Override
                    public void onNext(MessageTo<List<RecruitItemTo>> msg) {
                        if (msg.getSuccess()==0) {
                            Log.i("5555", "onNext: "+msg.toString());
                            recruitView.getNewsList(msg.getData());
                        }
                    }
                }
        );
    }

    @Override
    public void getRecruitListByLicenseId(String licenseId, int index) {
        CompanyRecruitParam param =new CompanyRecruitParam();
        param.setLicenseId(licenseId);
        param.setPageIndex(index);
        RecruitApi api= ApiClient.create(RecruitApi.class);
        api.findRecruitListByCompany(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<RecruitItemTo>>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("5555", "Throwable: "+e.toString());

                    }
                    @Override
                    public void onNext(MessageTo<List<RecruitItemTo>> msg) {
                        if (msg.getSuccess()==0) {
                            Log.i("5555", "onNext: "+msg.toString());
                            recruitView.getNewsList(msg.getData());
                        }
                    }
                }
        );
    }

    @Override
    public void adSuccess(List<AdvertiseTo> advertiseTos) {
        recruitView.getAdSuccess(advertiseTos);
    }
}

