package com.nacity.college.circle;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.college.common_libs.domain.circle.NeighborPostTypeTo;
import com.nacity.college.R;
import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.impl.FragmentPermissionListener;
import com.nacity.college.base.utils.image.Bimp;
import com.nacity.college.circle.presenter.PostPresenter;
import com.nacity.college.circle.presenter.impl.PostPresenterImpl;
import com.nacity.college.circle.view.PostView;
import com.nacity.college.main.MainActivity;
import com.nacity.college.share.ShareDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import rx.Observable;

/**
 * Created by xzz on 2017/7/1.
 **/

public class PostActivity extends BaseActivity implements PostView, FragmentPermissionListener {
    @BindView(R.id.grid_view)
    GridLayout gridView;

    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    @BindView(R.id.post_image_number_tip)
    TextView postImageNumberTip;
    @BindView(R.id.post_content)
    EditText postContent;
    @BindView(R.id.publish_type_name)
    TextView publishTypeName;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private List<NeighborPostTypeTo> typeList;
    private String typeSid;
    private PostPresenter presenter;
    private int tpiIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        setTitle(Constant.POST);
        presenter = new PostPresenterImpl(this);
        getIntentData();


    }

    private void getIntentData() {
        typeList = (List<NeighborPostTypeTo>) getIntent().getSerializableExtra("TypeList");
        typeSid = typeList.get(getIntent().getIntExtra("Index",0)).getTypeIndex();
        imagePaths.add("000000");//000000为添加图片背景设置的路径
        initView(imagePaths);
        publishTypeName.setText(typeList.get(getIntent().getIntExtra("Index",0)).getName());
    }

    private void initView(ArrayList<String> imagePaths) {


        gridView.removeAllViews();
        for (int i = 0; i < imagePaths.size(); i++) {
            PhotoView photoView = new PhotoView(appContext);
            photoView.setScaleType(ImageView.ScaleType.CENTER);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.height = (int) (getScreenWidth() * 0.3);
            layoutParams.width = (int) (getScreenWidth() * 0.3);
            layoutParams.setMargins(0, 0, (int) (getScreenWidth() * 0.01333), (int) (getScreenWidth() * 0.01333));
            if ("000000".equals(imagePaths.get(i)))
                photoView.setBackgroundResource(R.drawable.circle_add_pic_icon);
            else
                Glide.with(appContext).load(imagePaths.get(i)).into(photoView);
            photoView.setLayoutParams(layoutParams);
            photoView.setTag(imagePaths.get(i));
            photoView.setOnClickListener(v -> getPermissionPhoto(Manifest.permission.CAMERA, v, this));
            gridView.addView(photoView);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:


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
                    if (imagePaths.size() < 9)
                        imagePaths.add("000000");
                    if (imagePaths.size() >= 2)
                        postImageNumberTip.setVisibility(View.GONE);
                    else
                        postImageNumberTip.setVisibility(View.VISIBLE);

                    initView(imagePaths);

                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    imagePaths = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                    if (imagePaths.size() < 9)
                        imagePaths.add("000000");
                    if (imagePaths.size() >= 2)
                        postImageNumberTip.setVisibility(View.GONE);
                    else
                        postImageNumberTip.setVisibility(View.VISIBLE);
                    initView(imagePaths);
                    break;
            }
        }
    }

    @OnClick({R.id.submit, R.id.select_type_layout})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.submit:
                loadingShow();
                if (imagePaths.size() == 1)
                    presenter.addPost(postContent, typeSid);
                else
                    presenter.UploadImage("", imagePaths);

                break;
            case R.id.select_type_layout:
                selectTypeDialog();
                break;
        }
    }

    @Override
    public void showWarn(String message) {
        loadingDismiss();
        Toasty.warning(appContext, message).show();
    }

    @Override
    public void enterLifeActivity() {
        loadingDismiss();
        Intent intent = new Intent(appContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Index", 1);
        startActivity(intent);
        finish();
    }

    @Override
    public void addPost() {
        presenter.addPost(postContent, typeSid);
    }

    @Override
    public void accept(String permission, View view) {
        if (Manifest.permission.CAMERA.equals(permission))
            getPermissionPhoto(Manifest.permission.READ_EXTERNAL_STORAGE, view, PostActivity.this);
        if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)) {
            if ("000000".equals(view.getTag())) {
                PhotoPickerIntent intent = new PhotoPickerIntent(appContext);
                intent.setSelectModel(SelectModel.MULTI);
                intent.setShowCarema(true); // 是否显示拍照
                intent.setMaxTotal(9); // 最多选择照片数量，默认为6
                intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            } else {
                PhotoPreviewIntent intent = new PhotoPreviewIntent(appContext);
                intent.setCurrentItem(0);
                intent.setPhotoPaths(imagePaths);
                if ("000000".equals(imagePaths.get(imagePaths.size() - 1))) {
                    imagePaths.remove(imagePaths.get(imagePaths.size() - 1));
                }
                startActivityForResult(intent, REQUEST_PREVIEW_CODE);
            }
        }
    }

    @Override
    public void refuse(String permission) {

    }

    private void selectTypeDialog() {
        ShareDialog dialog = new ShareDialog(appContext, R.layout.circle_select_publish_type, R.style.downDialogTheme);

        dialog.setCanceledOnTouchOutside(true);
        dialog.findViewById(R.id.close).setOnClickListener(v -> dialog.dismiss());
        GridLayout typeLayout = (GridLayout) dialog.findViewById(R.id.type_layout);
        Observable.from(typeList).filter(neighborPostTypeTo -> !TextUtils.isEmpty(neighborPostTypeTo.getTypeIndex())).subscribe(typeTo -> {
            View typeView = View.inflate(appContext, R.layout.circle_select_post_type_item, null);
            ((TextView) typeView.findViewById(R.id.type_name)).setText(typeTo.getName());
            Glide.with(MainApp.mContext).load(MainApp.getImagePath(typeTo.getPicUrl())).into((ImageView) typeView.findViewById(R.id.type_image));
            typeView.setTag(typeTo);
            typeView.setOnClickListener(v -> {
                NeighborPostTypeTo typeTo1 = (NeighborPostTypeTo) v.getTag();
                publishTypeName.setText(typeTo1.getName());
                typeSid = typeTo1.getTypeIndex();
                dialog.dismiss();
            });
            typeLayout.addView(typeView);
        });
        dialog.show();

    }


}
