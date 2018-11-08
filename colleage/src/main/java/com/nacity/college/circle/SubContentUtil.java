package com.nacity.college.circle;


import android.text.Layout;
import android.text.TextUtils;
import android.widget.TextView;

import com.college.common_libs.domain.circle.NeighborCommentTo;
import com.college.common_libs.domain.circle.NeighborPostTo;

import java.util.List;


/**
 * Created by xzz on 2017/7/9.
 **/

public class SubContentUtil {
    public List<NeighborPostTo> getSubList(List<NeighborPostTo> postList,TextView subTextView){

        for (int i=0;i<postList.size();i++) {

            if (!TextUtils.isEmpty(postList.get(i).getUrlTitle()))
                postList.get(i).setUrlTitle("#"+postList.get(i).getUrlTitle()+"# ");
            String subcontent=(TextUtils.isEmpty(postList.get(i).getUrlTitle())?"": postList.get(i).getUrlTitle())+postList.get(i).getContent();
            postList.get(i).setSubPostContent(getSubContent(subTextView,subcontent));
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

    private String getSubContent(TextView tv, String content){


        tv.setText(content);
        Layout layout=tv.getLayout();

        int line=layout.getLineCount();

        if (line<3){
            return content;
        }else {

            String result = "";
            String text = layout.getText().toString();
            for (int i = 0; i < 3; i++) {
                int start = layout.getLineStart(i);
                int end = layout.getLineEnd(i);
                result += text.substring(start, end);
            }
            result=result.substring(0,result.length()-4);

            return result;
        }

    }
    public static String subTitleContent(TextView tv, String content){
        tv.setText(content);
        Layout layout=tv.getLayout();

        int line=layout.getLineCount();
        if (line>1){
            return content;
        }else {
            tv.setText(content);
            String result ;
            String text = layout.getText().toString();
            result=text;
            for (int i = 0; i <20-text.length(); i++) {
                result=result+"\t\t\t";
            }


            return result;
        }
    }
    public List<NeighborCommentTo> getSubCommentList(NeighborPostTo postTo,TextView subTextView){
        String subContent;
        if (postTo.getPostCommentVoList() != null) {
            for (int i = 0; i < postTo.getPostCommentVoList().size(); i++) {
                NeighborCommentTo commentTo = postTo.getPostCommentVoList().get(i);
                if (commentTo.getReplyUserId()==null){
                    subContent = getSubContent(subTextView, commentTo.getContent());
                    postTo.getPostCommentVoList().get(i).setSubComment(subContent.length()<commentTo.getContent().length()?subContent.substring(0,subContent.length()-2):subContent);
                }else {
                    subContent = getSubContent(subTextView, commentTo.getReplyTargetNickname() + "："+ commentTo.getContent());
                    postTo.getPostCommentVoList().get(i).setSubComment(subContent.length()<(commentTo.getContent().length()+commentTo.getReplyTargetNickname().length()+1)?subContent.substring(0,subContent.length()-4):subContent);
                    System.out.println(subContent);
                    System.out.println(commentTo.getContent());
                }
            }

        }
        return postTo.getPostCommentVoList();
    }

}
