package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.FeedbackListParam;
import com.college.common_libs.domain.property.FeedbackTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.BaseRecycleView;
import com.nacity.college.property.model.PraiseRecordModel;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/6.
 **/

public class PraiseRecordPresenter extends BasePresenter implements PraiseRecordModel {
    private BaseRecycleView<FeedbackTo> recycleView;

    public PraiseRecordPresenter(BaseRecycleView<FeedbackTo> recycleView) {
        this.recycleView = recycleView;

    }

    @Override
    public void getServiceRecord(int pageIndex, int type) {
        PropertyApi api = ApiClient.create(PropertyApi.class);
        FeedbackListParam param = new FeedbackListParam();
        param.setFeedbackType(type);
        param.setGardenId(apartmentInfo.getSid());
        param.setFeedbackUserId(userInfo.getSid());
        param.setPageIndex(pageIndex+1);
        api.findListByUser(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<FeedbackTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        recycleView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<FeedbackTo>> msg) {
                        if (msg.getSuccess() == 0)
                            recycleView.refreshRecycleView(msg.getData());
                        else
                            recycleView.showMessage(msg.getMessage());
                    }
                }
        );
    }
}
