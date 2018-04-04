package com.wanandroid.com.fragment;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wanandroid.com.R;
import com.wanandroid.com.adapter.SearchAdapter;
import com.wanandroid.com.base.BaseFragment;
import com.wanandroid.com.model.pojo.ArticleBean;
import com.wanandroid.com.presenter.TypePresent;
import com.wanandroid.com.utils.AutoLinefeedLayout;
import com.wanandroid.com.utils.LoadingUtils;
import com.wanandroid.com.utils.UIUtils;
import com.wanandroid.com.view.TypeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author: fupengyu
 * date: 2018/3/5.
 */

public class TypeFragment extends BaseFragment<TypeView, TypePresent> implements TypeView, BaseQuickAdapter.RequestLoadMoreListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.rcv_type)
    RecyclerView rcvType;
    @Bind(R.id.ll_tag)
    AutoLinefeedLayout llTag;
    private SearchAdapter homePageAdapter;

    @Override
    protected TypePresent createPresenter() {
        return new TypePresent(getActivity());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_type;
    }

    public static TypeFragment newInstance() {
        return new TypeFragment();
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);

        //显示loading动画
        LoadingUtils.showLoadingView(getActivity(), "正在加载中。。。");

        rcvType.setLayoutManager(new LinearLayoutManager(UIUtils.getContext()));
        homePageAdapter = new SearchAdapter(UIUtils.getContext(), null);
        rcvType.setAdapter(homePageAdapter);
        homePageAdapter.setOnLoadMoreListener(this, rcvType);
        mPresenter.getTagData();

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .navigationBarColor(R.color.btn3)
                .init();
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public TabLayout getTabLayout() {
        return tabLayout;
    }

    @Override
    public AutoLinefeedLayout getTagLayout() {
        return llTag;
    }

    @Override
    public SearchAdapter getAdapter() {
        return homePageAdapter;
    }

    @Override
    public void getDataError(String message) {
        //关闭Loading动画
        LoadingUtils.hideLoadingView();
        Snackbar.make(rcvType, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void getRefreshDataSuccess(List<ArticleBean> data) {
        //关闭Loading动画
        LoadingUtils.hideLoadingView();
        homePageAdapter.setNewData(data);
    }

    @Override
    public void getMoreDataSuccess(List<ArticleBean> data) {
        //关闭Loading动画
        LoadingUtils.hideLoadingView();
        if (data.size() != 0) {
            homePageAdapter.addData(data);
            homePageAdapter.loadMoreComplete();
        } else {
            homePageAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onDestroyView() {

        //关闭Loading动画
        LoadingUtils.hideLoadingView();

        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onLoadMoreRequested() {
        //显示loading动画
        LoadingUtils.showLoadingView(getActivity(), "正在加载中。。。");
        mPresenter.getMoreData();
    }
}
