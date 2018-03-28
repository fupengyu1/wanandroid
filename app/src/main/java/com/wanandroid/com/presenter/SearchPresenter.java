package com.wanandroid.com.presenter;

import com.wanandroid.com.api.WanService;
import com.wanandroid.com.base.BasePresenter;
import com.wanandroid.com.helper.rxjavahelper.RxObserver;
import com.wanandroid.com.helper.rxjavahelper.RxResultHelper;
import com.wanandroid.com.helper.rxjavahelper.RxSchedulersHelper;
import com.wanandroid.com.model.ResponseData;
import com.wanandroid.com.model.pojo.HotKeyBean;
import com.wanandroid.com.model.pojoVO.ArticleListVO;
import com.wanandroid.com.view.SearchView;

import java.util.List;

/**
 * author: fupengyu
 * date: 2018/3/22.
 */

public class SearchPresenter extends BasePresenter<SearchView> {
    private int mCurrentPage = 0;

    public void searchData(String query) {
        mCurrentPage = 0;
        WanService.searchArticle(mCurrentPage, query)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>io_main())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .subscribe(new RxObserver<ArticleListVO>() {
                    @Override
                    public void _onNext(ArticleListVO articleListVO) {
                        getView().searchDataSuccess(articleListVO.getDatas());
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().searchDataFail(errorMessage);

                    }
                });
    }

    public void getHotKeyData() {
        WanService.getHotKey()
                .compose(RxSchedulersHelper.<ResponseData<List<HotKeyBean>>>io_main())
                .compose(RxResultHelper.<List<HotKeyBean>>handleResult())
                .subscribe(new RxObserver<List<HotKeyBean>>() {
                    @Override
                    public void _onNext(List<HotKeyBean> hotKeyBeans) {
                        getView().getHotKeySuccess(hotKeyBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getHotKeyFail(errorMessage);
                    }
                });
    }

    public void getMoreData(String s) {
        mCurrentPage += 1;
        WanService.searchArticle(mCurrentPage, s)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>io_main())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .subscribe(new RxObserver<ArticleListVO>() {
                    @Override
                    public void _onNext(ArticleListVO articleListVO) {
                        getView().loadMoreDataSuccess(articleListVO.getDatas());
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().loadMoreDataFail(errorMessage);
                    }
                });
    }
}
