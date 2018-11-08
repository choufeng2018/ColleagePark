package com.nacity.college.common.presenter;

import com.college.common_libs.api.ApiClient;
import com.college.common_libs.api.PropertyApi;
import com.college.common_libs.domain.MessageTo;
import com.nacity.college.MainApp;
import com.nacity.college.base.impl.LoadCallback;
import com.nacity.college.base.utils.image.Bimp;
import com.nacity.college.base.utils.image.ImageItem;
import com.nacity.college.common.model.UploadImageModel;
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
 * Created by xzz on 2017/9/6.
 **/

public class UploadImagePresenter implements UploadImageModel, UpCompletionHandler {
    private int completeCount;  //上传图片完成次数
    private int photoPosition;
    private String uploadImagePath = "";
    private List<String> imagePathList = new ArrayList<>();
    private LoadCallback<String> mCallback;
    private int mPhotoNumber;//设定的最大上传数量

    @Override
    public void uploadImage(ArrayList<String> imagePathList, int photoNumber, LoadCallback<String> callback) {
        PropertyApi api = ApiClient.create(PropertyApi.class);
        api.getQnToken().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new Observer<MessageTo<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e+"error");
                        callback.fail(e);
                    }

                    @Override
                    public void onNext(MessageTo<String> msg) {
                        System.out.println(msg+"获取token成功");
                        if (msg.getSuccess() == 0) {
                            uploadImage(imagePathList, msg.getData());
                            mCallback = callback;
                            mPhotoNumber=photoNumber;
                        } else
                            callback.showErrorMessage(msg.getMessage());
                    }
                }
        );
    }

    private void uploadImage(List<String> pathList, String token) {
        imagePathList = pathList;
        for (String mPath : pathList) {
            if (!mPath.equals("000000")) {
                String uuidPath = UUID.randomUUID().toString();
                uploadImagePath = uploadImagePath + MainApp.getImagePath(uuidPath)+ ";";
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
        if (imagePathList.size() == mPhotoNumber &&(mPhotoNumber==1||"000000".equals(imagePathList.get(0)))) {
            if (completeCount == imagePathList.size()) {
                uploadImagePath = uploadImagePath.substring(0, uploadImagePath.length() - 1);
                mCallback.success(uploadImagePath);
                photoPosition = 0;
                Bimp.tempSelectBitmap.clear();
            }
        } else {
            if (completeCount == imagePathList.size() - 1) {
                uploadImagePath = uploadImagePath.substring(0, uploadImagePath.length() - 1);
                mCallback.success(uploadImagePath);
                photoPosition = 0;
                Bimp.tempSelectBitmap.clear();
            }
        }
    }
}
