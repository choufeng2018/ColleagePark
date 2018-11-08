package com.nacity.college.myself.presenter;

import android.util.Log;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.ServiceTypeParam;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.myself.model.MyServiceRecordModel;
import com.nacity.college.myself.view.MyServiceRecordView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by usb on 2017/12/7.
 */

public class MyServiceRecordPresenter extends BasePresenter implements MyServiceRecordModel {
    private PropertyApi api = ApiClient.create(PropertyApi.class);

    private MyServiceRecordView backView;
    public MyServiceRecordPresenter(MyServiceRecordView backView){
        this.backView=backView;
    }
    @Override
    public void getServiceType() {
        ServiceTypeParam param=new ServiceTypeParam();
        param.setGardenId(apartmentInfo.getSid());
        param.setLoginUserId(userInfo.getSid());
        api.getServiceCategory(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<String>>() {
                    @Override
                    public void onCompleted() {
                        Log.i("222", "onCompleted: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("222", "onError: "+e.toString());
                        backView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<String> msg) {
                        if (msg.getSuccess()==0){
                            Log.i("222", "onNext: "+msg.getData());
                        backView.getServiceTypeSuccess(msg.getData());}
                        else
                            backView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }
}
