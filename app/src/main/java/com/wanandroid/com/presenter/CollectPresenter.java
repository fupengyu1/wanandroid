package com.wanandroid.com.presenter;

import com.wanandroid.com.api.WanService;
import com.wanandroid.com.base.BasePresenter;
import com.wanandroid.com.helper.rxjavahelper.RxObserver;
import com.wanandroid.com.helper.rxjavahelper.RxResultHelper;
import com.wanandroid.com.helper.rxjavahelper.RxSchedulersHelper;
import com.wanandroid.com.model.pojoVO.ArticleListVO;
import com.wanandroid.com.view.CollectView;

/**
 * author: fupengyu
 * date: 2018/4/12.
 */

public class CollectPresenter extends BasePresenter<CollectView> {
    private int mCurrentPage;

    //刷新获取数据
    public void getRefreshData() {
        mCurrentPage = 0;
        WanService.getCollectData(mCurrentPage)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<ArticleListVO>() {
                    @Override
                    public void _onNext(ArticleListVO articleListVO) {
                        getView().onRefreshSuccess(articleListVO.getDatas());
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().onRefreshFail(errorMessage);
                    }

                    @Override
                    public void _onComplete() {
                        getView().loadComplete();
                    }
                });

    }

    //获取更多数据
    public void getMoreData() {
        mCurrentPage += 1;
        WanService.getCollectData(mCurrentPage)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<ArticleListVO>() {
                    @Override
                    public void _onNext(ArticleListVO articleListVO) {
                        getView().onLoadMoreSuccess(articleListVO.getDatas());
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().onLoadMoreFail(errorMessage);
                    }
                });
    }
}
