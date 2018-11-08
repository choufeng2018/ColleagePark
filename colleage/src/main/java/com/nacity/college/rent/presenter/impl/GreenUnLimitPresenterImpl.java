package com.nacity.college.rent.presenter.impl;


import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.rent.HouseListParam;
import com.college.common_libs.domain.rent.HouseRentTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.impl.SuccessCallback;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.common.model.ApartmentAdModel;
import com.nacity.college.common.presenter.ApartmentAdPresenter;
import com.nacity.college.rent.interactor.GreenUnLimitInteractor;
import com.nacity.college.rent.interactor.Impl.GreenUnLimitInteractorImpl;
import com.nacity.college.rent.presenter.GreenUnLimitPresenter;
import com.nacity.college.rent.view.GreenUnLimitView;

import java.util.List;


/**
 * Created by xzz on 2017/6/23.
 **/

public class GreenUnLimitPresenterImpl extends BasePresenter implements GreenUnLimitPresenter, LoadingCallback<List<HouseRentTo>>,SuccessCallback<List<AdvertiseTo>> {


    private GreenUnLimitInteractor interactor;
    private GreenUnLimitView unLimitView;


    public GreenUnLimitPresenterImpl(GreenUnLimitView unLimitView){
        this.unLimitView=unLimitView;
        interactor=new GreenUnLimitInteractorImpl();
        ApartmentAdModel model=new ApartmentAdPresenter();
        model.getApartmentAd(5,apartmentInfo.getSid(),this);
    }

    @Override
    public void getData(int pageIndex) {
        HouseListParam param=new HouseListParam();

        param.setPageSize("20");
        param.setPageNumber(1+pageIndex+"");
        interactor.getSaleHouseList(param,this);
    }

    @Override
    public void success(List<HouseRentTo> houseList) {
        unLimitView.refreshRecycleView(houseList);
    }

    @Override
    public void message(String message) {
    unLimitView.showMessage(message);

    }

    @Override
    public void error(Throwable error) {
      unLimitView.loadError(error);
    }

    @Override
    public void adSuccess(List<AdvertiseTo> advertiseTos) {
        unLimitView.getAdSuccess(advertiseTos);
    }
}
