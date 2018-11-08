package com.nacity.college.property;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nacity.college.R;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.CommonDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.KeyboardUtil;
import com.nacity.college.base.impl.FragmentPermissionListener;
import com.nacity.college.base.utils.image.Bimp;
import com.nacity.college.property.model.ParkingModel;
import com.nacity.college.property.presenter.ParkingPresenter;
import com.nacity.college.property.view.ParkingView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 *  Created by usb on 2017/10/23.
 */

public class ParkingApplyActivity extends BaseActivity implements ParkingView, FragmentPermissionListener {
    @BindView(R.id.iv_03)
    ImageView    iv03;
    @BindView(R.id.tv_upload_image)
    TextView    tvUpload;
    @BindView(R.id.view_layout)
    View    view_layout;
    @BindView(R.id.iv_02)
    ImageView    iv02;
    @BindView(R.id.other)
    EditText     other;
    @BindView(R.id.tip)
    TextView     mTip;
    @BindView(R.id.iv_01)
    ImageView    iv01;
    @BindView(R.id.name)
    EditText     name;
    @BindView(R.id.carNo)
    EditText     carNo;
    @BindView(R.id.park_area)
    EditText     parkArea;
    @BindView(R.id.term)
    EditText     term;
    @BindView(R.id.rp_image)
    LinearLayout serviceImage;
    private  String parkingType ="2";
    private ParkingModel model ;
    private ArrayList<String> imagePaths = new ArrayList<>();

    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private KeyboardUtil keyboardUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_apply);
        setTitle(Constant.PARKING_APPLY);
        model = new ParkingPresenter(this);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        keyboardUtil = new KeyboardUtil(this, carNo);
//        carNo.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                keyboardUtil.hideSoftInputMethod();
//                keyboardUtil.showKeyboard();
////                if(s.length()==0){
////                    keyboardUtil.changeKeyboard(false);
////                }
////                if (s.length() >=10) {
////                    keyboardUtil.hideKeyboard();
////                }
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
        carNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("2222", "onTouch: 左边");
                carNo.requestFocus();
                keyboardUtil.hideSoftInputMethod();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                keyboardUtil.showKeyboard();
                return false;
            }
        });
        carNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
//                    keyboardUtil.hideSoftInputMethod();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                keyboardUtil.showKeyboard();
                } else {
                    // 此处为失去焦点时的处理内容
                    keyboardUtil.hideKeyboard();
                }
            }
        });
        other.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTip.setText("还可以输入" + (200 - s.length()) + "字");
                if (s.length() == 200) {
                    Toast.makeText(ParkingApplyActivity.this, "你只能输入200字哦", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.type_01,R.id.type_02,R.id.type_03,R.id.upload_image,R.id.manual_input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.type_01:
                Glide.with(MainApp.mContext).load(R.drawable.circle_select).into(iv01);
                Glide.with(MainApp.mContext).load(R.drawable.circle_unselect).into(iv02);
                Glide.with(MainApp.mContext).load(R.drawable.circle_unselect).into(iv03);
                parkingType="0";
                iv01.setClickable(false);
                iv02.setClickable(true);
                iv03.setClickable(true);
                break;
            case R.id.type_02:
                Glide.with(MainApp.mContext).load(R.drawable.circle_unselect).into(iv01);
                Glide.with(MainApp.mContext).load(R.drawable.circle_select).into(iv02);
                Glide.with(MainApp.mContext).load(R.drawable.circle_unselect).into(iv03);
                parkingType="2";
                iv01.setClickable(true);
                iv02.setClickable(false);
                iv03.setClickable(true);
                break;
            case R.id.type_03:
                Glide.with(MainApp.mContext).load(R.drawable.circle_unselect).into(iv01);
                Glide.with(MainApp.mContext).load(R.drawable.circle_unselect).into(iv02);
                Glide.with(MainApp.mContext).load(R.drawable.circle_select).into(iv03);
                parkingType="1";
                iv01.setClickable(true);
                iv02.setClickable(true);
                iv03.setClickable(false);
                break;
            case R.id.upload_image:
               //照片处理
                getPermissionPhoto(Manifest.permission.CAMERA, view, this);
                break;
            case R.id.manual_input:
               //申请
                if (checking())
                return;
                if (imagePaths.size() > 0)
                    model.getUpImageToken();
                else
                model.submitParking(name.getText().toString(),carNo.getText().toString(),parkingType,parkArea.getText().toString(),term.getText().toString(),other.getText().toString());
                loadingShow();
                break;

        }

    }
    private boolean checking() {
        if (TextUtils.isEmpty(name.getText())) {
            Toasty.info(this, "请输入姓名").show();
            return true;
        }else if (TextUtils.isEmpty(carNo.getText())) {
            Toasty.info(this, "请输入车牌号").show();
            return true;
        }
        else if (imagePaths.size()==0) {
            Toasty.info(this, "请上传行驶证").show();
            return true;
        }
        else if (TextUtils.isEmpty(parkArea.getText())) {
            Toasty.info(this, "请输入车位区域").show();
            return true;
        }
        else if (TextUtils.isEmpty(term.getText())) {
            Toasty.info(this, "请输入申请期限").show();
            return true;
        }
        return false;
    }

    @Override
    public void submit() {
        model.submitParking(name.getText().toString(),carNo.getText().toString(),parkingType,parkArea.getText().toString(),term.getText().toString(),other.getText().toString());
    }

    @Override
    public void submitSuccess() {
        loadingDismiss();
        final CommonDialog dialog = new CommonDialog(this, R.layout.dailog_decoratian_input, R.style.myDialogTheme);
        Button btnAdd = (Button) dialog.findViewById(R.id.confirm);
        btnAdd.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
//        Toast.makeText(ParkingApplyActivity.this, "车位申请成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getTokenSuccess(String token) {
        loadingShow();
        model.uploadImage(imagePaths, token);
    }

    @Override
    public void accept(String permission, View view) {
        if (Manifest.permission.CAMERA.equals(permission))
            getPermissionPhoto(Manifest.permission.READ_EXTERNAL_STORAGE, view, this);
        if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)) {
//            if ("000000".equals(view.getTag())) {
            PhotoPickerIntent intent = new PhotoPickerIntent(appContext);
            intent.setSelectModel(SelectModel.MULTI);
            intent.setShowCarema(true); // 是否显示拍照
            intent.setMaxTotal(9); // 最多选择照片数量，默认为6
            intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
            startActivityForResult(intent, REQUEST_CAMERA_CODE);
//            } else {
//                PhotoPreviewIntent intent = new PhotoPreviewIntent(appContext);
//                intent.setCurrentItem(0);
//                intent.setPhotoPaths(imagePaths);
//                if ("000000".equals(imagePaths.get(imagePaths.size() - 1))) {
//                    imagePaths.remove(imagePaths.get(imagePaths.size() - 1));
//                }
//                startActivityForResult(intent, REQUEST_PREVIEW_CODE);
//            }
        }
    }

    @Override
    public void refuse(String permission) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:

//                    imagePaths = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
//                    if (imagePaths.size() < 4)
//                        imagePaths.add("000000");
                    ArrayList<String> handlerImageList=new ArrayList<>();
                    imagePaths = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
//                    if (imagePaths.size() < 4)
//                        imagePaths.add("000000");
                    for (String mPath:imagePaths){
                        try{
                            Bimp.getImageUri(mPath, false);
                            handlerImageList.add(mPath);
                        }catch (Exception e){
                            System.out.println();
                        }
                    }
                    imagePaths=handlerImageList;

                    if (imagePaths.size()>0)
                        setImageLayout(imagePaths);
                    setImageLayout(imagePaths);
                    Log.i("222", "onActivityResult:1 "+imagePaths.toString());

                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    Log.i("222", "onActivityResult:2 "+imagePaths.toString());

                    imagePaths = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
//                    if (imagePaths.size() < 4)
//                        imagePaths.add("000000");
                    setImageLayout(imagePaths);
                    break;
            }
        }

    }
    //设置上传图片的布局
    private void setImageLayout(ArrayList<String> imagePaths) {
        if(imagePaths.size()==0){
            view_layout.setVisibility(View.GONE);
            tvUpload.setText("上传照片");
        }
        else{
            view_layout.setVisibility(View.VISIBLE);
            tvUpload.setText("继续添加");
        }
        serviceImage.removeAllViews();
        for (int i = 0; i < imagePaths.size(); i++) {
           View view= LayoutInflater.from(this).inflate(R.layout.parking_photo_view, null);
            ImageView image=(ImageView)view.findViewById(R.id.image);
            ImageView imageDelete=(ImageView)view.findViewById(R.id.image_delete);
//            PhotoView photoView = new PhotoView(appContext);
//            photoView.setScaleType(ImageView.ScaleType.CENTER);
            int width = getScreenWidth()*165/1000;
            int margin = getScreenWidth()*1/100;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            params.leftMargin = margin;
            params.rightMargin = margin;
//            if ("000000".equals(imagePaths.get(i)))
//                photoView.setBackgroundResource(R.drawable.selector_capture);
//            else
            view.setLayoutParams(params);
            Glide.with(appContext).load(imagePaths.get(i)).into(image);
//            image.setTag(imagePaths.get(i));
            imageDelete.setTag(imagePaths.get(i));
            image.setTag(i);
            imageDelete.setOnClickListener(v -> {
                for (int j = 0; j <imagePaths.size() ; j++) {
                    if(v.getTag()==imagePaths.get(j)){
                        imagePaths.remove(j);
                        serviceImage.removeViewAt(j);
                    }
                }
//                imagePaths.remove(v.getTag());
//                setImageLayout(imagePaths);
                if(imagePaths.size()==0){
                    view_layout.setVisibility(View.GONE);
                    tvUpload.setText("上传照片");
                }
                else{
                    view_layout.setVisibility(View.VISIBLE);
                    tvUpload.setText("继续添加");
                }
                    });
            image.setOnClickListener(v -> {
//                getPermissionPhoto(Manifest.permission.CAMERA, v, this);
                PhotoPreviewIntent intent = new PhotoPreviewIntent(appContext);
                intent.setCurrentItem((int)v.getTag());
                intent.setPhotoPaths(imagePaths);
//                if ("000000".equals(imagePaths.get(imagePaths.size() - 1))) {
//                    imagePaths.remove(imagePaths.get(imagePaths.size() - 1));
//                }
                startActivityForResult(intent, REQUEST_PREVIEW_CODE);
            });
            serviceImage.addView(view);
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK)
            Bimp.tempSelectBitmap.clear();
        return super.onKeyDown(keyCode, event);
    }
}
