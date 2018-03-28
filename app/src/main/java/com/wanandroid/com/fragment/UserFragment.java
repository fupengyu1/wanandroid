package com.wanandroid.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.wanandroid.com.R;
import com.wanandroid.com.base.BaseFragment;
import com.wanandroid.com.base.BasePresenter;

import butterknife.Bind;

/**
 * author: fupengyu
 * date: 2018/3/5.
 */

public class UserFragment extends BaseFragment {
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    @Bind(R.id.iv_user_header)
    ImageView ivUserHeader;

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
//        ImmersionBar.setTitleBar(getActivity(), toolbar);
    }

//    @Override
//    protected void initImmersionBar() {
//        super.initImmersionBar();
//        mImmersionBar.statusBarDarkFont(true, 0.2f)
//                .navigationBarColor(R.color.btn3)
//                .init();
//    }
}
