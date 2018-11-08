package com.nacity.college.login.presenter;

import com.college.common_libs.api.ApartmentApi;
import com.college.common_libs.api.ApiClient;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.apartment.ApartmentInfoTo;
import com.college.common_libs.domain.apartment.ApartmentParam;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.login.model.SelectApartmentModel;
import com.nacity.college.login.view.SelectApartmentView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/17.
 **/

public class SelectApartmentPresenter extends BasePresenter implements SelectApartmentModel {

    private SelectApartmentView<ApartmentInfoTo> selectApartmentView;

    public SelectApartmentPresenter(SelectApartmentView<ApartmentInfoTo> selectApartmentView) {
        this.selectApartmentView = selectApartmentView;
    }


    /**
     * 获取所有小区，如果输入名称后，接口就是搜索结果
     */
    @Override
    public void getApartmentByNameAndCityId(String name, String cityId) {
        ApartmentApi api = ApiClient.create(ApartmentApi.class);
        ApartmentParam param = new ApartmentParam();
        param.setGardenName(name);
        param.setCityId(cityId);
        api.getApartmentByNameAndCityId(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<ApartmentInfoTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        selectApartmentView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<ApartmentInfoTo>> msg) {
                        if (msg.getSuccess() == 0)
                            selectApartmentView.refreshRecycleView(msg.getData());
                        else
                            selectApartmentView.showMessage(msg.getMessage());
                    }
                }
        );

    }
}
