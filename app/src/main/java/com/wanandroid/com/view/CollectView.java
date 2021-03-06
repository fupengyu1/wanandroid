package com.wanandroid.com.view;


import com.wanandroid.com.model.pojo.ArticleBean;

import java.util.List;

/**
 * user：lqm
 * desc：我的收藏
 */

public interface CollectView {

    void onRefreshSuccess(List<ArticleBean> data);

    void onRefreshFail(String errorString);

    void onLoadMoreSuccess(List<ArticleBean> data);

    void onLoadMoreFail(String errorString);

    void loadComplete();
}
