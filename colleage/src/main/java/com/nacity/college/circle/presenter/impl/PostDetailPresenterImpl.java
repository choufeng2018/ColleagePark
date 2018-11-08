package com.nacity.college.circle.presenter.impl;


import com.college.common_libs.domain.circle.NeighborCommentParam;
import com.college.common_libs.domain.circle.NeighborCommentTo;
import com.college.common_libs.domain.circle.NeighborHandleParam;
import com.college.common_libs.domain.circle.NeighborLikeTo;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.base.BasePresenter;
import com.nacity.college.circle.LoadingCallback;
import com.nacity.college.circle.interactor.CommonLifeInteractor;
import com.nacity.college.circle.interactor.PostDetailInteractor;
import com.nacity.college.circle.interactor.impl.CommonLifeInteractorImpl;
import com.nacity.college.circle.interactor.impl.PostDetailInteractorImpl;
import com.nacity.college.circle.presenter.PostDetailPresenter;
import com.nacity.college.circle.view.PostDetailView;

/**
 * Created by xzz on 2017/7/2.
 **/

public class PostDetailPresenterImpl extends BasePresenter implements PostDetailPresenter, LoadingCallback<NeighborPostTo>,CommonLifeInteractor.DeletePostCallback, CommonLifeInteractor.AddCommentCallback, CommonLifeInteractor.DeleteCommentCallback, CommonLifeInteractor.AddPraiseCallback {

    private PostDetailInteractor interactor;
    private PostDetailView detailView;
    private CommonLifeInteractor lifeInteractor;
    public PostDetailPresenterImpl(PostDetailView detailView){
        this.detailView=detailView;
        interactor=new PostDetailInteractorImpl();
        lifeInteractor=new CommonLifeInteractorImpl();
    }
    @Override
    public void getPostDetail(String postSid) {
        interactor.getPostDetail(postSid,userInfo.getSid(),this);
    }

    @Override
    public void deletePost(String postSid) {
         lifeInteractor.deletePost(postSid,this);
    }

    @Override
    public void addComment(NeighborPostTo mode, NeighborCommentTo commentTo, String commentContent) {
        NeighborCommentParam param=new NeighborCommentParam();
        param.setPostId(mode.getId());
        if (commentTo!=null) {
            param.setReplyUserId(commentTo.getCreateUserId());
            param.setReplyCommentId(commentTo.getId());
        }
        param.setContent(commentContent);
        param.setCreateUserId(userInfo.getSid());
        lifeInteractor.addComment(param,this);
    }

    @Override
    public void deleteComment(String commentSid) {
        lifeInteractor.deleteComment(commentSid,this);
    }

    @Override
    public void praise(String postSid) {
        NeighborHandleParam param=new NeighborHandleParam();
        param.setPostId(postSid);
        param.setCreateUserId(userInfo.getSid());
        lifeInteractor.addPraise(param,this);
    }

    @Override
    public void success(NeighborPostTo postTo) {
        detailView.getDataSuccess(postTo);
    }

    @Override
    public void message(String message) {
        detailView.showMessage(message);
    }

    @Override
    public void error(Throwable error) {
     detailView.loadError(error);

    }

    /**
     * 删除帖子成功
     */
    @Override
    public void deletePostSuccess() {
        detailView.deletePostSuccess();
    }

    /**
     *
     * 评论成功
     */
    @Override
    public void addCommentSuccess(NeighborCommentTo commentTo) {
        detailView.commentSuccess(commentTo);
    }

    /**
     * 删除评论成功
     */
    @Override
    public void deleteCommentSuccess() {
        detailView.deleteCommentSuccess();
    }

    /**
     *
     *点赞成功
     */
    @Override
    public void AddPraiseSuccess(NeighborLikeTo likeTo) {
        detailView.addPriseSuccess();

    }
}
