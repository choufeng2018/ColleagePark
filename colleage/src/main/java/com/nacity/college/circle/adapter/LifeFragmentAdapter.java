package com.nacity.college.circle.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.college.common_libs.domain.user.UserInfoTo;
import com.nacity.college.R;
import com.nacity.college.databinding.LifePostItemBinding;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nacity.college.MainApp;
import com.nacity.college.base.MyObserver;
import com.nacity.college.base.adapter.BaseAdapter;
import com.nacity.college.base.adapter.BindingHolder;
import com.nacity.college.base.info.UserInfoHelper;
import com.nacity.college.base.utils.DateUtil;
import com.nacity.college.base.utils.image.ImageLoadleUtil;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xzz on 2017/6/24.
 **/

public class LifeFragmentAdapter extends BaseAdapter<NeighborPostTo, LifePostItemBinding> {
    private UserInfoTo userInfo;


    public LifeFragmentAdapter(Context context) {
        super(context);
        this.mContext = context;
        userInfo = UserInfoHelper.getInstance(context).getUserInfoTo();
        ImageLoadleUtil.initLoader(context);
    }

    @Override
    public BindingHolder<LifePostItemBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        LifePostItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.life_post_item, parent, false);
        BindingHolder<LifePostItemBinding> holder = new BindingHolder<>(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder<LifePostItemBinding> holder, int position) {
        NeighborPostTo mode = mList.get(position);
        Observable.just(mode).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<NeighborPostTo>() {
                    @Override
                    public void onNext(NeighborPostTo neighborPostTo) {
                        LifePostItemBinding binding = holder.getBinding();
                        binding.setMode(mode);

                        Glide.with(MainApp.mContext).load(MainApp.getImagePath(mode.getUserPic())).into(binding.headImage);

//                        if (mode.getUserType()==7||mode.getUserType()==6)
//                            binding.vIcon.setVisibility(View.VISIBLE);
//                        else
//                            binding.vIcon.setVisibility(View.GONE);

                        binding.postTime.setText(DateUtil.passTime(mode.getCreateTime()));

                        binding.commentNumber.setText(mode.getCommentTotal() + "");
                        binding.praiseNumber.setText(mode.getPraiseTotal() + "");
                        binding.headImage.setOnClickListener(v -> {
                            if (headImageListener != null)
                                headImageListener.onHeadImageClick(mode, binding.headImage);
                        });
                        setPostImage(mode.getImages(), binding.gridView);

                        if (mode.isPraised())
                            binding.praiseIcon.setBackgroundResource(R.drawable.life_praise_icon);
                        else
                            binding.praiseIcon.setBackgroundResource(R.drawable.like_unpress);

                        binding.praiseLayout.setOnClickListener(v ->
                                {
                                    if (praiseListener != null)
                                        praiseListener.onPraiseClick(mode, position);
                                }
                        );
                        setContent(mode, binding);

                        //设置删除帖子
                        if (userInfo.getUserId().equals(mode.getCreateUserId())) {
                            binding.deletePost.setVisibility(View.VISIBLE);
                            binding.deletePost.setOnClickListener(v -> {

                                if (deletePostListener != null) {

                                    deletePostListener.deletePostClick(mode.getId(),position);
                                }
                            });
                        } else
                            binding.deletePost.setVisibility(View.GONE);
                    }
                }
        );


    }

    /**
     * 发帖内容显示
     */
    private void setContent(NeighborPostTo mode, LifePostItemBinding bindingView) {
        bindingView.postContent.setText(mode.getSubPostContent());
        if ((mode.getContent().length() + (mode.getUrlTitle() == null ? 0 : mode.getUrlTitle().length())) > mode.getSubPostContent().length()) {
            bindingView.allContent.setVisibility(View.VISIBLE);
        } else
            bindingView.allContent.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(mode.getUrlTitle())) {
            SpannableStringBuilder spannable = new SpannableStringBuilder(mode.getSubPostContent());
            spannable.setSpan(new TextClick(mode.getPostUrl(), 1, mode.getUrlTitle()), 0, mode.getUrlTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(0xff007aff), 0, mode.getUrlTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            bindingView.postContent.setMovementMethod(LinkMovementMethod.getInstance());
            bindingView.postContent.setText(spannable);
        }


        bindingView.allContent.setOnClickListener(v -> {
            bindingView.postContent.setTextIsSelectable(true);

            setAllContent(mode, bindingView);

            bindingView.allContent.setVisibility(View.GONE);
        });


    }

    /**
     * 全文展示
     */
    private void setAllContent(NeighborPostTo mode, LifePostItemBinding bindingView) {


        if (!TextUtils.isEmpty(mode.getUrlTitle())) {
            SpannableStringBuilder spannable = new SpannableStringBuilder(mode.getUrlTitle() + mode.getContent() + "收起");
            spannable.setSpan(new TextClick(mode.getPostUrl(), 1, mode.getUrlTitle()), 0, mode.getUrlTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new TextClick(2, mode, bindingView), spannable.length() - 2, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(0xff6d75a4), 0, mode.getUrlTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(0xff6d75a4), spannable.length() - 2, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            bindingView.postContent.setMovementMethod(LinkMovementMethod.getInstance());
            bindingView.postContent.setText(spannable);
        } else {
            SpannableStringBuilder spannable = new SpannableStringBuilder(mode.getContent() + "收起");
            spannable.setSpan(new TextClick(2, mode, bindingView), spannable.length() - 2, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(0xff6d75a4), spannable.length() - 2, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            bindingView.postContent.setMovementMethod(LinkMovementMethod.getInstance());
            bindingView.postContent.setText(spannable);
        }
    }

    /**
     * 设置贴子的图片
     */
    private void setPostImage(String pathList, GridLayout gridLayout) {
        gridLayout.removeAllViews();
        if (!TextUtils.isEmpty(pathList)) {
            String[] paths = pathList.split(";");
            Observable.from(paths).subscribe(s -> {
                View postImageView = View.inflate(mContext, R.layout.life_post_image_item, null);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                if (paths.length == 1) {
                    gridLayout.setColumnCount(1);
                    layoutParams.width = (int) (getScreenWidthPixels(mContext) * 0.4023);
                    layoutParams.height = (int) (getScreenWidthPixels(mContext) * 0.4023);
                } else if (paths.length == 2 || paths.length == 4) {
                    gridLayout.setColumnCount(2);
                    layoutParams.width = (int) (getScreenWidthPixels(mContext) * 0.31);
                    layoutParams.height = (int) (getScreenWidthPixels(mContext) * 0.31);
                } else {
                    gridLayout.setColumnCount(3);
                    layoutParams.width = (int) (getScreenWidthPixels(mContext) * 0.3);
                    layoutParams.height = (int) (getScreenWidthPixels(mContext) * 0.3);
                }

                layoutParams.setMargins(0, 0, (int) (getScreenWidthPixels(mContext) * 0.0139), (int) (getScreenWidthPixels(mContext) * 0.0139));
                postImageView.setLayoutParams(layoutParams);
                PhotoView postImage = (PhotoView) postImageView.findViewById(R.id.post_image);
                TextView imageType = (TextView) postImageView.findViewById(R.id.image_type);

                // Glide.with(mContext).load(MainApp.getImagePath()).into(postImage);
                ImageLoadleUtil.getNeighborImageType(mContext, MainApp.getImagePath(s +"?imageMogr2/thumbnail/250x"), postImage, imageType);
                postImage.setTransitionName("LifeImage");

                postImage.setOnClickListener(v -> {
                    if (listener != null)
                        listener.imageClick(s, pathList, postImage);
                });
                gridLayout.addView(postImageView);
            });
        }
    }

    public interface OnItemClickListener {
        void imageClick(String currentPath, String pathList, PhotoView postImage);
    }

    public interface OnHeadImageClickListener {
        void onHeadImageClick(NeighborPostTo mode, RoundedImageView headImage);
    }

    public interface OnPraiseClickListener {
        void onPraiseClick(NeighborPostTo mode, int position);
    }

    public interface EnterWebClickListener {
        void onEnterWebClick(String title, String url);
    }

    public interface DeletePostClickListener {
        void deletePostClick(String postSid, int position);
    }

    protected OnItemClickListener listener;
    private OnHeadImageClickListener headImageListener;
    private OnPraiseClickListener praiseListener;
    private EnterWebClickListener webClickListener;
    private DeletePostClickListener deletePostListener;

    public void setOnImageClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

    public void setOnHeadImageClickListener(OnHeadImageClickListener listener) {
        this.headImageListener = listener;

    }

    public void setPraiseClickListener(OnPraiseClickListener listener) {
        this.praiseListener = listener;

    }

    public void setEnterWebActivity(EnterWebClickListener webClickListener) {
        this.webClickListener = webClickListener;

    }

    public void setDeletePostListener(DeletePostClickListener deletePostListener) {
        this.deletePostListener = deletePostListener;

    }

    private class TextClick extends ClickableSpan {
        private int type;
        private String url;
        private String title;
        private NeighborPostTo postTo;
        private LifePostItemBinding postItemBinding;

        private TextClick(String url, int type, String title) {
            this.type = type;
            this.url = url;
            this.title = title;
        }

        private TextClick(int type, NeighborPostTo postTo, LifePostItemBinding postItemBinding) {
            this.type = type;
            this.postTo = postTo;
            this.postItemBinding = postItemBinding;
        }

        @Override
        public void onClick(View widget) {
            if (type == 1) {
                if (webClickListener != null)
                    webClickListener.onEnterWebClick(title, url);

            }
            if (type == 2) {
                postItemBinding.postContent.setTextIsSelectable(false);
                setContent(postTo, postItemBinding);

            }
        }
    }
}



