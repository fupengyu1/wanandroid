package com.wanandroid.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.wanandroid.com.R;
import com.wanandroid.com.base.BaseFragment;
import com.wanandroid.com.base.BasePresenter;

import butterknife.Bind;

/**
 * author: fupengyu
 * date: 2018/3/5.
 */

public class TypeFragment extends BaseFragment {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_type;
    }

    public static TypeFragment newInstance() {
        return new TypeFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(), toolbar);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .navigationBarColor(R.color.btn3)
                .init();
    }
}
