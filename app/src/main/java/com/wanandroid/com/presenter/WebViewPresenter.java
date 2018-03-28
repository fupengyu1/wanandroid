package com.wanandroid.com.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.wanandroid.com.base.BasePresenter;
import com.wanandroid.com.view.CommonWebView;

import java.lang.reflect.Method;

/**
 * author: fupengyu
 * date: 2018/3/15.
 */

public class WebViewPresenter extends BasePresenter<CommonWebView> {


    public void initWebViewSetting( WebView webView, String url) {
        final CommonWebView urlView = getView();
        final ProgressBar progressBar = urlView.getProgressBar();
        final SwipeRefreshLayout refreshLayout = urlView.getRefreshLayout();

        disableAccessibility(urlView.getContext());
//        webView.clearHistory();
        webView.clearCache(true);
        webView.getSettings().setSupportZoom(false);//缩放功能
        webView.getSettings().setBuiltInZoomControls(false);//缩放控件
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不缓存
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadsImagesAutomatically(true);//支持自动加载图片
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                progressBar.setVisibility(View.VISIBLE);
                Logger.e("onPageStarted()");

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                refreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);

                Logger.e("onPageFinished()");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                Logger.e("onReceivedError()");

            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                progressBar.setProgress(newProgress);//设置进度值

                Logger.e("onProgressChanged()");

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

                urlView.setTitle(title);

                Logger.e("onReceivedTitle()");

            }
        });

        webView.loadUrl(url);

    }

    /**
     * 关闭辅助功能，针对4.2.1和4.2.2 崩溃问题 java.lang.NullPointerException at
     * android.webkit.AccessibilityInjector$TextToSpeechWrapper$1.onInit(
     * AccessibilityInjector.java: 753) ... ... at
     * android.webkit.CallbackProxy.handleMessage(CallbackProxy.java:321)
     *
     * @param context
     */
    private static void disableAccessibility(Context context) {
        /**
         * 4.2
         * (Build.VERSION_CODES.JELLY_BEAN_MR1)
         */
        if (Build.VERSION.SDK_INT == 17) {
            try {
                AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
                if (!am.isEnabled()) {
                    return;
                }
                Method set = am.getClass().getDeclaredMethod("setState", int.class);
                set.setAccessible(true);
                set.invoke(am, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 刷新
     * @param webView
     */
    public void getRefresh(final WebView webView) {

        final CommonWebView urlView = getView();
        final ProgressBar progressBar = urlView.getProgressBar();
        final SwipeRefreshLayout refreshLayout = urlView.getRefreshLayout();


        refreshLayout.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(SwipeRefreshLayout parent, @Nullable View child) {
                return webView.getScrollY() > 0;
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (webView != null) {
                    webView.reload();
                }

            }
        });
    }
}
