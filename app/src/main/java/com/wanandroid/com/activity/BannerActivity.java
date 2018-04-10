package com.wanandroid.com.activity;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wanandroid.com.R;
import com.wanandroid.com.base.BaseSwipeBackActivity;
import com.wanandroid.com.presenter.WebViewPresenter;
import com.wanandroid.com.view.CommonWebView;

import butterknife.Bind;
import butterknife.OnClick;

public class BannerActivity extends BaseSwipeBackActivity<CommonWebView, WebViewPresenter> implements CommonWebView {
    @Bind(R.id.wv_banner)
    WebView wvBanner;
    @Bind(R.id.pb_banner)
    ProgressBar pbBanner;
    @Bind(R.id.srefresh_banner)
    SwipeRefreshLayout srefreshBanner;
    @Bind(R.id.tv_title_banner)
    TextView tvTitleBanner;
    @Bind(R.id.appbar_banner)
    LinearLayout appbarBanner;
    @Bind(R.id.iv_back_banner)
    ImageView ivBackBanner;

    private String url;

    @Override
    protected WebViewPresenter createPresenter() {
        return new WebViewPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_banner;
    }

    @Override
    public void init() {
        super.init();
        url = getIntent().getStringExtra("url");
    }

    @Override
    protected void onStart() {
        super.onStart();

        //获取webview
        mPresenter.initWebViewSetting(wvBanner, url);
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initListener() {
        super.initListener();

        mPresenter.getRefresh(wvBanner);

    }


    @Override
    public Context getContext() {
        return BannerActivity.this;
    }

    @Override
    public SwipeRefreshLayout getRefreshLayout() {
        return srefreshBanner;
    }

    @Override
    public ProgressBar getProgressBar() {
        return pbBanner;
    }

    @Override
    public void setTitle(String title) {
        tvTitleBanner.setText(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wvBanner.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wvBanner.canGoBack()) {
                wvBanner.goBack();
                return true;
            } else {
                finish();//退出程序
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.iv_back_banner)
    public void onViewClicked() {

        if (wvBanner.canGoBack()) {
            wvBanner.goBack();
        } else {
            finish();//退出程序
        }

    }
}
