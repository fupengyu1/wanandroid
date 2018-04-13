package com.wanandroid.com.view;

import com.wanandroid.com.model.pojo.UserBean;

/**
 * author: fupengyu
 * date: 2018/4/12.
 */

public interface LoginView {
    void showProgress(String tipString);

    void hideProgress();

    void loginSuccess(UserBean user);

    void registerSuccess(UserBean user);

    void loginFail();

    void registerFail();
}
