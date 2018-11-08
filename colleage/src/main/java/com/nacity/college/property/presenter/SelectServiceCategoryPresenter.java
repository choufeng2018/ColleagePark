package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.ServiceTypeParam;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.property.model.SelectServiceCategoryModel;
import com.nacity.college.property.view.SelectServiceCategoryView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/5.
 **/

public class SelectServiceCategoryPresenter extends BasePresenter implements SelectServiceCategoryModel {
    private SelectServiceCategoryView<ServiceTypeTo> categoryView;

    public SelectServiceCategoryPresenter(SelectServiceCategoryView<ServiceTypeTo> categoryView) {
        this.categoryView = categoryView;
    }

    /**
     * 获取服务种类数据
     */
    @Override
    public void getCategoryData(String categorySid) {
        ServiceTypeParam param = new ServiceTypeParam();
        param.setLoginUserId(userInfo.getSid());
        param.setCategoryType(categorySid);
        param.setGardenId(apartmentInfo.getSid());
        PropertyApi api = ApiClient.create(PropertyApi.class);
        api.findTypeByCategory(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<List<ServiceTypeTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        categoryView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<ServiceTypeTo>> msg) {
                        if (msg.getSuccess() == 0) {
                            if (msg.getData() != null)
                                categoryView.refreshRecycleView(msg.getData());
                        } else
                            categoryView.showMessage(msg.getMessage());
                    }
                }
        );

    }
}
