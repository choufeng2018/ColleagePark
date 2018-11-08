package com.nacity.college.property;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.college.common_libs.domain.property.FeedbackTo;
import com.nacity.college.R;
import com.nacity.college.MainApp;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.BaseView;
import com.nacity.college.base.PostImageDetailActivity;
import com.nacity.college.base.utils.DateUtil;
import com.nacity.college.property.model.PropertyPraiseDetailModel;
import com.nacity.college.property.presenter.PropertyPraiseDetailPresenter;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by xzz on 2017/9/20.
 **/

public class PraiseDetailActivity extends BaseActivity<FeedbackTo> implements BaseView<FeedbackTo> {
    @BindView(R.id.praise_content)
    TextView praiseContent;
    @BindView(R.id.praise_time)
    TextView praiseTime;
    @BindView(R.id.service_image)
    GridLayout serviceImage;
    @BindView(R.id.praise_reply)
    TextView praiseReply;
    @BindView(R.id.replay_time)
    TextView replayTime;
    @BindView(R.id.reply_layout)
    AutoLinearLayout replyLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praise_detail);
        ButterKnife.bind(this);
        setTitle(getIntent().getStringExtra("Title"));
        PropertyPraiseDetailModel model = new PropertyPraiseDetailPresenter(this);
        model.getPraiseDetail(getIntent().getStringExtra("FeedbackId"));
    }

    @Override
    public void getDataSuccess(FeedbackTo data) {
        praiseContent.setText(data.getFeedbackContent());
        praiseTime.setText(DateUtil.getDateTimeFormat(DateUtil.mDateFormatString, data.getCreateTime()));
        if (!TextUtils.isEmpty(data.getReplyContent())){
            replyLayout.setVisibility(View.VISIBLE);
            praiseReply.setText(data.getReplyContent());
            replayTime.setText(DateUtil.getDateTimeFormat(DateUtil.mDateFormatString, data.getLastModTime()));
        }
        if (!TextUtils.isEmpty(data.getFeedbackImages()))
        setImageLayout(data.getFeedbackImages());
    }

    private void setImageLayout(String images) {

        String[] imageList=images.split(";");
        Observable.from(imageList).subscribe(imageUrl ->{
            ImageView imageView=new ImageView(appContext);
            GridLayout.LayoutParams layoutParams=new GridLayout.LayoutParams();
            layoutParams.width= (int) (getScreenWidth()*0.21333);
            layoutParams.height= (int) (getScreenWidth()*0.21333);
            layoutParams.leftMargin= (int) (getScreenWidth()*0.0168889);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(MainApp.mContext).load(MainApp.getImagePath(imageUrl)).into(imageView);
            imageView.setTag(imageUrl);
            imageView.setOnClickListener(v -> {
                Intent intent = new Intent(appContext, PostImageDetailActivity.class);
                intent.putExtra("CurrentPath",(String) v.getTag());
                intent.putExtra("PathList",images);
                startActivity(intent);
            });
            serviceImage.addView(imageView);
        } );
    }
}
