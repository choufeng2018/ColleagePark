package com.nacity.college.circle.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.college.common_libs.domain.circle.NeighborLikeTo;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.R;
import com.nacity.college.base.AdWebActivity;
import com.nacity.college.base.BaseFragment;
import com.nacity.college.base.CommonAlertDialog;
import com.nacity.college.base.Constant;
import com.nacity.college.base.Event;
import com.nacity.college.base.ParkLoadingDialog;
import com.nacity.college.base.PostImageDetailActivity;
import com.nacity.college.circle.CirclePersonalCenterActivity;
import com.nacity.college.circle.PostDetailActivity;
import com.nacity.college.circle.adapter.LifeFragmentAdapter;
import com.nacity.college.circle.presenter.LifeFragmentPresenter;
import com.nacity.college.circle.presenter.impl.LifeFragmentPresenterImpl;
import com.nacity.college.circle.view.LifeFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;


/**
 * Created by xzz on 2017/6/29.
 **/

@SuppressLint("ValidFragment")
public class LifeFragment extends BaseFragment implements LifeFragmentView {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    Unbinder unbinder;

    private String typeSid;
    private int pageIndex;
    private List<NeighborPostTo> postList = new ArrayList<>();
    private LifeFragmentAdapter adapter;
    private LifeFragmentPresenter presenter = new LifeFragmentPresenterImpl(this);
    private int praisePosition;
    private TextView subTextView;
    private int deletePosition;
    private boolean canDismiss;
    private Activity mActivity;
    private boolean init;

    public LifeFragment(String typeSid, TextView subTextView, Activity activity) {
        this.typeSid = typeSid;
        this.subTextView = subTextView;
        this.mActivity=activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View mView = inflater.inflate(R.layout.fragment_common_recycle_view, null);

        unbinder = ButterKnife.bind(this, mView);
        setRecycleView();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void setRecycleView() {
        adapter = new LifeFragmentAdapter(appContext);
        adapter.setList(postList);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycleView.setFooterViewColor(R.color.app_green, R.color.app_green, R.color.transparent);

        recycleView.setOnRefreshListener(() -> {
            pageIndex = 0;
            presenter.getLifePostData(typeSid, pageIndex);
        });

        recycleView.setOnLoadMoreListener(() -> {
            pageIndex++;
            presenter.getLifePostData(typeSid, pageIndex);
        });

        adapterClickListener();
        //进入帖子详情
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(appContext, PostDetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("PostSid", postList.get(position).getId());
            startActivity(intent);
        });
    }


    @Override
    public void refreshRecycleView(List<NeighborPostTo> storeToList) {
        loadingDismiss();
        recycleView.refreshComplete(20);
        if (pageIndex == 0)
            postList.clear();
            postList.addAll(storeToList);

        if (storeToList.size() < 20) {
            recycleView.setNoMore(true);
            pageIndex--;
        }
        postList = getSubList(postList, subTextView);
        adapter.setList(postList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public List<NeighborLikeTo> getLikeList() {
        return postList.get(praisePosition).getPostPraiseVoList();
    }

    @Override
    public void addPraiseSuccess(List<NeighborLikeTo> likeList) {
       loadingDismiss();
        postList.get(praisePosition).setPraiseTotal(Integer.valueOf(postList.get(praisePosition).getPraiseTotal()) + (postList.get(praisePosition).isPraised() ? -1 : +1)+"");
        postList.get(praisePosition).setPraised(!postList.get(praisePosition).isPraised());
        adapter.setList(postList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void deletePostSuccess() {
        loadingDismiss();
        postList.remove(deletePosition);
        adapter.notifyDataSetChanged();
    }

    private void adapterClickListener() {
        adapter.setOnImageClickListener((currentPath, pathList, postImage) -> {
            Intent intent = new Intent(getActivity(), PostImageDetailActivity.class);
            intent.putExtra("CurrentPath", currentPath);
            intent.putExtra("PathList", pathList);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), postImage, "Image");
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        });
        adapter.setOnHeadImageClickListener((mode, headImage) -> {
            Intent intent = new Intent(getActivity(), CirclePersonalCenterActivity.class);
            intent.putExtra("PoseTo", mode);
            intent.putExtra("OtherSid", mode.getCreateUserId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), headImage, "HeadImage");
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        });

        adapter.setPraiseClickListener((mode, position) -> {
            praisePosition = position;
            presenter.addPraise(mode.getId());
            loadingShow();
        });

        adapter.setEnterWebActivity((title, url) -> {
            if (!TextUtils.isEmpty(url)) {
                Intent intent = new Intent(appContext, AdWebActivity.class);
                intent.putExtra("Title", title);
                intent.putExtra("Url", url);
                startActivity(intent);
            }

        });
        adapter.setDeletePostListener((postSid ,position)-> CommonAlertDialog.show(getActivity(), Constant.DELETE_POST, Constant.CONFIRM, Constant.CANCEL).setOnClickListener(v -> {
            CommonAlertDialog.dismiss();
            presenter.deletePost(postSid);
            deletePosition=position;
            loadingShow();
        }));
    }

    public List<NeighborPostTo> getSubList(List<NeighborPostTo> postList, TextView subTextView) {

        for (int i = 0; i < postList.size(); i++) {

            if (!TextUtils.isEmpty(postList.get(i).getUrlTitle()))
                postList.get(i).setUrlTitle("#" + postList.get(i).getUrlTitle() + "# ");
            String subcontent = (TextUtils.isEmpty(postList.get(i).getUrlTitle()) ? "" : postList.get(i).getUrlTitle()) + postList.get(i).getContent();
            postList.get(i).setSubPostContent(getSubContent(subTextView, subcontent));
//            if (!TextUtils.isEmpty(postList.get(i).getPostSubject())){
//                if (postList.get(i).getPostContent() != null && !(postList.get(i).getPostSubject()+postList.get(i).getPostContent()).equals(getSubContent(subTextView,postList.get(i).getPostSubject()+ postList.get(i).getPostContent())))
//                    postList.get(i).setSubPostContent((getSubContent(subTextView, postList.get(i).getPostSubject() + postList.get(i).getPostContent())).substring(0,getSubContent(subTextView, postList.get(i).getPostSubject() + postList.get(i).getPostContent()).length()-4));
//                else
//                    postList.get(i).setSubPostContent(null);
//            } else {
//                if (postList.get(i).getPostContent() != null && !(postList.get(i).getPostContent()).equals(getSubContent(subTextView, postList.get(i).getPostContent())))
//                    postList.get(i).setSubPostContent(getSubContent(subTextView, postList.get(i).getPostContent()).substring(0,getSubContent(subTextView, postList.get(i).getPostContent()).length()-4));
//                else
//                    postList.get(i).setSubPostContent(null);
//
//            }
        }
//        String subContent;
//        for (int j=0;j<postList.size();j++) {
//            if (postList.get(j).getCommentList() != null) {
//                for (int i = 0; i < postList.get(j).getCommentList().size(); i++) {
//                    NeighborCommentTo commentTo = postList.get(j).getCommentList().get(i);
//                    subContent = getSubContent(subTextView, commentTo.getCommentContent());
//                    if (!subContent.equals(commentTo.getCommentContent()))
//                        postList.get(j).getCommentList().get(i).setSubComment(subContent);
//
//                }
//            }
//        }
        return postList;
    }

    private String getSubContent(TextView tv, String content) {


        tv.setText(content);
        Layout layout = tv.getLayout();

        int line = layout.getLineCount();

        if (line < 3) {
            return content;
        } else {

            String result = "";
            String text = layout.getText().toString();
            for (int i = 0; i < 3; i++) {
                int start = layout.getLineStart(i);
                int end = layout.getLineEnd(i);
                result += text.substring(start, end);
            }
            result = result.substring(0, result.length() - 4);

            return result;
        }

    }


    public void init() {
        if (!init) {

            EventBus.getDefault().register(this);
            presenter.getLifePostData(typeSid, 0);
            init=true;
            loadingShow();
        }
    }
    public void loadingShow(){
        canDismiss=false;
        if (loadingDialog==null)
            loadingDialog=new ParkLoadingDialog(mActivity,"", R.drawable.loading_animation);
        if (!loadingDialog.isShowing()) {
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();
            loadingDialog.setOnDismissListener(dialog -> {
                if (!canDismiss){
                    getActivity().finish();
                }
            });
        }

    }
    public void loadingDismiss(){
        canDismiss=true;
        if (loadingDialog!=null)
            loadingDialog.dismiss();

    }

    @Subscribe
    public void refreshData(Event<String> event){
        if ("CircleAddPraiseSuccess".equals(event.getType())||"CircleCommentDeleteSuccess".equals(event.getType())||"CircleCommentSuccess".equals(event.getType())||"CircleDeleteSuccess".equals(event.getType())){
            for (int i=0;i<postList.size();i++){
                if (postList.get(i).getId().equals(event.getMode())) {
                    switch (event.getType()){
                        case "CircleAddPraiseSuccess":
                            praisePosition=i;
                            addPraiseSuccess(null);
                            break;
                        case "CircleDeleteSuccess":
                            deletePosition=i;
                            deletePostSuccess();
                            break;
                        case "CircleCommentDeleteSuccess":
                            postList.get(i).setCommentTotal(Integer.valueOf(postList.get(i).getCommentTotal())-1+"");
                            adapter.notifyDataSetChanged();
                            break;
                        case "CircleCommentSuccess":
                            postList.get(i).setCommentTotal(Integer.valueOf(postList.get(i).getCommentTotal())+1+"");
                            adapter.notifyDataSetChanged();
                            break;


                    }



                }
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
