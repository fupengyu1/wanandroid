package com.wanandroid.com.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ProgressBar;

/**
 * user：lqm
 * desc： WebView
 */

public interface CommonWebView {

    Context getContext();

    SwipeRefreshLayout getRefreshLayout();
    ProgressBar getProgressBar();
    void setTitle(String title);

}
