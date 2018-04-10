package com.wanandroid.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.wanandroid.com.R;
import com.wanandroid.com.base.BaseFragment;
import com.wanandroid.com.base.BasePresenter;
import com.wanandroid.com.utils.UIUtils;
import com.wanandroid.com.view.myinterface.UserFragmentListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: fupengyu
 * date: 2018/3/5.
 */

public class UserFragment extends BaseFragment {

    @Bind(R.id.parallax)
    ImageView parallax;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.user_NestedScrollView)
    NestedScrollView userNestedScrollView;
    @Bind(R.id.rl_user_header)
    RelativeLayout rlUserHeader;
    @Bind(R.id.iv_user_menu)
    ImageView ivUserMenu;
    @Bind(R.id.tv_user_chakanquanbu)
    TextView tvUserChakanquanbu;
    @Bind(R.id.tv_user_shangpinshoucang_num)
    TextView tvUserShangpinshoucangNum;
    @Bind(R.id.tv_user_zuji)
    TextView tvUserZuji;
    private int mOffset = 0;
    private int mScrollY = 0;

    private UserFragmentListener userFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userFragmentListener = (UserFragmentListener) context;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_user;
    }

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(), toolbar);
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);

        final RefreshLayout refreshLayout = (RefreshLayout) rootView.findViewById(R.id.refreshLayout);
        //设置 Header 为 贝塞尔雷达 样式
//        refreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
//        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        //设置 Header 为 经典 样式
//        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));

        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(3000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                mOffset = offset / 2;
                parallax.setTranslationY(mOffset - mScrollY);
                toolbar.setAlpha(1 - Math.min(percent, 1));
            }
//            @Override
//            public void onHeaderPulling(@NonNull RefreshHeader header, float percent, int offset, int bottomHeight, int maxDragHeight) {
//                mOffset = offset / 2;
//                parallax.setTranslationY(mOffset - mScrollY);
//                toolbar.setAlpha(1 - Math.min(percent, 1));
//            }
//            @Override
//            public void onHeaderReleasing(@NonNull RefreshHeader header, float percent, int offset, int bottomHeight, int maxDragHeight) {
//                mOffset = offset / 2;
//                parallax.setTranslationY(mOffset - mScrollY);
//                toolbar.setAlpha(1 - Math.min(percent, 1));
//            }
        });

        userNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(200);
            private int color = ContextCompat.getColor(UIUtils.getContext(), R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    rlUserHeader.setAlpha(1f * mScrollY / h);
                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    parallax.setTranslationY(mOffset - mScrollY);
                }
                lastScrollY = scrollY;
            }
        });
//        toolbar.setAlpha(0);
        rlUserHeader.setAlpha(0);
        toolbar.setBackgroundColor(0);
    }

    @Override
    public void initData() {
        super.initData();
    }

//    @Override
//    protected void initImmersionBar() {
//        super.initImmersionBar();
//        mImmersionBar.statusBarDarkFont(true, 0.2f)
//                .navigationBarColor(R.color.btn3)
//                .init();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.tv_user_chakanquanbu, R.id.tv_user_shangpinshoucang_num, R.id.tv_user_zuji, R.id.iv_user_menu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_user_chakanquanbu:
                break;
            case R.id.tv_user_shangpinshoucang_num:
                break;
            case R.id.tv_user_zuji:
                break;
            case R.id.iv_user_menu:

                userFragmentListener.showNavigationView();

                break;
        }
    }
}
