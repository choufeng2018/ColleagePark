package com.nacity.college.property.presenter;

import android.text.TextUtils;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.ServiceRequestParam;
import com.college.common_libs.domain.property.ServiceTimeParam;
import com.college.common_libs.domain.property.ServiceTimeTo;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.college.common_libs.domain.property.WaterDistributionTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.property.model.DistributionOrderModel;
import com.nacity.college.property.view.DistributionOrderView;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by usb on 2017/10/30.
 */

public class DistributionOrderPresenter extends BasePresenter implements DistributionOrderModel {
    private DistributionOrderView distributionOrderView;
    private PropertyApi api = ApiClient.create(PropertyApi.class);

    public DistributionOrderPresenter(DistributionOrderView distributionOrderView) {
        this.distributionOrderView = distributionOrderView;
    }

    @Override
    public void getServiceTime(int type) {
        ServiceTimeParam param = new ServiceTimeParam();
        param.setType(type);
        param.setGardenId(apartmentInfo.getSid());
        api.findTime(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<List<ServiceTimeTo>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        distributionOrderView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<List<ServiceTimeTo>> msg) {
                        if (msg.getSuccess() == 0) {
                            if (msg.getData() != null && msg.getData().size() > 0) {
                                int a = msg.getData().size();
                                String[] dayList = new String[a];
                                String[] dateList = new String[a];
                                String[][] timeList = new String[a][];
                                for (int i = 0; i < msg.getData().size(); i++) {
                                    ServiceTimeTo serviceTimeTo = msg.getData().get(i);
                                    dayList[i] = serviceTimeTo.getDateName();
                                    dateList[i] = serviceTimeTo.getDateName();
                                    List<String> list = serviceTimeTo.getTimes();
                                    String[] arrayTime = new String[list.size()];
                                    for (int j = 0; j < list.size(); j++) {
                                        arrayTime[j] = list.get(j);
                                    }
                                    timeList[i] = arrayTime;
                                }
                                distributionOrderView.setServiceTime(dayList, dateList, timeList);
                            }

                        } else
                            distributionOrderView.showErrorMessage(msg.getMessage());
                    }
                }
        );

    }

    @Override
    public void submit(ServiceTypeTo selectServiceType, String serviceNumber, String contact, String phoneNumber, String repairTime, String serviceDescription, String repairAddress,List<WaterDistributionTo> serviceTypeIdList) {
        ServiceRequestParam param = new ServiceRequestParam();
        param.setGardenId(apartmentInfo.getSid());
        param.setLoginUserId(userInfo.getSid());
        param.setRepairTime(repairTime);
        param.setServiceTypeId(selectServiceType.getId());
        param.setPhone(TextUtils.isEmpty(phoneNumber)?userInfo.getUserInfoTo().getUserMobile():phoneNumber);
        param.setUserName(TextUtils.isEmpty(contact)?userInfo.getUserInfoTo().getNickname():contact);
        param.setDeliverArea(repairAddress);
        param.setAmountNum(Integer.valueOf(serviceNumber));
        param.setServiceTypeIdList(serviceTypeIdList);
//        param.setImages(uploadImagePath);
        param.setServiceNum(Integer.valueOf(serviceNumber));
        param.setServiceDesc(serviceDescription);
        param.setServiceClassify(selectServiceType.getServiceClassify());
        param.setServiceTypeName(selectServiceType.getName());
        System.out.println(param + "addService");
        api.addServiceRequest(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<Boolean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        distributionOrderView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<Boolean> msg) {
                        if (msg.getSuccess() == 0)
                            distributionOrderView.submitSuccess(msg.getData());
                        else
                            distributionOrderView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }
}
