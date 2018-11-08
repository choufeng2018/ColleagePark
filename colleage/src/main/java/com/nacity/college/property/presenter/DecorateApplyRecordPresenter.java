package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.DecorateApplyListParam;
import com.college.common_libs.domain.property.DecorateApplyTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.property.model.DecorateApplyRecordModel;
import com.nacity.college.property.view.DecorateApplyRecordView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by usb on 2017/10/17.
 */

public class DecorateApplyRecordPresenter extends BasePresenter implements DecorateApplyRecordModel {
    private DecorateApplyRecordView<DecorateApplyTo> recordView;

    public DecorateApplyRecordPresenter(DecorateApplyRecordView<DecorateApplyTo> recordView) {
        this.recordView = recordView;

    }
    @Override
    public void getDecorateApplyRecord(int pageIndex) {
        PropertyApi api = ApiClient.create(PropertyApi.class);
        DecorateApplyListParam param=new DecorateApplyListParam();
        param.setUserId(userInfo.getSid());
        param.setPageIndex(pageIndex+1);
        param.setGardenId(apartmentInfo.getSid());
        api.findDecorateApplyList(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<List<DecorateApplyTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        recordView.loadError(e);

                    }

                    @Override
                    public void onNext(MessageTo<List<DecorateApplyTo>> msg) {
                        if (msg.getSuccess() == 0) {
                            if (msg.getData() != null) {

                                recordView.refreshRecycleView(msg.getData());
                            }
                        } else
                            recordView.showMessage(msg.getMessage());

                    }
                }
        );

    }

}
