package com.nacity.college.guide.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.GuideApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.guide.IntroduceListParam;
import com.college.common_libs.domain.guide.IntroduceTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.BaseRecycleView;
import com.nacity.college.guide.model.IntroduceListModel;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/22.
 **/

public class IntroduceListPresenter extends BasePresenter implements IntroduceListModel {
    private BaseRecycleView<IntroduceTo> introduceView;

    public IntroduceListPresenter(BaseRecycleView<IntroduceTo> introduceView){
        this.introduceView=introduceView;
    }
    @Override
    public void getIntroduceList(int pageIndex) {
        GuideApi api = ApiClient.create(GuideApi.class);
        IntroduceListParam param = new IntroduceListParam();
        param.setNextPage(1+pageIndex+"");

        api.getIntroduceList(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<IntroduceTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        introduceView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<IntroduceTo>> msg) {
                        if (msg.getSuccess() == 0)
                            introduceView.refreshRecycleView(msg.getData());
                        else
                            introduceView.showMessage(msg.getMessage());
                    }
                }
        );
    }
}
