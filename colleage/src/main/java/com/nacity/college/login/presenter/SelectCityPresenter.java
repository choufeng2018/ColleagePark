package com.nacity.college.login.presenter;

import com.college.common_libs.api.ApartmentApi;
import com.college.common_libs.api.ApiClient;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.apartment.ParkCityParam;
import com.college.common_libs.domain.apartment.ParkCityTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.login.model.SelectCityModel;
import com.nacity.college.login.view.SelectApartmentView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/17.
 **/

public class SelectCityPresenter extends BasePresenter implements SelectCityModel {

    private SelectApartmentView<ParkCityTo> selectApartmentView;
    public SelectCityPresenter(SelectApartmentView<ParkCityTo> selectApartmentView){
        this.selectApartmentView=selectApartmentView;
    }

    /**
     *
     *获取所有城市，如果输入名称后，接口就是搜索结果
     */
    @Override
    public void getCityByName(String name) {
        ApartmentApi api= ApiClient.create(ApartmentApi.class);
        ParkCityParam param=new ParkCityParam();
        param.setName(name);
        api.getCityByName(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<ParkCityTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                     selectApartmentView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<ParkCityTo>> msg) {
                     if (msg.getSuccess()==0)
                         selectApartmentView.refreshRecycleView(msg.getData());
                        else
                            selectApartmentView.showMessage(msg.getMessage());
                    }
                }
        );
    }
}
