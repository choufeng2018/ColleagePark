package com.nacity.college.circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.college.common_libs.domain.circle.NeighborCommentTo;
import com.college.common_libs.domain.circle.NeighborPostTo;
import com.nacity.college.R;
import com.nacity.college.base.BaseActivity;
import com.nacity.college.base.BaseRecycleView;
import com.nacity.college.base.Constant;
import com.nacity.college.circle.adapter.NewMessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by xzz on 2017/7/2.
 **/

public class NewMessageActivity extends BaseActivity implements BaseRecycleView<NeighborPostTo> {
    @BindView(R.id.recycleView)
    LRecyclerView recycleView;
    private List<NeighborCommentTo>postList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_common_layout);
        ButterKnife.bind(this);
        setTitle(Constant.NEW_MESSAGE);
        setRecycleView();

    }


    public void setRecycleView(){
        postList= (List<NeighborCommentTo>) getIntent().getSerializableExtra("NewMessageList");
       if (postList==null)
           postList=  new ArrayList<>();
        NewMessageAdapter adapter = new NewMessageAdapter(appContext);
        adapter.setList(postList);
        recycleView.setLayoutManager(new LinearLayoutManager(NewMessageActivity.this));
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        recycleView.setAdapter(lRecyclerViewAdapter);
        recycleView.setPullRefreshEnabled(false);
        recycleView.setLoadMoreEnabled(false);
        lRecyclerViewAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(appContext, PostDetailActivity.class);
            intent.putExtra("PostSid", postList.get(position).getId());
            startActivity(intent);
        });


    }


    @Override
    public void refreshRecycleView(List<NeighborPostTo> list) {

    }

    @Override
    public void showMessage(String message) {
     showInfo(message);
    }
}
