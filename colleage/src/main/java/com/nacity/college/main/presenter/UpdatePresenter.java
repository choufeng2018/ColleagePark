package com.nacity.college.main.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.college.common_libs.domain.user.UpdateTo;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.base.Constant;
import com.nacity.college.main.model.UpdateModel;
import com.nacity.college.main.view.UpdateView;

import java.io.File;

import static com.lidroid.xutils.http.client.HttpRequest.HttpMethod.GET;


/**
 * Created by xzz on 2017/9/13.
 **/

public class UpdatePresenter extends BasePresenter implements UpdateModel {
    private UpdateView updateView;
    public UpdatePresenter(UpdateView updateView){
        this.updateView=updateView;
    }
    @Override
    public void getUpdateInfo() {
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.send(GET, "http://joyhomenet.com/appUpdate/colleage/android-update.html", new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> msg) {
                UpdateTo updateTo=  JSON.parseObject(msg.result,UpdateTo.class);
                if (updateTo!=null){
                   updateView.showUpdateDialog(updateTo);

                }

            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }


        });
    }

    @Override
    public void downloadApp(String url, ProgressBar progressBar, TextView updateProgress) {
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.download(url, Environment.getExternalStorageDirectory() + "/park.apk", new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                updateView.installApk();
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {

                progressBar.setMax((int) total);
                progressBar.setProgress((int) current);
                updateProgress.setText(Constant.CURRENT_PROGRESS+current*100/total+"%");
            }
        });
    }


    }

