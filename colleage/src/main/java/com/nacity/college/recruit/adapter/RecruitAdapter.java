package com.nacity.college.recruit.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.college.common_libs.domain.recruit.RecruitItemTo;
import com.nacity.college.R;
import com.nacity.college.databinding.RecruitDetailItemBinding;
import com.nacity.college.base.CommonDialog;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;
import com.nacity.college.recruit.RecruitDetailActivity;

/**
 * Created by usb on 2017/9/8.
 */

public class RecruitAdapter extends BaseAdapter<RecruitItemTo,RecruitDetailItemBinding> {
//    public static final int TYPE_HEADER = 0;
//    public static final int TYPE_NORMAL = 1;
//    private View mHeaderView;
    public RecruitAdapter(Context context) {

        super(context);
        this.mContext=context;

    }
    @Override
    public BindingHolder<RecruitDetailItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (mHeaderView != null && viewType == TYPE_HEADER) {
//          return new BindingHolder(mHeaderView);
//        } else {
            RecruitDetailItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.recruit_detail_item, parent, false);
            BindingHolder<RecruitDetailItemBinding> holder = new BindingHolder<>(binding.getRoot());
            holder.setBinding(binding);
            return holder;

//        }

    }
    @Override
    public void onBindViewHolder(BindingHolder<RecruitDetailItemBinding> holder, int position) {
//        if(getItemViewType(position) == TYPE_HEADER) return;
        RecruitItemTo mode=mList.get(position);

        RecruitDetailItemBinding binding = holder.getBinding();

        binding.setMode(mode);
        binding.welfare.removeAllViews();
        if(!TextUtils.isEmpty(mode.getWelfare())){
        String[] welfareList = mode.getWelfare().split(",");
        for (int j = 0; j < welfareList.length && j < 4; j++) {
            View welfareText = View.inflate(mContext, R.layout.welfare_text_item, null);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            ((TextView) welfareText.findViewById(R.id.welfareText)).setText(welfareList[j]);
            layoutParams.setMargins(0, 0, 10, 0);
            welfareText.setLayoutParams(layoutParams);
            binding.welfare.addView(welfareText);
        }
        }
        binding.phone.setOnClickListener(v -> {
//            callShowDialog((String) v.getTag());
            callShowDialog(mode.getPhone());
        });
        binding.toDetail.setOnClickListener(v -> {
            Intent intent =new Intent(mContext, RecruitDetailActivity.class);
                intent.putExtra("mode",mode);
            mContext.startActivity(intent);
        });

        if (1==mode.getIsTop())
            binding.isTop.setVisibility(View.VISIBLE);
        else
            binding.isTop.setVisibility(View.GONE);
        if ("1".equals(mode.getIsNew()))
            binding.isNew.setVisibility(View.VISIBLE);
        else
            binding.isNew.setVisibility(View.GONE);
if(!TextUtils.isEmpty(mode.getUpdateTime())){
    if("刚刚".equals(mode.getUpdateTime()))
    binding.publishTime.setText(mode.getUpdateTime());
    else
        binding.publishTime.setText(mode.getUpdateTime()+"前");
}
//        if (mode.getNoticeTitle()!=null)
//            Glide.with(mContext).load(MainApp.getPicassoImagePath(mode.getPostOwner().getIcon())).into(binding.headImage);
//        binding.noticeTitle.setText(mode.getNoticeTitle());
//        binding.noticeContent.setText(mode.getNoticeContent());

//        binding.noticeContent.setText((mode.getCommentList()==null?0:mode.getCommentList().size())+"");
//        binding.noticeContent.setText((mode.getLikeList()==null?0:mode.getLikeList().size())+"");

    }
    private void callShowDialog(String phoneNumber) {
        final CommonDialog dialog = new CommonDialog(mContext,
                R.layout.dialog_call, R.style.myDialogTheme);
//        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView btnAdd = (TextView) dialog.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" +phoneNumber));
            mContext.startActivity(intent);
            dialog.dismiss();
        });
        TextView btnCancel = (TextView) dialog.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

}
