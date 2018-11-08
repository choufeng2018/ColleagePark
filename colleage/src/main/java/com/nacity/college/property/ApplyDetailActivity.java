package com.nacity.college.property;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.property.DecorateApplyTo;
import com.nacity.college.R;
import com.nacity.college.databinding.ActivityDecorationDetailBinding;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.Constant;
import com.nacity.college.base.PostImageDetailActivity;
import com.nacity.college.property.model.DecorateDetailModel;
import com.nacity.college.property.presenter.DecorateDetailPresenter;
import com.nacity.college.property.view.DecorationDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 *  Created by usb on 2017/10/17.
 */

public class ApplyDetailActivity extends BaseActivity implements DecorationDetailView {

    @BindView(R.id.explain)
    TextView       explain;
    @BindView(R.id.no_date_01)
    TextView       noDate01;
    @BindView(R.id.no_date_02)
    TextView       noDate02;
    @BindView(R.id.no_date_03)
    TextView       noDate03;
    @BindView(R.id.no_date_04)
    TextView       noDate04;
    @BindView(R.id.reply)
    TextView       reply;
    @BindView(R.id.phone)
    TextView             phone;
    @BindView(R.id.status)
    TextView             status;
    @BindView(R.id.contact)
    TextView             contact;
    @BindView(R.id.reply_layout)
    RelativeLayout       replyLayout;
    @BindView(R.id.rp_image)
    LinearLayout         nameInfoImage;
    @BindView(R.id.listen_image)
    LinearLayout         listenImage;
    @BindView(R.id. contract_image)
    LinearLayout         contractImage;
    @BindView(R.id.designer_image)
    LinearLayout         designerImage;
    @BindView(R.id.image_scroll_01)
    HorizontalScrollView imageScroll01;
    @BindView(R.id.image_scroll_02)
    HorizontalScrollView       imageScroll02;
    @BindView(R.id. image_scroll_03)
    HorizontalScrollView       imageScroll03;
    @BindView(R.id.image_scroll_04)
    HorizontalScrollView       imageScroll04;
    @BindView(R.id.image_layout)
    LinearLayout         imageLayout;
    private ActivityDecorationDetailBinding binding;
    private DecorateDetailModel model;
    private DecorateApplyTo decorateTo;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this, R.layout.activity_decoration_detail);
        model = new DecorateDetailPresenter(this);
        model.getDecorationDetail(getIntent().getStringExtra("DecorateId"));
        loadingShow();
        setTitle(Constant.DECORATION_APPLY);
        ButterKnife.bind(this);
    }
    @Override
    public void getDecorationDetail(DecorateApplyTo decorateTo) {
        loadingDismiss();
        this.decorateTo = decorateTo;
        binding.setMode(decorateTo);
       setView();
    }

    private void setView() {
        if("1".equals(decorateTo.getApplyStatus())){
            status.setTextColor(Color.parseColor("#ef4661"));
        }else if ("2".equals(decorateTo.getApplyStatus())){
            status.setTextColor(Color.parseColor("#6d75a4"));
        }else{
            status.setTextColor(Color.parseColor("#b4b4b4"));
        }
            explain.setText("\u3000\u3000\u3000"+decorateTo.getExplains());

        if(TextUtils.isEmpty(decorateTo.getAnswerTime())){
            replyLayout.setVisibility(View.GONE);
        }else {
            if(TextUtils.isEmpty(decorateTo.getAnswerContent()))
                reply.setText("\u3000\u3000\u3000"+"暂无");
            else
            reply.setText("\u3000\u3000\u3000"+decorateTo.getAnswerContent());
        }
        phone.setText("联系电话："+decorateTo.getUserTel());
        contact.setText("联系人："+decorateTo.getUserName());
        displayImage();
    }

    private void displayImage() {
        if("2".equals(decorateTo.getApplyStatus())){
                if(TextUtils.isEmpty(decorateTo.getCorporationImgs())){
                    noDate01.setVisibility(View.VISIBLE);
                    imageScroll01.setVisibility(View.GONE);
                }else{
                    imageScroll01.setVisibility(View.VISIBLE);
                    noDate01.setVisibility(View.GONE);
                    setNameInfoImage();
                }
                if(TextUtils.isEmpty(decorateTo.getBusinessImgs())){
                    noDate02.setVisibility(View.VISIBLE);
                    imageScroll02.setVisibility(View.GONE);
                }else{
                    imageScroll02.setVisibility(View.VISIBLE);
                    noDate02.setVisibility(View.GONE);
                    setListenImage();
                }  if(TextUtils.isEmpty(decorateTo.getContractImgs())){
                    noDate03.setVisibility(View.VISIBLE);
                    imageScroll03.setVisibility(View.GONE);
                }else{
                imageScroll03.setVisibility(View.VISIBLE);
                noDate03.setVisibility(View.GONE);
                    setContractImage();
                }  if(TextUtils.isEmpty(decorateTo.getDesignerImgs())){
                    noDate04.setVisibility(View.VISIBLE);
                    imageScroll04.setVisibility(View.GONE);
                }else{
                imageScroll04.setVisibility(View.VISIBLE);
                noDate04.setVisibility(View.GONE);
                    setDesignerImage();
                }
        }else {
            imageLayout.setVisibility(View.GONE);

        }

    }

    private void setDesignerImage() {
          int width = getScreenWidth()*145/1000;
          int margin = getScreenWidth()*2/100;
        String image=decorateTo.getDesignerImgs().replaceAll(",",";");
          String[] imageList = image.split(";");
          Observable.from(imageList).subscribe(imageUrl -> {
          ImageView rpImage = new ImageView(this);
          LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
          params.leftMargin = margin;
          params.rightMargin = margin;
          Glide.with(MainApp.mContext).load(MainApp.getImagePath(imageUrl)).into(rpImage);
          rpImage.setTag(imageUrl);
//        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508341371894&di=044a21738a6217400730183f862e2c79&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201312%2F05%2F20131205172519_fSFSA.jpeg").into(rpImage);
          rpImage.setScaleType(ImageView.ScaleType.FIT_XY);
          rpImage.setOnClickListener((View v)-> {
            Intent intent = new Intent(appContext, PostImageDetailActivity.class);
            intent.putExtra("CurrentPath", (String) v.getTag());
            intent.putExtra("PathList", image);
            startActivity(intent);
        });
            designerImage.addView(rpImage, params);
        });
    }

    private void setContractImage() {
        int width = getScreenWidth()*145/1000;
        int margin = getScreenWidth()*2/100;
        String image=decorateTo.getContractImgs().replaceAll(",",";");
        String[] imageList = image.split(";");
        Observable.from(imageList).subscribe(imageUrl -> {
            ImageView rpImage = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            params.leftMargin = margin;
            params.rightMargin = margin;
            Glide.with(MainApp.mContext).load(MainApp.getImagePath(imageUrl)).into(rpImage);
            rpImage.setTag(imageUrl);
//        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508341371894&di=044a21738a6217400730183f862e2c79&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201312%2F05%2F20131205172519_fSFSA.jpeg").into(rpImage);
            rpImage.setScaleType(ImageView.ScaleType.FIT_XY);
            rpImage.setOnClickListener((View v)-> {
                Intent intent = new Intent(appContext, PostImageDetailActivity.class);
                intent.putExtra("CurrentPath", (String) v.getTag());
                intent.putExtra("PathList", image);
                startActivity(intent);
            });
            contractImage.addView(rpImage, params);
        });
    }

    private void setListenImage() {

        int width = getScreenWidth()*145/1000;
        int margin = getScreenWidth()*2/100;
        String image=decorateTo.getBusinessImgs().replaceAll(",",";");
        String[] imageList = image.split(";");
        Observable.from(imageList).subscribe(imageUrl -> {
            ImageView rpImage = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            params.leftMargin = margin;
            params.rightMargin = margin;
            Glide.with(MainApp.mContext).load(MainApp.getImagePath(imageUrl)).into(rpImage);
//        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508341371894&di=044a21738a6217400730183f862e2c79&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201312%2F05%2F20131205172519_fSFSA.jpeg").into(rpImage);
            rpImage.setTag(imageUrl);
            rpImage.setScaleType(ImageView.ScaleType.FIT_XY);
            rpImage.setOnClickListener((View v)-> {
                Intent intent = new Intent(appContext, PostImageDetailActivity.class);
                intent.putExtra("CurrentPath", (String) v.getTag());
                intent.putExtra("PathList", image);
                startActivity(intent);
            });
            listenImage.addView(rpImage, params);
        });
    }

    private void setNameInfoImage() {
        int width = getScreenWidth()*145/1000;
        int margin = getScreenWidth()*2/100;
        String image=decorateTo.getCorporationImgs().replaceAll(",",";");
        String[] imageList = image.split(";");
        Observable.from(imageList).subscribe(imageUrl -> {
            ImageView rpImage = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            params.leftMargin = margin;
            params.rightMargin = margin;
            Glide.with(MainApp.mContext).load(MainApp.getImagePath(imageUrl)).into(rpImage);
            rpImage.setTag(imageUrl);
//        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508341371894&di=044a21738a6217400730183f862e2c79&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201312%2F05%2F20131205172519_fSFSA.jpeg").into(rpImage);
            rpImage.setScaleType(ImageView.ScaleType.FIT_XY);
            rpImage.setOnClickListener((View v)-> {
                Intent intent = new Intent(appContext, PostImageDetailActivity.class);
                intent.putExtra("CurrentPath", (String) v.getTag());
                intent.putExtra("PathList", image);
                startActivity(intent);
            });
            nameInfoImage.addView(rpImage, params);
        });
    }
}
