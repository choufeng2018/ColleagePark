package com.nacity.college.myself;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.college.common_libs.domain.user.UpdateTo;
import com.nacity.college.R;
import com.nacity.college.databinding.UpdateLayoutBinding;
import com.nacity.college.base.AdWebActivity;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.impl.PermissionListener;
import com.nacity.college.base.utils.AppUtil;
import com.nacity.college.main.model.UpdateModel;
import com.nacity.college.main.presenter.UpdatePresenter;
import com.nacity.college.main.view.UpdateView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xzz on 2017/9/13.
 **/

public class AboutActivity extends BaseActivity implements UpdateView, PermissionListener {

    @BindView(R.id.version)
    TextView version;
    private UpdateModel model;
    private String downLoadUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setTitle(Constant.ABOUT);
        initView();
        model = new UpdatePresenter(this);

    }

    private void initView() {
        try {
            version.setText("v " + AppUtil.getVersionName(appContext));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.service_agreement, R.id.share_load, R.id.check_update})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.service_agreement:
                intent=new Intent(appContext, AdWebActivity.class);
                intent.putExtra("Url","http://joyhomenet.com/tech_protocol.html");
                intent.putExtra("Title", Constant.SERVICE_AGREEMENT);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.share_load:
                intent = new Intent(appContext, ShareActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.check_update:
                model.getUpdateInfo();
                break;
        }
    }





    @Override
    public void showUpdateDialog(UpdateTo updateTo) {

        NiftyDialogBuilder dialog = NiftyDialogBuilder.getInstance(appContext);
        View mView=View.inflate(appContext,R.layout.update_layout,null);
        dialog.setContentView(mView);
        UpdateLayoutBinding bind = DataBindingUtil.bind(mView);
        if (updateTo.getVerCode()> AppUtil.getVersionCode(appContext)) {
            bind.noUpdateLayout.setVisibility(View.GONE);
            bind.updateLayout.setVisibility(View.VISIBLE);
//            bind.updateTitle.setText("是否升级到"+updateTo.getAppVersion()+"版本");
//            bind.updateSize.setText("大小："+updateTo.getAppSize());
            bind.updateContent.setText(updateTo.getUpdateDesc());
            bind.confirm.setOnClickListener(v -> {
                dialog.dismiss();
                getPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,this);
            });
            bind.cancel.setOnClickListener(v -> dialog.dismiss());
            downLoadUrl=updateTo.getDownloadUrl();
        }else {
           bind.noUpdateLayout.setVisibility(View.VISIBLE);
            bind.updateLayout.setVisibility(View.GONE);
            bind.updateTitle.setText("已是最新"+updateTo.getAppVersion()+"版本");
            bind.updateSize.setText("大小："+updateTo.getAppSize());
            bind.noUpdate.setOnClickListener(v -> dialog.dismiss());
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void showDownloadApp(String url){
        NiftyDialogBuilder dialog = NiftyDialogBuilder.getInstance(this);
        dialog.setContentView(R.layout.update_download_view);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        ProgressBar progressBar= (ProgressBar) dialog.findViewById(R.id.progress_bar);
        TextView updateProgress = (TextView) dialog.findViewById(R.id.update_progress);
        model.downloadApp(url,progressBar,updateProgress);
    }

    @Override
    public void installApk() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "park.apk")), "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }

    @Override
    public void accept(String permission) {
        showDownloadApp(downLoadUrl);
    }

    @Override
    public void refuse(String permission) {

    }
}
