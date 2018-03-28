package com.wanandroid.com.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @user lqm
 * @desc BasePresenter
 */

public abstract class BasePresenter<V> {

    protected Reference<V> mViewRef;
    private V view;


    public void attachView(V view) {
        this.view = view;
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        if (mViewRef != null) {
            if (mViewRef.get() != null) {
                return mViewRef.get();
            } else {
                return new WeakReference<V>(view).get();
            }
        } else {
            return new WeakReference<V>(view).get();
        }
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
