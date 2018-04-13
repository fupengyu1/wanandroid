package com.wanandroid.com.presenter;

import com.wanandroid.com.api.WanService;
import com.wanandroid.com.base.BasePresenter;
import com.wanandroid.com.helper.rxjavahelper.RxObserver;
import com.wanandroid.com.helper.rxjavahelper.RxResultHelper;
import com.wanandroid.com.helper.rxjavahelper.RxSchedulersHelper;
import com.wanandroid.com.model.pojo.UserBean;
import com.wanandroid.com.view.LoginView;

import io.reactivex.disposables.Disposable;

/**
 * author: fupengyu
 * date: 2018/4/12.
 */

public class LoginPresenter extends BasePresenter<LoginView>{

    //登录
    public void toLogin(String username, String password) {

        WanService.login(username, password)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<UserBean>() {

                    @Override
                    public void _onSubscribe(Disposable d) {
                        getView().showProgress("正在登陆...");
                    }

                    @Override
                    public void _onNext(UserBean userBean) {
                        getView().loginSuccess(userBean);
                        getView().hideProgress();
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().loginFail();
                        getView().hideProgress();
                    }

                    @Override
                    public void _onComplete() {
                        getView().hideProgress();
                    }

                });
    }

    //注册
    public void toRegist(String username, String password) {
        WanService.regist(username, password)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<UserBean>() {
                    @Override
                    public void _onSubscribe(Disposable d) {
                        getView().showProgress("正在注册...");
                    }

                    @Override
                    public void _onNext(UserBean userBean) {
                        getView().registerSuccess(userBean);
                        getView().hideProgress();
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().registerFail();
                        getView().hideProgress();
                    }

                    @Override
                    public void _onComplete() {
                        getView().hideProgress();
                    }
                });
    }

}
