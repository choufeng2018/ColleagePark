package com.nacity.college.myself;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
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
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.CommonDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.impl.PermissionListener;
import com.nacity.college.base.info.ApartmentInfoHelper;
import com.nacity.college.base.info.UserInfoHelper;
import com.nacity.college.base.utils.DateUtil;
import com.nacity.college.base.utils.FileUtil;
import com.nacity.college.base.utils.SoftKeyBoardListener;
import com.nacity.college.common.model.UpdateUserInfoModel;
import com.nacity.college.common.presenter.UpdateUserInfoPresenter;
import com.nacity.college.login.SelectCityActivity;
import com.nacity.college.login.view.UpdateUserView;
import com.soundcloud.android.crop.Crop;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by xzz on 2017/9/11.
 **/

public class PersonCenterActivity extends BaseActivity<UserInfoTo> implements PermissionListener, UpdateUserView<UserInfoTo>, OnDateSetListener {
    @BindView(R.id.head_image)
    RoundedImageView headImage;
    @BindView(R.id.nick_name)
    EditText nickName;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.apartment_name)
    TextView apartmentName;
    @BindView(R.id.real_name)
    EditText realName;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.account)
    TextView account;

    private String photoPath = "";
    private String userImage = "";
    private UpdateUserInfoModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        ButterKnife.bind(this);
        setTitle(Constant.PERSON_INFO);
        initView();
        model = new UpdateUserInfoPresenter(this);
        EventBus.getDefault().register(this);
        setKeyBoardListener();

    }

    private void initView() {
        nickName.setText(userInfo.getUserInfoTo().getNickname());
        realName.setText(userInfo.getUserInfoTo().getRealName());

        account.setText(userInfo.getUserInfoTo().getUserMobile());
        sex.setText(userInfo.getUserInfoTo().getSex());
        if (!TextUtils.isEmpty(userInfo.getUserInfoTo().getUserBirth()))
            birthday.setText(DateUtil.formatDateString(DateUtil.mDateFormatStringLine, userInfo.getUserInfoTo().getUserBirth()));
        Glide.with(MainApp.mContext).load(MainApp.getImagePath(userInfo.getUserInfoTo().getUserPic())).into(headImage);
        if (userInfo.getUserInfoTo() != null && userInfo.getUserInfoTo().getCommonGarden() != null && userInfo.getUserInfoTo().getCommonGarden().getGardenName() != null)
            apartmentName.setText(apartmentInfo.getGardenName());
    }

    @OnClick({R.id.head_image_layout, R.id.sex_layout, R.id.apartment_layout, R.id.birthday_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.head_image_layout:
                uploadHeadImageDialog();
                break;
            case R.id.sex_layout:
                selectSexDialog();
                break;
            case R.id.apartment_layout:
                Intent intent = new Intent(appContext, SelectCityActivity.class);
                startActivity(intent);
                goToAnimation(1);
                break;
            case R.id.birthday_layout:
                showBirthday();
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

    @Override
    public void upLoadImageSuccess(String imagePath) {

        Glide.with(MainApp.mContext).load(MainApp.getImagePath(imagePath)).into(headImage);
        UserInfoTo userInfoTo = userInfo.getUserInfoTo();
        userInfoTo.setUserPic(imagePath);
        userInfo.updateUser(userInfoTo);
        userImage = imagePath;
        model.updateUserInfo(userImage, nickName.getText().toString(), realName.getText().toString(), birthday.getText().toString(), sex.getText().toString(), userInfo.getUserInfoTo().getUpdateGardenId());
    }

    public void showBirthday() {
        long tenYears = 60L * 365 * 1000 * 60 * 60 * 24L;
        long oneYears = 365 * 1000 * 60 * 60 * 24L;
        TimePickerDialog mDialogAll = new TimePickerDialog.Builder().setCallBack(this).
                setCancelStringId(Constant.CANCEL).
                setSureStringId(Constant.CONFIRM).
                setTitleStringId(Constant.SELECT_BIRTHDAY).
                setMonthText(Constant.MONTH).setDayText(Constant.DAY).setMinMillseconds(System.currentTimeMillis() - tenYears).setMaxMillseconds(System.currentTimeMillis() + oneYears).setCurrentMillseconds(System.currentTimeMillis()).
                setCyclic(false).setThemeColor(Color.parseColor("#6d75a4")).setType(Type.YEAR_MONTH_DAY).setWheelItemTextNormalColor(R.color.timetimepicker_default_text_color).setWheelItemTextSelectorColor(R.color.timepicker_toolbar_bg).setWheelItemTextSize(12).build();
        mDialogAll.show(getSupportFragmentManager(), "");

    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millSeconds) {
        birthday.setText(DateUtil.longToDate(millSeconds, DateUtil.mDateFormatStringLine));
        model.updateUserInfo(userImage, nickName.getText().toString(), realName.getText().toString(), birthday.getText().toString(), sex.getText().toString(), userInfo.getUserInfoTo().getUpdateGardenId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getUserInfoChange(Event<String> event) {
        if ("UpdateUserInfo".equals(event.getType())) {
            apartmentInfo = ApartmentInfoHelper.getInstance(MainApp.mContext);
            if (!apartmentInfo.getSid().equals(userInfo.getUserInfoTo().getUpdateGardenId()))
                model.updateUserInfo(userImage, nickName.getText().toString(), realName.getText().toString(), birthday.getText().toString(), sex.getText().toString(), apartmentInfo.getSid());
            else
                userInfo = UserInfoHelper.getInstance(MainApp.mContext);
            initView();
            apartmentName.setText(event.getMode());
        }
    }

    private void setKeyBoardListener() {

        SoftKeyBoardListener.setListener(PersonCenterActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
            }

            @Override
            public void keyBoardHide(int height) {
                if (TextUtils.isEmpty(nickName.getText().toString())) {
                    nickName.setText(userInfo.getUserInfoTo().getNickname());

                }
                if (TextUtils.isEmpty(realName.getText().toString())) {
                    realName.setText(userInfo.getUserInfoTo().getName());

                }

                if (!nickName.getText().toString().equals(userInfo.getUserInfoTo().getNickname())) {
                    changeNameDialog();
                    return;
                }

                if (!realName.getText().toString().equals(userInfo.getUserInfoTo().getRealName())) {
                    changeNameDialog();

                }
            }
        });
    }

    private void changeNameDialog() {
        CommonDialog dialog = new CommonDialog(this, R.layout.change_name_dialog, R.style.myDialogTheme);
        dialog.findViewById(R.id.btn_add).setOnClickListener(v -> {
            model.updateUserInfo(userImage, nickName.getText().toString(), realName.getText().toString(), birthday.getText().toString(), sex.getText().toString(), userInfo.getUserInfoTo().getUpdateGardenId());
            dialog.dismiss();
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        dialog.setCancelable(true);

    }

    private void selectSexDialog() {
        CommonDialog dialog = new CommonDialog(this, R.layout.dialog_sex_select, R.style.EditDialogDown);
        dialog.findViewById(R.id.select_man).setOnClickListener(v -> {
            sex.setText(Constant.MAN);
            dialog.dismiss();
            model.updateUserInfo(userImage, nickName.getText().toString(), realName.getText().toString(), birthday.getText().toString(), sex.getText().toString(), userInfo.getUserInfoTo().getUpdateGardenId());
        });
        dialog.findViewById(R.id.select_woman).setOnClickListener(v -> {
            sex.setText(Constant.WOMAN);
            dialog.dismiss();
            model.updateUserInfo(userImage, nickName.getText().toString(), realName.getText().toString(), birthday.getText().toString(), sex.getText().toString(), userInfo.getUserInfoTo().getUpdateGardenId());
        });
        dialog.show();
        dialog.setCancelable(true);
    }

    @Override
    public void getDataSuccess(UserInfoTo data) {
        userInfo.updateUser(data);
    }
}
