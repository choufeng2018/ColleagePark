package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.EvaluateServiceParam;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.BaseView;
import com.nacity.college.property.model.EvaluateModel;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/10/12.
 **/

public class EvaluatePresenter extends BasePresenter implements EvaluateModel {

    private BaseView<Boolean> evaluateView;

    public EvaluatePresenter(BaseView<Boolean> evaluateView) {
        this.evaluateView = evaluateView;
    }

    @Override
    public void submitEvaluate(String serviceId, int attitude, int speed, int satisfaction, String content) {
//        if (TextUtils.isEmpty(content)) {
//            evaluateView.showErrorMessage(Constant.EVALUATE_CONTENT_NO_EMPTY);
//        }
        PropertyApi api = ApiClient.create(PropertyApi.class);
        EvaluateServiceParam param = new EvaluateServiceParam();
        param.setServiceId(serviceId);
        param.setEvaluateServiceAttitude(attitude);
        param.setEvaluateFinishSpeed(speed);
        param.setEvaluatePleasedDegree(satisfaction);
        param.setServiceDesc(content);
        param.setLoginUserId(userInfo.getSid());
        api.evaluateService(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<Boolean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        evaluateView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<Boolean> msg) {
                        if (msg.getSuccess() == 0)
                            evaluateView.getDataSuccess(msg.getData());
                        else
                            evaluateView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }
}
