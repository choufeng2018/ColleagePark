package com.nacity.college.guide.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.GuideApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.apartment.AdvertiseTo;
import com.college.common_libs.domain.apartment.GuideHouseTypeTo;
import com.college.common_libs.domain.apartment.ParkIdParam;
import com.college.common_libs.domain.guide.EnterpriseListParam;
import com.college.common_libs.domain.guide.EnterpriseTo;
import com.college.common_libs.domain.guide.IntroduceListParam;
import com.college.common_libs.domain.guide.IntroduceTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.impl.SuccessCallback;
import com.nacity.college.common.model.ApartmentAdModel;
import com.nacity.college.common.presenter.ApartmentAdPresenter;
import com.nacity.college.guide.model.ParkGuideModel;
import com.nacity.college.guide.view.ParkGuideView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/22.
 **/

public class ParkGuidePresenter extends BasePresenter implements ParkGuideModel, SuccessCallback<List<AdvertiseTo>> {
    private ParkGuideView<EnterpriseTo> guideView;
    private GuideApi api = ApiClient.create(GuideApi.class);

    public ParkGuidePresenter(ParkGuideView<EnterpriseTo> parkGuideView) {
        this.guideView = parkGuideView;
        ApartmentAdModel model = new ApartmentAdPresenter();
        model.getApartmentAd(7, apartmentInfo.getSid(), this);
    }

    @Override
    public void getParkIntroduceList() {

        IntroduceListParam param = new IntroduceListParam();

        api.getIntroduceList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<IntroduceTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        guideView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<IntroduceTo>> msg) {
                        if (msg.getSuccess() == 0)
                            guideView.getIntroduceSuccess(msg.getData());
                        else
                            guideView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }

    @Override
    public void getParkHouse() {
        ParkIdParam param = new ParkIdParam();
        param.setGardenId(apartmentInfo.getSid());
        api.getEnterpriseHouse(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<GuideHouseTypeTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MessageTo<List<GuideHouseTypeTo>> msg) {
                        if (msg.getSuccess() == 0)
                            guideView.setHouse(msg.getData());

                    }
                }
        );
    }

    @Override
    public void getEnterPriseLit(int pageIndex, String house, int type) {
        EnterpriseListParam param = new EnterpriseListParam();
        param.setGardenId(apartmentInfo.getSid());
        if (type == 1)
            param.setTeam(house);
        else if (type == 2)
            param.setBuilding(house);
        param.setPageIndex(pageIndex + 1);
        api.getEnterpriseList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<EnterpriseTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        guideView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<EnterpriseTo>> msg) {
                        if (msg.getSuccess() == 0)
                            guideView.refreshRecycleView(msg.getData());
                        else
                            guideView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }

    @Override
    public void adSuccess(List<AdvertiseTo> advertiseList) {
        guideView.getAdSuccess(advertiseList);
    }
}
