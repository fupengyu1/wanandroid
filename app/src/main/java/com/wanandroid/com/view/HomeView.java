package com.wanandroid.com.view;


import com.wanandroid.com.model.pojo.ArticleBean;
import com.wanandroid.com.model.pojo.BannerBean;

import java.util.List;

/**
 * user：lqm
 * desc：首页
 */

public interface HomeView {

    void showRefreshView(Boolean refresh);
    void getBannerDataSuccess(List<BannerBean> data);
    void getDataError(String message);
    void getRefreshDataSuccess(List<ArticleBean> data);
    void getMoreDataSuccess(List<ArticleBean> data);

}
