package com.nacity.college.property.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.college.common_libs.domain.property.ParkingApplyTo;
import com.college.common_libs.domain.property.ParkingParam;
import com.nacity.college.MainApp;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.utils.image.Bimp;
import com.nacity.college.base.utils.image.ImageItem;
import com.nacity.college.property.model.ParkingModel;
import com.nacity.college.property.view.ParkingView;
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
 *  Created by usb on 2017/10/23.
 */

public class ParkingPresenter extends BasePresenter implements ParkingModel,UpCompletionHandler {
    private ParkingView parkingView;
    private int completeCount;  //上传图片完成次数
    private int photoPosition;
    private PropertyApi api = ApiClient.create(PropertyApi.class);

    private String       uploadImagePath = "";
    private List<String> imagePathList   = new ArrayList<>();
    public ParkingPresenter(ParkingView parkingView) {
        this.parkingView = parkingView;
    }

    @Override
    public void submitParking(String userName, String carNo, String loadType, String loadArea, String applyDead, String otherDesc) {
        api = ApiClient.create(PropertyApi.class);
        ParkingParam param=new ParkingParam();
        param.setGardenId(apartmentInfo.getSid());
        param.setUserId(userInfo.getSid());
        param.setUserName(userName);
        param.setCarNo(carNo);
        param.setDriverImgs(uploadImagePath);
        param.setLoadType(loadType);
        param.setLoadArea(loadArea);
        param.setApplyDead(applyDead);
        param.setOtherDesc(otherDesc);
        api.applyPark(param).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Observer<MessageTo<ParkingApplyTo>>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        parkingView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getSuccess() == 0) {
                            parkingView.submitSuccess();

                        }
                        else
                            parkingView.showErrorMessage(msg.getMessage());

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
                        parkingView.loadError(e);
                    }

                    @Override
                    public void onNext(MessageTo<String> msg) {
                        if (msg.getSuccess() == 0) {
                            parkingView.getTokenSuccess(msg.getData());
                        } else
                            parkingView.showErrorMessage(msg.getMessage());
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
    public void complete(String key, ResponseInfo info, JSONObject response) {
        completeCount++;
//        if (imagePathList.size() == 9 && "000000".equals(imagePathList.get(0))) {
//            if (completeCount == imagePathList.size()) {
//                uploadImagePath = uploadImagePath.substring(0, uploadImagePath.length() - 1);
//                parkingView.submit();
//                photoPosition = 0;
//                Bimp.tempSelectBitmap.clear();
//            }
//        } else {
        if (completeCount == imagePathList.size() ) {
            uploadImagePath = uploadImagePath.substring(0, uploadImagePath.length() - 1);
            parkingView.submit();
            photoPosition = 0;
            Bimp.tempSelectBitmap.clear();
        }
//        }
    }
}
