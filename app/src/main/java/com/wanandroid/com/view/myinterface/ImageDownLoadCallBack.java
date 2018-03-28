package com.wanandroid.com.view.myinterface;

import android.graphics.Bitmap;

import java.io.File;

/**
 * author: fupengyu
 * date: 2018/3/27.
 */

public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(File file);
    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();
}
