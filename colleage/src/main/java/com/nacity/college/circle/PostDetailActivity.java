package com.nacity.college.circle;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.college.common_libs.domain.circle.NeighborCommentTo;
import com.college.common_libs.domain.circle.NeighborLikeTo;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.R;
import com.nacity.college.databinding.ActivityPostDetailBinding;
import com.nacity.college.databinding.LifeCommentItemBinding;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nacity.college.MainApp;
import com.nacity.college.base.AdWebActivity;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.CommonAlertDialog;
import com.nacity.college.base.CommonDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.PostImageDetailActivity;
import com.nacity.college.base.utils.DateUtil;
import com.nacity.college.base.utils.image.ImageLoadleUtil;
import com.nacity.college.circle.presenter.PostDetailPresenter;
import com.nacity.college.circle.presenter.impl.PostDetailPresenterImpl;
import com.nacity.college.circle.view.PostDetailView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import es.dmoral.toasty.Toasty;
import rx.Observable;

/**
 * Created by xzz on 2017/7/2.
 **/

public class PostDetailActivity extends BaseActivity implements PostDetailView {

    @BindView(R.id.head_image)
    RoundedImageView headImage;
    @BindView(R.id.sub_textView)
    TextView subTextView;
    @BindView(R.id.v_icon)
    View vIcon;
    private PostDetailPresenter presenter;
    private ActivityPostDetailBinding binding;
    private String praiseName = "";
    private NeighborPostTo postTo;
    private CommonDialog mDialog;
    private SubContentUtil subContentUtil;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail);
        ButterKnife.bind(this);
        setTitle(Constant.POST_DETAIL);
        presenter = new PostDetailPresenterImpl(this);
        presenter.getPostDetail(getIntent().getStringExtra("PostSid"));
        ImageLoadleUtil.initLoader(this);
        loadingShow();
        subContentUtil = new SubContentUtil();

    }

    @Override
    public void showDataSuccess(String message) {

    }

    @Override
    public void showMessage(String message) {
        loadingDismiss();
        Toasty.warning(appContext, message).show();

    }

    @Override
    public void getDataSuccess(NeighborPostTo mode) {
        postTo = mode;
        if (postTo.getPostCommentVoList() != null && postTo.getPostCommentVoList().size() > 0)
            postTo.setPostCommentVoList(subContentUtil.getSubCommentList(postTo, subTextView));
        loadingDismiss();
        binding.setMode(mode);
        if (appContext != null && binding.headImage != null)
            Glide.with(appContext).load(MainApp.getImagePath(mode.getUserPic())).into(binding.headImage);
        setContent(mode, binding.postContent);
//        vIcon.setVisibility(mode.getUserType()==6||mode.getUserType()==7?View.VISIBLE:View.GONE);
        binding.postTime.setText(DateUtil.passTime(mode.getCreateTime()));
        binding.commentNumber.setText((mode.getPostCommentVoList() == null ? 0 : mode.getPostCommentVoList().size()) + "");
        binding.praiseNumber.setText((mode.getPostPraiseVoList() == null ? 0 : mode.getPostPraiseVoList().size()) + "");

        setPostImage(mode.getImages(), binding.gridView);
        setPraiseLayout(mode.getPostPraiseVoList(), binding.praiseIcon);

        commentContent(mode.getPostCommentVoList());
        //设置点赞的图标


        binding.praiseLayout.setOnClickListener(v -> {
            presenter.praise(mode.getId());
            loadingShow();
        });

        //设置删除帖子
        if (userInfo.getSid().equals(mode.getCreateUserId())) {
            binding.deletePost.setVisibility(View.VISIBLE);
            binding.deletePost.setOnClickListener(v -> deletePost(mode.getId()));
        }
        //添加评论

        binding.commentLayout.setOnClickListener(v -> commentDialog(null));


    }

    @Override
    public void deletePostSuccess() {
        showSuccess(Constant.DELETE_POST_SUCCESS);
        new Handler().postDelayed(() -> runOnUiThread(this::finish), 2000);
        EventBus.getDefault().post(new Event<>("CircleDeleteSuccess", getIntent().getStringExtra("PostSid")));
    }

    @Override
    public void commentSuccess(NeighborCommentTo commentTo) {
        presenter.getPostDetail(getIntent().getStringExtra("PostSid"));
        EventBus.getDefault().post(new Event<>("CircleCommentSuccess", getIntent().getStringExtra("PostSid")));
        mDialog.dismiss();
    }

    @Override
    public void deleteCommentSuccess() {
        EventBus.getDefault().post(new Event<>("CircleCommentDeleteSuccessq", getIntent().getStringExtra("PostSid")));
        presenter.getPostDetail(getIntent().getStringExtra("PostSid"));
    }

    @Override
    public void addPriseSuccess() {
        EventBus.getDefault().post(new Event<>("CircleAddPraiseSuccess", getIntent().getStringExtra("PostSid")));
        presenter.getPostDetail(getIntent().getStringExtra("PostSid"));
    }


    /**
     * 设置贴子的图片
     */
    private void setPostImage(String pathList, GridLayout gridLayout) {
        if (TextUtils.isEmpty(pathList))
            gridLayout.setVisibility(View.GONE);
        else {
            gridLayout.setVisibility(View.VISIBLE);
            gridLayout.removeAllViews();

            for (int i = 0; i < pathList.split(";").length; i++) {

                RoundedImageView imageView = new RoundedImageView(appContext);

//

                imageView.setBackgroundResource(R.drawable.post_list_item_bg);
                imageView.setCornerRadius(12, 12, 12, 12);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                if (pathList.split(";").length == 2 || pathList.split(";").length == 4) {
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    layoutParams.width = (int) (250.0 / 750 * getScreenWidth());
                    layoutParams.height = (int) (150.0 / 750 * getScreenWidth());
                    layoutParams.setMarginStart((int) (20.0 / 750 * getScreenWidth()));
                    layoutParams.bottomMargin = (int) (20.0 / 750 * getScreenWidth());
                    gridLayout.setColumnCount(2);

                    disPlayImage(imageView, pathList.split(";")[i]);
                } else if (pathList.split(";").length == 1) {

                    Glide.with(appContext).load(pathList.split(";")[i]).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                            if (resource.getHeight() / resource.getWidth() >= 1) {
                                layoutParams.width = (int) (400.0 * resource.getWidth() / (resource.getHeight() * 750) * getScreenWidth());
                                layoutParams.height = (int) (400.0 / 750 * getScreenWidth());
                            } else {
                                layoutParams.width = (int) (400.0 / 750 * getScreenWidth());
                                layoutParams.height = (int) (400.0 * resource.getHeight() / (resource.getWidth() * 750) * getScreenWidth());
                            }
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageView.setMaxWidth(400);
                            imageView.setMaxHeight((int) (400.0 / 750 * getScreenWidth()));
                            imageView.setImageBitmap(resource);
//
//
                        }
                    });
                    layoutParams.setMarginStart((int) (22.0 / 750 * getScreenWidth()));
                } else {

                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    layoutParams.width = (int) (170.0 / 750 * getScreenWidth());
                    layoutParams.height = (int) (100.0 / 750 * getScreenWidth());
                    layoutParams.setMarginStart((int) (20.0 / 750 * getScreenWidth()));
                    layoutParams.bottomMargin = (int) (20.0 / 750 * getScreenWidth());

                    gridLayout.setColumnCount(3);
                    disPlayImage(imageView, pathList.split(";")[i]);

                }
                imageView.setTag(i);
                imageView.setLayoutParams(layoutParams);
                imageView.setOnClickListener(view -> {
                    Intent intent = new Intent(appContext, PostImageDetailActivity.class);
                    intent.putExtra("PathList", pathList);
                    intent.putExtra("CurrentPath", pathList.split(";")[(int) view.getTag()]);
                  startActivity(intent);
                });
                gridLayout.addView(imageView);
            }
        }
    }

    /**
     * 设置点赞显示
     */
    public void setPraiseLayout(List<NeighborLikeTo> likeList, ImageView praiseIcon) {
        praiseName = "";
        if (likeList == null || likeList.size() == 0) {
            binding.praiseNameLayout.setVisibility(View.GONE);
            praiseIcon.setBackgroundResource(R.drawable.like_unpress);
        } else {
            binding.praiseNameLayout.setVisibility(View.VISIBLE);
            Observable.from(likeList).subscribe(neighborLikeTo -> praiseName = praiseName + (neighborLikeTo.getNickname()) + ", ");
            binding.praiseName.setText(praiseName.substring(0, praiseName.length() - 2));
            if (praiseName.contains(userInfo.getUserInfoTo().getNickname()))
                praiseIcon.setBackgroundResource(R.drawable.life_praise_icon);
            else
                praiseIcon.setBackgroundResource(R.drawable.like_unpress);
        }


    }

    /**
     * 评论显示
     */
    public void commentContent(List<NeighborCommentTo> commentList) {
        binding.commentGridView.removeAllViews();
        if (commentList == null || commentList.size() == 0)
            binding.commentGridView.setVisibility(View.GONE);
        else {
            binding.commentGridView.setVisibility(View.VISIBLE);
            Observable.from(commentList).subscribe(commentTo -> {
                View mView = View.inflate(appContext, R.layout.life_comment_item, null);
                LifeCommentItemBinding commentBind = DataBindingUtil.bind(mView);
                commentBind.setMode(commentTo);
                commentBind.commentTime.setText(commentTo.getCreateTimeDesc());
//                commentBind.vIcon.setVisibility(commentTo.getUserType()==7||commentTo.getUserType()==6?View.VISIBLE:View.GONE);
                if (appContext != null && commentBind.headImage != null)
                    Glide.with(appContext).load(MainApp.getImagePath(commentTo.getUserPic())).into(commentBind.headImage);

                if (commentTo.getReplyUserId() != null) {
                    Spannable ss = new SpannableString(Constant.REPLY + commentTo.getSubComment());

                    ss.setSpan(new ForegroundColorSpan(0xff6d75a4), 2, 3 + commentTo.getReplyTargetNickname().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    commentBind.commentContent.setText(ss);
                } else
                    commentBind.commentContent.setText(commentTo.getSubComment());

                mView.setOnClickListener(v -> {
                    if (userInfo.getSid().equals(commentTo.getCreateUserId()))
                        deleteComment(commentTo.getId());
                    else
                        commentDialog(commentTo);
                });
                if ((commentTo.getReplyUserId() == null ? 0 : commentTo.getReplyTargetNickname().length()) + commentTo.getContent().length() > commentTo.getSubComment().length() + 3) {
                    commentBind.allComment.setVisibility(View.VISIBLE);
                    commentBind.allComment.setOnClickListener(v -> {
                        commentBind.commentContent.setTextIsSelectable(true);
                        commentBind.allComment.setVisibility(View.GONE);
                        setExpandContent(commentBind.allComment, commentTo, commentBind.commentContent);


                    });
                } else {
                    commentBind.allComment.setVisibility(View.GONE);
                }


                binding.commentGridView.addView(mView);
            });
        }

    }

    private void setExpandContent(TextView allComment, NeighborCommentTo commentTo, TextView commentContent) {
        if (commentTo.getReplyUserId() != null) {
            Spannable ss = new SpannableString(Constant.REPLY + commentTo.getReplyTargetNickname() + "：" + commentTo.getContent() + "收起");
            ss.setSpan(new TextClick(commentTo, 2, allComment, commentContent), ss.length() - 2, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(0xff6d75a4), 2, 3 + commentTo.getReplyTargetNickname().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(0xff6d75a4), ss.length() - 2, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            commentContent.setMovementMethod(LinkMovementMethod.getInstance());
            commentContent.setText(ss);
        } else {
            Spannable ss = new SpannableString(commentTo.getContent() + "收起");
            ss.setSpan(new TextClick(commentTo, 2, allComment, commentContent), ss.length() - 2, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(0xff6d75a4), ss.length() - 2, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            commentContent.setMovementMethod(LinkMovementMethod.getInstance());
            commentContent.setText(ss);
        }
    }

    /**
     * 删除帖子
     */
    public void deletePost(String postSid) {
        CommonAlertDialog.show(appContext, Constant.DELETE_POST, Constant.CONFIRM, Constant.CANCEL).setOnClickListener(v -> {
            CommonAlertDialog.dismiss();
            presenter.deletePost(postSid);
            loadingShow();
        });


    }

    /**
     * 添加评论
     */
    public void addComment(NeighborPostTo mode, NeighborCommentTo commentTo, String commentContent) {
        if (TextUtils.isEmpty(commentContent)) {
            Toasty.warning(appContext, Constant.COMMENT_NO_EMPTY).show();
            return;
        }
        loadingShow();
        presenter.addComment(mode, commentTo, commentContent);
    }

    /**
     * 删除评论
     */
    public void deleteComment(String commentSid) {
        CommonAlertDialog.show(appContext, Constant.DELETE_COMMENT, Constant.CONFIRM, Constant.CANCEL).setOnClickListener(v -> {
            CommonAlertDialog.dismiss();
            loadingShow();
            presenter.deleteComment(commentSid);
        });

    }

    /**
     * 评论弹出框
     */
    public void commentDialog(NeighborCommentTo commentTo) {
        mDialog = new CommonDialog(appContext, getScreenWidth(), (int) (getScreenWidth() * 0.1666), R.layout.dialog_editor_input, R.style.myDialogTheme);
        EditText mContent = (EditText) mDialog.findViewById(R.id.comment_content);
        Button send = (Button) mDialog.findViewById(R.id.btn_send);
        mContent.requestFocus();
        mContent.setFocusable(true);
        mContent.setHint(commentTo == null ? "我想说..." : "@" + (commentTo.getCreateUserId() == null ? "" : commentTo.getNickname()) + "：");
        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    send.setTextColor(Color.parseColor("#6d75a4"));
                } else
                    send.setTextColor(Color.parseColor("#b4b4b4"));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDialog.show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) mContent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mContent, 0);
            }
        }, 300);

        mDialog.setCanceledOnTouchOutside(true);
        send.setOnClickListener(v -> addComment(postTo, commentTo, mContent.getText().toString()));
    }

    /**
     * 发帖内容显示
     */
    private void setContent(NeighborPostTo mode, TextView postContent) {
        postContent.setText(mode.getContent());

        if (!TextUtils.isEmpty(mode.getUrlTitle())) {
            SpannableStringBuilder spannable = new SpannableStringBuilder("#" + mode.getUrlTitle() + "#" + mode.getContent());

            spannable.setSpan(new TextClick(mode.getPostUrl(), 1, mode.getUrlTitle()), 0, mode.getUrlTitle().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(0xff007aff), 0, mode.getUrlTitle().length() + 2, 0);
            postContent.setMovementMethod(LinkMovementMethod.getInstance());
            postContent.setText(spannable);
        }

    }

    private class TextClick extends ClickableSpan {
        private int type;
        private String url;
        private String title;
        private TextView allComment;
        private TextView commentContent;
        private NeighborCommentTo commentTo;


        private TextClick(String url, int type, String title) {
            this.type = type;
            this.url = url;
            this.title = title;
        }

        private TextClick(NeighborCommentTo commentTo, int type, TextView allComment, TextView commentContent) {
            this.type = type;
            this.commentTo = commentTo;
            this.allComment = allComment;
            this.commentContent = commentContent;

        }

        @Override
        public void onClick(View widget) {

            if (type == 1 && !TextUtils.isEmpty(url)) {
                Intent intent = new Intent(appContext, AdWebActivity.class);
                intent.putExtra("Title", title);
                intent.putExtra("Url", url);
                appContext.startActivity(intent);
            }
            if (type == 2) {
                commentContent.setTextIsSelectable(false);
                if (commentTo.getReplyUserId() != null) {
                    Spannable ss = new SpannableString(Constant.REPLY + commentTo.getSubComment());
                    ss.setSpan(new ForegroundColorSpan(0xff6d75a4), 2, 3 + commentTo.getReplyTargetNickname().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    commentContent.setText(ss);
                } else
                    commentContent.setText(commentTo.getSubComment());

                if ((commentTo.getReplyUserId() == null ? 0 : commentTo.getReplyTargetNickname().length()) + commentTo.getContent().length() > commentTo.getSubComment().length()) {
                    allComment.setVisibility(View.VISIBLE);

                    allComment.setOnClickListener(v -> {
                        commentContent.setTextIsSelectable(true);
                        allComment.setVisibility(View.GONE);
                        setExpandContent(allComment, commentTo, commentContent);


                    });
                } else {
                    allComment.setVisibility(View.GONE);
                }
            }

        }
    }


}
