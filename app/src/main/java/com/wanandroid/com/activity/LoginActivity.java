package com.wanandroid.com.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wanandroid.com.R;
import com.wanandroid.com.app.AppConst;
import com.wanandroid.com.base.BaseSwipeBackActivity;
import com.wanandroid.com.model.pojo.UserBean;
import com.wanandroid.com.presenter.LoginPresenter;
import com.wanandroid.com.utils.LoadingUtils;
import com.wanandroid.com.utils.PrefUtils;
import com.wanandroid.com.view.LoginView;
import com.wanandroid.com.widget.IconFontTextView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * author: fupengyu
 * date: 2018/4/12.
 */

public class LoginActivity extends BaseSwipeBackActivity<LoginView, LoginPresenter> implements LoginView {

    @Bind(R.id.ic_close)
    IconFontTextView icClose;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_regist)
    Button btnRegist;
    @Bind(R.id.btn_login)
    Button btnLogin;


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void testTransferee() {

    }

    @OnClick({R.id.ic_close, R.id.btn_regist, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_close:
                finish();
                break;
            case R.id.btn_regist:
                if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (etName.getText().toString().length() < 6 || etName.getText().toString().length() < 6) {
                    Toast.makeText(LoginActivity.this, "用户名和密码长度不能小于6位", Toast.LENGTH_SHORT).show();
                } else {
                    mPresenter.toRegist(etName.getText().toString(), etPassword.getText().toString());
                }

                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (etName.getText().toString().length() < 6 || etName.getText().toString().length() < 6) {
                    Toast.makeText(LoginActivity.this, "用户名和密码长度不能小于6位", Toast.LENGTH_SHORT).show();
                } else {
                    mPresenter.toLogin(etName.getText().toString(), etPassword.getText().toString());
                }
                break;
        }
    }

    @Override
    public void showProgress(String tipString) {
        LoadingUtils.showLoadingView(LoginActivity.this, "正在加载中...");
    }

    @Override
    public void hideProgress() {
        LoadingUtils.hideLoadingView();
    }

    @Override
    public void loginSuccess(UserBean user) {
        PrefUtils.setBoolean(LoginActivity.this, AppConst.IS_LOGIN_KEY, true);
        PrefUtils.setString(LoginActivity.this, AppConst.USERNAME_KEY, etName.getText().toString());
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void registerSuccess(UserBean user) {
        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        PrefUtils.setBoolean(LoginActivity.this, AppConst.IS_LOGIN_KEY, true);
        PrefUtils.setString(LoginActivity.this, AppConst.USERNAME_KEY, etName.getText().toString());
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void loginFail() {
        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerFail() {
        Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
    }
}
