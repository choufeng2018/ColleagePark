package com.nacity.college.circle.presenter.impl;

import android.text.TextUtils;
import android.widget.EditText;

import com.college.common_libs.domain.circle.NeighborPostParam;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.college.common_libs.domain.user.UserInfoTo;
import com.nacity.college.MainApp;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.Constant;
import com.nacity.college.base.utils.image.Bimp;
import com.nacity.college.base.utils.image.ImageItem;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.circle.interactor.PostInteractor;
import com.nacity.college.circle.interactor.impl.PostInteractorImpl;
import com.nacity.college.circle.presenter.CommonInteractor;
import com.nacity.college.circle.presenter.PostPresenter;
import com.nacity.college.circle.view.PostView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by xzz on 2017/7/1.
 **/

public class PostPresenterImpl extends BasePresenter implements PostPresenter, LoadingCallback<NeighborPostTo>, CommonInteractor.TokenFinish, UpCompletionHandler, CommonInteractor.UpImageFinish {
    private PostView postView;
    private PostInteractor interactor;
    private CommonInteractor commonInteractor;
    private String uploadImagePath = "";
    private ArrayList<String> pathList;
    private int completeCount;  //上传图片完成次数
    private int photoPosition;

    public PostPresenterImpl(PostView postView) {
        this.postView = postView;
        interactor = new PostInteractorImpl();
        commonInteractor = new CommonInteractorImpl();
    }

    @Override
    public void addPost(EditText postContent, String typeSid) {
        if (TextUtils.isEmpty(postContent.getText())) {
            postView.showWarn(Constant.POST_CONTENT_NO_EMPTY);
            return;
        }

        NeighborPostParam param = new NeighborPostParam();

        param.setCreateUserId(userInfo.getSid());
        param.setContent(postContent.getText().toString());
        param.setTypeId(typeSid);
        param.setGardenId(apartmentInfo.getSid());
        param.setImages(uploadImagePath);
        System.out.println(param + "postParam");
        interactor.lifePost(param, this);


    }


    /**
     * 发帖成功
     */
    @Override
    public void success(NeighborPostTo postTo) {
        postView.enterLifeActivity();
    }

    @Override
    public void upImageSuccess(UserInfoTo userInfo) {


    }

    @Override
    public void message(String message) {
        postView.showWarn(message);
    }

    @Override
    public void error(Throwable error) {
        postView.loadError(error);
    }

    @Override
    public void UploadImage(String path, ArrayList<String> pathList) {
        commonInteractor.getToken(path, this);
        this.pathList = pathList;

    }


    @Override
    public void tokenSuccess(String token, String path) {
//        pathList.stream().filter(mPath -> !"000000".equals(mPath)).forEach(mPath -> {
//            String uuidPath = UUID.randomUUID().toString();
//            uploadImagePath = uploadImagePath + uuidPath + ";";
//            UploadManager uploadManager = new UploadManager();
//            uploadManager.put(mPath, uuidPath, token, this, null);
//        });
        new Thread(() -> {
            photoPosition = 0;
            for (String mPath : pathList) {
                if (!mPath.equals("000000")) {
                    String uuidPath = UUID.randomUUID().toString();
                    uploadImagePath = uploadImagePath + MainApp.MAIN_IMAGE_URI + uuidPath + ";";
                    UploadManager uploadManager = new UploadManager();
                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setImagePath(mPath);

                    Bimp.tempSelectBitmap.add(takePhoto);
                    uploadManager.put(Bimp.getImageUri(Bimp.tempSelectBitmap.get(photoPosition).getImagePath(), true), uuidPath, token, this, null);
                    photoPosition++;
                }
            }
        }).start();

    }

    @Override
    public void error() {

    }

    @Override
    public void complete(String key, ResponseInfo info, JSONObject response) {
        completeCount++;
        if (pathList.size() == 9 && "000000".equals(pathList.get(0))) {
            if (completeCount == pathList.size()) {
                uploadImagePath = uploadImagePath.substring(0, uploadImagePath.length() - 1);
                postView.addPost();
                photoPosition = 0;
                Bimp.tempSelectBitmap.clear();
            }
        } else {
            if (completeCount == pathList.size() - 1) {
                uploadImagePath = uploadImagePath.substring(0, uploadImagePath.length() - 1);
                postView.addPost();
                photoPosition = 0;
                Bimp.tempSelectBitmap.clear();
            }
        }
    }
}
