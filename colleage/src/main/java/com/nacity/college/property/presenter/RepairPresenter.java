package com.nacity.college.property.presenter;

import android.text.TextUtils;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.ServiceRequestParam;
import com.college.common_libs.domain.property.ServiceTimeParam;
import com.college.common_libs.domain.property.ServiceTimeTo;
import com.college.common_libs.domain.property.ServiceTypeTo;
import com.nacity.college.MainApp;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.utils.image.Bimp;
import com.nacity.college.base.utils.image.ImageItem;
import com.nacity.college.property.model.RepairModel;
import com.nacity.college.property.view.RepairView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/9/5.
 **/

public class RepairPresenter extends BasePresenter implements RepairModel, UpCompletionHandler {
    private RepairView repairView;
    private PropertyApi api = ApiClient.create(PropertyApi.class);
    private int completeCount;  //上传图片完成次数
    private int photoPosition;
    private String uploadImagePath = "";
    private List<String> imagePathList = new ArrayList<>();

    public RepairPresenter(RepairView repairView) {
        this.repairView = repairView;
    }

    /**
     * 获取服务时间
     **/
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
                        repairView.loadError(e);
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
                                repairView.setServiceTime(dayList, dateList, timeList);
                            }

                        } else
                            repairView.showErrorMessage(msg.getMessage());
                    }
                }
        );

    }

    /**
     * 获取上传图片token
     **/
    @Override
    public void getUpImageToken() {
        api.getQnToken().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        repairView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<String> msg) {
                        if (msg.getSuccess() == 0) {
                            repairView.getTokenSuccess(msg.getData());
                        } else
                            repairView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }

    /**
     * 上传图片
     */

    @Override
    public void uploadImage(ArrayList<String> pathList, String token) {
        imagePathList = pathList;
        for (String mPath : pathList) {
            if (!mPath.equals("000000")) {
                String uuidPath = UUID.randomUUID().toString();
                uploadImagePath = uploadImagePath + MainApp.getImagePath(uuidPath) + ",";
                UploadManager uploadManager = new UploadManager();
                ImageItem takePhoto = new ImageItem();
                takePhoto.setImagePath(mPath);
                takePhoto.setBitmap(Bimp.getBitmap(mPath));
                Bimp.tempSelectBitmap.add(takePhoto);
                uploadManager.put(Bimp.getImageUri(Bimp.tempSelectBitmap.get(photoPosition).getImagePath(), false), uuidPath, token, this, null);
                photoPosition++;
            }
        }
    }

    @Override
    public void submit(ServiceTypeTo selectServiceType, String serviceNumber, String contact, String phoneNumber, String repairTime, String serviceDescription, String repairAddress) {

        ServiceRequestParam param = new ServiceRequestParam();
        param.setGardenId(apartmentInfo.getSid());
        param.setLoginUserId(userInfo.getSid());
        param.setRepairTime(repairTime);
        param.setServiceTypeId(selectServiceType.getId());
        param.setPhone(TextUtils.isEmpty(phoneNumber)?userInfo.getUserInfoTo().getUserMobile():phoneNumber);
        param.setUserName(TextUtils.isEmpty(contact)?userInfo.getUserInfoTo().getNickname():contact);
        param.setDeliverArea(repairAddress);
        param.setImages(uploadImagePath);
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
                        repairView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<Boolean> msg) {
                        if (msg.getSuccess() == 0)
                            repairView.submitSuccess(msg.getData());
                        else
                            repairView.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }

    @Override
    public void complete(String key, ResponseInfo info, JSONObject response) {
        completeCount++;
        if (imagePathList.size() == 4 && "000000".equals(imagePathList.get(0))) {
            if (completeCount == imagePathList.size()) {
                uploadImagePath = uploadImagePath.substring(0, uploadImagePath.length() - 1);
                repairView.submit();
                photoPosition = 0;
                Bimp.tempSelectBitmap.clear();
            }
        } else {
            if (completeCount == imagePathList.size() - 1) {
                uploadImagePath = uploadImagePath.substring(0, uploadImagePath.length() - 1);
                repairView.submit();
                photoPosition = 0;
                Bimp.tempSelectBitmap.clear();
            }
        }
    }


}
