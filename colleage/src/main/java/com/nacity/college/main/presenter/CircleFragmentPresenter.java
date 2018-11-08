package com.nacity.college.main.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.CircleApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.circle.NeighborPostTypeTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.main.model.CircleFragmentModel;
import com.nacity.college.main.view.CircleFragmentView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/24.
 **/

public class CircleFragmentPresenter extends BasePresenter implements CircleFragmentModel {

    private CircleApi api = ApiClient.create(CircleApi.class);
    private CircleFragmentView circleView;

    public CircleFragmentPresenter(CircleFragmentView circleView) {
        this.circleView = circleView;
    }

    @Override
    public void getCircleType() {
        api.getPostTypeList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<NeighborPostTypeTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        circleView.loadError(e);
                        System.out.println(e+"error");
                    }

                    @Override
                    public void onNext(MessageTo<List<NeighborPostTypeTo>> msg) {

                        if (msg.getSuccess() == 0)
                            circleView.getTypeSuccess(msg.getData());
                        else
                            circleView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }
}
