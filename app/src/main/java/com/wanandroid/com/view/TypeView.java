package com.wanandroid.com.view;


import android.support.design.widget.TabLayout;

import com.wanandroid.com.adapter.SearchAdapter;
import com.wanandroid.com.model.pojo.ArticleBean;
import com.wanandroid.com.utils.AutoLinefeedLayout;

import java.util.List;

/**
 * user：lqm
 * desc：分类View
 */

public interface TypeView {

    TabLayout getTabLayout();
    AutoLinefeedLayout getTagLayout();
    SearchAdapter getAdapter();

    void getDataError(String message);
    void getRefreshDataSuccess(List<ArticleBean> data);
    void getMoreDataSuccess(List<ArticleBean> data);
}
