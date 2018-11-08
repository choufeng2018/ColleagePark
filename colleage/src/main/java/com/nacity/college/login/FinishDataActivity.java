package com.nacity.college.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.user.UserInfoTo;
import com.nacity.college.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nacity.college.MainApp;
import com.nacity.college.base.ActivityUitl;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.CommonDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.impl.PermissionListener;
import com.nacity.college.base.utils.DateUtil;
import com.nacity.college.base.utils.FileUtil;
import com.nacity.college.common.model.UpdateUserInfoModel;
import com.nacity.college.common.presenter.UpdateUserInfoPresenter;
import com.nacity.college.login.view.UpdateUserView;
import com.nacity.college.main.MainActivity;
import com.soundcloud.android.crop.Crop;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by xzz on 2017/9/20.
 **/

public class FinishDataActivity extends BaseActivity<UserInfoTo> implements UpdateUserView<UserInfoTo>, PermissionListener {

    @BindView(R.id.nick_name)
    EditText nickName;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.head_image)
    RoundedImageView headImage;
    private UpdateUserInfoModel model;
    private String photoPath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_data);
        ButterKnife.bind(this);
        model = new UpdateUserInfoPresenter(this);
    }


    @Override
    public void upLoadImageSuccess(String headImagePath) {
        photoPath = headImagePath;
        Glide.with(MainApp.mContext).load(MainApp.getImagePath(headImagePath)).into(headImage);
    }

    @OnClick({R.id.upload_head_image, R.id.sex_layout, R.id.finish, R.id.head_image,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sex_layout:
                selectSexDialog();
                break;
            case R.id.finish:
                if (TextUtils.isEmpty(nickName.getText().toString())) {
                    showWarnInfo(Constant.NICK_NAME_NO_EMPTY);
                    return;
                }
                loadingShow();
                model.updateUserInfo(photoPath, nickName.getText().toString(), "", null, sex.getText().toString(), userInfo.getUserInfoTo().getUpdateGardenId());

                break;
            case R.id.head_image:
            case R.id.upload_head_image:
                uploadHeadImageDialog();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void uploadHeadImageDialog() {
        CommonDialog alertDialog = new CommonDialog(this, getScreenWidth(), (int) (getScreenWidth() * 0.2), R.layout.dialog_pic_type, R.style.EditDialogDown);
        alertDialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.findViewById(R.id.btn_camera).setOnClickListener(v -> {
            alertDialog.dismiss();
            getPermission(Manifest.permission.CAMERA, this);
        });
        alertDialog.findViewById(R.id.btn_album).setOnClickListener(v -> {
            alertDialog.dismiss();
            getPermission(Manifest.permission.READ_EXTERNAL_STORAGE, this);
        });
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public void accept(String permission) {
        if (permission.equals(Manifest.permission.CAMERA)) {
            openCamera();
        } else if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)) {
            enterAlbum();
        }

    }

    @Override
    public void refuse(String permission) {

    }

    private void openCamera() {
        if (FileUtil.IsExistsSDCard()) {
            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File mOutPhotoFile = new File(MainApp.getCacheImagePath(),
                    DateUtil.getDateString(DateUtil.mFormatTimeCamara) + ".png");
            photoPath = mOutPhotoFile.getAbsolutePath();
            Uri uri = Uri.fromFile(mOutPhotoFile);
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intentCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intentCamera, Constant.RESULT_CAMERA);
        } else {
            Toast.makeText(this, Constant.NO_SD_CARD, Toast.LENGTH_LONG).show();
        }


    }

    private void enterAlbum() {
        if (FileUtil.IsExistsSDCard()) {
            // 打开相册
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra("return_data", true);
            startActivityForResult(intent, Constant.RESULT_SDCARD);
        } else {
            Toast.makeText(appContext, Constant.NO_SD_CARD, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.RESULT_SDCARD:
                    Uri uri = data.getData();
                    String mPhotoPath = FileUtil.getPath(this, uri);

                    if (!TextUtils.isEmpty(mPhotoPath)) {

                        beginCrop(uri);
                    }

                    break;
                case Constant.RESULT_CAMERA:

                    Uri uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                    if (uri2 != null && photoPath != null) {
                        Uri uri1 = Uri.fromFile(new File(photoPath));
                        beginCrop(uri1);

                    } else {
                        Toast.makeText(this, "sdcard无效或没有插入", Toast.LENGTH_LONG).show();
                    }
                    break;
                case Crop.REQUEST_CROP:
                    handleCrop(resultCode, data);
                    break;

            }
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "croppedd"));
        Crop.of(source, destination).asSquare().start(this);

    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            model.uploadHeadImage(FileUtil.getPath(appContext, Crop.getOutput(result)), 1);


        }
    }

    private void selectSexDialog() {
        CommonDialog dialog = new CommonDialog(this, getScreenWidth(), (int) (getScreenWidth() * 0.075), R.layout.dialog_sex_select, R.style.EditDialogDown);
        dialog.findViewById(R.id.select_man).setOnClickListener(v -> {
            sex.setText(Constant.MAN);
            dialog.dismiss();
        });
        dialog.findViewById(R.id.select_woman).setOnClickListener(v -> {
            sex.setText(Constant.WOMAN);
            dialog.dismiss();
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public void getDataSuccess(UserInfoTo data) {
        loadingDismiss();
        userInfo.updateUser(data);
        userInfo.updateLogin(true);
        Intent intent = new Intent(appContext, MainActivity.class);
        startActivity(intent);
        finish();
        Observable.from(ActivityUitl.activityList).subscribe(Activity::finish);
        goToAnimation(1);

    }
}
