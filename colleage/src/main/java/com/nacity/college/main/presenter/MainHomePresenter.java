package com.nacity.college.main.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.UserApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.user.MainMenuParam;
import com.college.common_libs.domain.user.MainMenuTo;
import com.nacity.college.MainApp;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.impl.SuccessCallback;
import com.nacity.college.base.info.ApartmentInfoHelper;
import com.nacity.college.common.model.ApartmentAdModel;
import com.nacity.college.common.presenter.ApartmentAdPresenter;
import com.nacity.college.main.model.MainHomeModel;
import com.nacity.college.main.view.MainHomeView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/19.
 **/

public class MainHomePresenter extends BasePresenter implements MainHomeModel, SuccessCallback<List<AdvertiseTo>> {
    private MainHomeView homeView;
    private UserApi api = ApiClient.create(UserApi.class);
    private MainMenuParam param = new MainMenuParam();
    private ApartmentAdModel model = new ApartmentAdPresenter();

    public MainHomePresenter(MainHomeView homeView) {
        this.homeView = homeView;


        param.setUserId(userInfo.getSid());

    }

    @Override
    public void getApartmentAd(String parkSid) {
        apartmentInfo = ApartmentInfoHelper.getInstance(MainApp.mContext);

        model.getApartmentAd(2, apartmentInfo.getSid(), this);
    }

    /**
     * 获取顶部广告成功
     */
    @Override
    public void adSuccess(List<AdvertiseTo> advertiseList) {
        homeView.getAdSuccess(advertiseList);
    }


    @Override
    public void getParkService() {
        apartmentInfo = ApartmentInfoHelper.getInstance(MainApp.mContext);
        param.setGardenId(apartmentInfo.getSid());
        param.setType("1");
        api.getMainMenu(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<MainMenuTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        homeView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<MainMenuTo>> msg) {
                        if (msg.getSuccess() == 0) {
                            homeView.getParkServiceSuccess(msg.getData());
                        } else
                            homeView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }

    @Override
    public void getPropertyService() {
        apartmentInfo = ApartmentInfoHelper.getInstance(MainApp.mContext);
        param.setGardenId(apartmentInfo.getSid());
        param.setType("2");
        api.getMainMenu(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<MainMenuTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        homeView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<MainMenuTo>> msg) {
                        if (msg.getSuccess() == 0) {
                            homeView.getPropertySuccess(msg.getData());
                        } else
                            homeView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }

    @Override
    public void getServiceList() {
        apartmentInfo = ApartmentInfoHelper.getInstance(MainApp.mContext);
        param.setGardenId(apartmentInfo.getSid());
        param.setType("3");
        api.getMainMenu(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<MainMenuTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        homeView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<MainMenuTo>> msg) {
                        if (msg.getSuccess() == 0) {
                            homeView.getServiceListSuccess(msg.getData());
                        } else
                            homeView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }
}
