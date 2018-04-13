package com.wanandroid.com.activity;

import android.graphics.Canvas;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.orhanobut.logger.Logger;
import com.wanandroid.com.R;
import com.wanandroid.com.adapter.CollectArticleAdapter;
import com.wanandroid.com.base.BaseSwipeBackActivity;
import com.wanandroid.com.model.pojo.ArticleBean;
import com.wanandroid.com.presenter.CollectPresenter;
import com.wanandroid.com.view.CollectView;
import com.wanandroid.com.widget.IconFontTextView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * author: fupengyu
 * date: 2018/4/12.
 */

public class CollectActivity extends BaseSwipeBackActivity<CollectView, CollectPresenter> implements CollectView,
        SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @Bind(R.id.tv_return)
    IconFontTextView tvReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_no_collect)
    TextView tvNoCollect;
    @Bind(R.id.tv_other)
    IconFontTextView tvOther;
    @Bind(R.id.rv_content)
    RecyclerView rvContent;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private CollectArticleAdapter mAdapter;

    @Override
    protected CollectPresenter createPresenter() {
        return new CollectPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_collect;
    }

    @Override
    public void initView() {
        super.initView();

        tvTitle.setText("我的收藏");

        rvContent.setLayoutManager(new LinearLayoutManager(CollectActivity.this));
        mAdapter = new CollectArticleAdapter(CollectActivity.this, null);
        rvContent.setAdapter(mAdapter);
        swipeRefresh.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, rvContent);

        onRefresh();

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rvContent);
        // 开启拖拽
        mAdapter.enableDragItem(itemTouchHelper, R.id.rcv_home, true);
        mAdapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {

            }
        });
        // 开启滑动删除
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Logger.e("onItemSwipeStart");

                viewHolder.itemView.getId();
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                Logger.e("clearView");
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Logger.e("onItemSwiped");
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                Logger.e("onItemSwipeMoving   viewHolder.itemView.getId() == " + viewHolder.getItemId());


//                Logger.e("dx == " + dX + ", dy == " + dY);
//                if(dX >= 120) {
//                    dX = 120;
//                }


            }
        });


    }

    @Override
    public void onRefresh() {
        mPresenter.getRefreshData();
    }

    @Override
    protected void testTransferee() {

    }


    @OnClick(R.id.tv_return)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefreshSuccess(List<ArticleBean> data) {
        showSwipeRefresh(false);
        mAdapter.setNewData(data);
        tvNoCollect.setVisibility(data.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRefreshFail(String errorString) {
        showSwipeRefresh(false);
        Snackbar.make(rvContent, errorString, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMoreSuccess(List<ArticleBean> data) {
        if (data.size() == 0) {
            mAdapter.loadMoreEnd();
        } else {
            mAdapter.addData(data);
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onLoadMoreFail(String errorString) {
        showSwipeRefresh(false);
        mAdapter.loadMoreComplete();
        Snackbar.make(rvContent, errorString, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void loadComplete() {
        showSwipeRefresh(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getMoreData();
    }

    private void showSwipeRefresh(boolean isRefresh) {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(isRefresh);
            }
        });
    }
}
