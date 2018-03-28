package com.wanandroid.com.presenter;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.wanandroid.com.api.WanService;
import com.wanandroid.com.base.BasePresenter;
import com.wanandroid.com.helper.rxjavahelper.RxObserver;
import com.wanandroid.com.helper.rxjavahelper.RxResultHelper;
import com.wanandroid.com.helper.rxjavahelper.RxSchedulersHelper;
import com.wanandroid.com.model.ResponseData;
import com.wanandroid.com.model.pojo.BannerBean;
import com.wanandroid.com.model.pojoVO.ArticleListVO;
import com.wanandroid.com.view.HomeView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * author: fupengyu
 * date: 2018/3/5.
 */

public class HomePresenter extends BasePresenter<HomeView> {

    //获取轮播图数据
    public void getBannerData() {

        WanService.getBannerData()
                .compose(RxSchedulersHelper.<ResponseData<List<BannerBean>>>io_main())
                .compose(RxResultHelper.<List<BannerBean>>handleResult())
                .subscribe(new RxObserver<List<BannerBean>>() {
                    @Override
                    public void _onNext(List<BannerBean> bannerBeans) {
                        getView().getBannerDataSuccess(bannerBeans);
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataError(errorMessage);
                    }
                });

    }

    private int mCurrentPage;

    //刷新
    public void getRefreshData() {
        mCurrentPage = 0;

        WanService.getHomeData(mCurrentPage)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>io_main())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .subscribe(new RxObserver<ArticleListVO>() {
                    @Override
                    public void _onNext(ArticleListVO articleListVO) {
                        getView().getRefreshDataSuccess(articleListVO.getDatas());
                        Gson gson = new Gson();
                        String s = gson.toJson(articleListVO);

                        Logger.e("s == " + s);

                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataError(errorMessage);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        getView().showRefreshView(true);
                    }

                    @Override
                    public void _onComplete() {
                        super._onComplete();
                        getView().showRefreshView(false);

                    }
                });

    }

    //加载更多
    public void getMoreData() {
        mCurrentPage = mCurrentPage + 1;

        WanService.getHomeData(mCurrentPage)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>io_main())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .subscribe(new RxObserver<ArticleListVO>() {
                    @Override
                    public void _onNext(ArticleListVO articleListVO) {
                        getView().getMoreDataSuccess(articleListVO.getDatas());
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataError(errorMessage);
                    }
                });
    }
}
