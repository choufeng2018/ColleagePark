package com.nacity.college.property.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.nacity.college.R;
import com.kyleduo.switchbutton.SwitchButton;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.base.Constant;
import com.nacity.college.base.impl.FragmentPermissionListener;
import com.nacity.college.base.utils.image.Bimp;
import com.nacity.college.property.PropertyPraiseActivity;
import com.nacity.college.property.PropertySuggestActivity;
import com.nacity.college.property.model.PropertyPraiseModel;
import com.nacity.college.property.presenter.PropertyPraisePresenter;
import com.nacity.college.property.view.PropertyPraiseView;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by xzz on 2017/9/6.
 **/

@SuppressLint("ValidFragment")
public class PropertyPraiseFragment extends BaseFragment implements FragmentPermissionListener, PropertyPraiseView {
    @BindView(R.id.praise_content)
    EditText praiseContent;
    @BindView(R.id.praise_image)
    GridLayout praiseImage;
    @BindView(R.id.share_circle)
    SwitchButton shareCircle;
    @BindView(R.id.slide_capture)
    TextView slideCapture;
    Unbinder unbinder;
    @BindView(R.id.share_circle_layout)
    AutoRelativeLayout shareCircleLayout;
    @BindView(R.id.suggest_describe_layout)
    AutoRelativeLayout suggestDescribeLayout;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private PropertyPraiseModel model;
    private int type;


    public PropertyPraiseFragment(int type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_property_praise, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        initView();
        model = new PropertyPraisePresenter(this);
        return mRootView;
    }

    private void initView() {
        if (type==1) {
            shareCircle.setChecked(true);
            shareCircle.setVisibility(View.VISIBLE);
            suggestDescribeLayout.setVisibility(View.GONE);
        }else {
            shareCircle.setVisibility(View.GONE);
            suggestDescribeLayout.setVisibility(View.VISIBLE);
        }
        imagePaths.add("000000");//000000为添加图片背景设置的路径
        setImageLayout(imagePaths);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.submit)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (TextUtils.isEmpty(praiseContent.getText().toString())){
                    showMessage(type==1? Constant.INPUT_PRAISE_CONTENT: Constant.INPUT_SUGGEST_CONTENT);
                    return;
                }
                loadingShow();
                if (imagePaths.size() > 1)
                    model.uploadImage(imagePaths, 4);
                else
                    model.submit("", type,"", praiseContent.getText().toString());
                break;
        }
    }

    //设置上传图片的布局
    private void setImageLayout(ArrayList<String> imagePaths) {
        praiseImage.removeAllViews();
        slideCapture.setVisibility(imagePaths.size() > 1 ? View.GONE : View.VISIBLE);
        for (int i = 0; i < imagePaths.size(); i++) {
            PhotoView photoView = new PhotoView(appContext);
            photoView.setScaleType(ImageView.ScaleType.CENTER);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.height = (int) (getScreenWidth() * 0.21333);
            layoutParams.width = (int) (getScreenWidth() * 0.21333);
            layoutParams.setMargins(0, 0, i == 3 ? 0 : (int) (getScreenWidth() * 0.022222), 0);
            if ("000000".equals(imagePaths.get(i)))
                photoView.setBackgroundResource(R.drawable.selector_capture);
            else
                Glide.with(appContext).load(imagePaths.get(i)).into(photoView);
            photoView.setLayoutParams(layoutParams);
            photoView.setTag(imagePaths.get(i));
            photoView.setTag(R.id.position,i);
            photoView.setOnClickListener(v -> getPermissionPhoto(Manifest.permission.CAMERA, v, this));
            praiseImage.addView(photoView);
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:

//                    imagePaths = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);

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
                    if (imagePaths.size() < 4)
                        imagePaths.add("000000");
                    if (imagePaths.size()>0)
                        setImageLayout(imagePaths);

                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    imagePaths = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    if (imagePaths.size() < 4)
                        imagePaths.add("000000");
                    setImageLayout(imagePaths);
                    break;
            }
        }

    }


    @Override
    public void refuse(String permission) {

    }

    @Override
    public void accept(String permission, View view) {
        if (Manifest.permission.CAMERA.equals(permission))
            getPermissionPhoto(Manifest.permission.READ_EXTERNAL_STORAGE, view, this);
        if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)) {
            if ("000000".equals(view.getTag())) {
                PhotoPickerIntent intent = new PhotoPickerIntent(appContext);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(4); // 最多选择照片数量，默认为6
                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            } else {
                PhotoPreviewIntent intent = new PhotoPreviewIntent(appContext);
                intent.setCurrentItem((Integer) view.getTag(R.id.position));
                intent.setPhotoPaths(imagePaths);
                if ("000000".equals(imagePaths.get(imagePaths.size() - 1))) {
                    imagePaths.remove(imagePaths.get(imagePaths.size() - 1));
                }
                startActivityForResult(intent, REQUEST_PREVIEW_CODE);
            }
        }
    }

    @Override
    public void uploadImageSuccess(String imagePath) {
        model.submit(imagePath, type, "", praiseContent.getText().toString());
    }

    @Override
    public void submitSuccess() {
        loadingDismiss();
        successShow("提交成功");
        Intent intent=new Intent(appContext,type==1? PropertyPraiseActivity.class: PropertySuggestActivity.class);
        intent.putExtra("Index",1);
        startActivity(intent);
        goToAnimation(1);
        getActivity().finish();
    }
}
