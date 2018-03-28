package com.wanandroid.com.view;


import android.support.design.widget.TabLayout;

import com.wanandroid.com.model.pojo.ArticleBean;

import java.util.List;

/**
 * user：lqm
 * desc：分类View
 */

public interface TypeView {

    TabLayout getTabLayout();

    void getDataError(String message);
    void getRefreshDataSuccess(List<ArticleBean> data);
    void getMoreDataSuccess(List<ArticleBean> data);

}
