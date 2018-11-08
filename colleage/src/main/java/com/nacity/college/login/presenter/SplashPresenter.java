package com.nacity.college.login.presenter;

import android.text.TextUtils;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.LoginApi;
import com.college.common_libs.domain.AdInfoTo;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.impl.SuccessCallback;
import com.nacity.college.common.model.ApartmentAdModel;
import com.nacity.college.common.presenter.ApartmentAdPresenter;
import com.nacity.college.login.model.SplashModel;
import com.nacity.college.login.view.SplashView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/8/25.
 **/

public class SplashPresenter extends BasePresenter implements SplashModel, SuccessCallback<List<AdvertiseTo>> {

    private SplashView mSplashView;
    private long loadingTime;

    public SplashPresenter(SplashView splashView) {
        this.mSplashView = splashView;
        ApartmentAdModel model=new ApartmentAdPresenter();
        if (!TextUtils.isEmpty(apartmentInfo.getSid()))
        model.getApartmentAd(1,apartmentInfo.getSid(),this);
        loadingTime=System.currentTimeMillis();

    }

    @Override
    public List<AdInfoTo> getAdList(String apartmentSid, int type) {


        LoginApi api=ApiClient.create(LoginApi.class);
        api.findAdListInfoByApartmentSid(apartmentSid, type).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<AdInfoTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mSplashView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<AdInfoTo>> msg) {

                    }
                }
        );

        return null;
    }

    @Override
    public void adSuccess(List<AdvertiseTo> advertiseTos) {
        if (advertiseTos!=null&&advertiseTos.size()>0&&(System.currentTimeMillis()-loadingTime<3000))
            mSplashView.setLoadingAd(advertiseTos.get(0),0);
    }
}
