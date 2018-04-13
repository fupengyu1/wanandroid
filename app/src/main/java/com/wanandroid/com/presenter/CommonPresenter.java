package com.wanandroid.com.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.wanandroid.com.base.BasePresenter;
import com.wanandroid.com.view.CommonView;

import java.util.ArrayList;
import java.util.List;

/**
 * author: fupengyu
 * date: 2018/4/8.
 */

public class CommonPresenter extends BasePresenter<CommonView> {

    private static final String POSTERS_PATH = "https://raw.githubusercontent.com/stfalcon-studio/FrescoImageViewer/v.0.5.0/images/posters";
    protected static final int READ_EXTERNAL_STORAGE = 100;
    private List<String> images;


    public List<String> getPosters() {

        List<String> urls = new ArrayList<>();

        urls.add(POSTERS_PATH + "/Vincent.jpg");
        urls.add(POSTERS_PATH + "/Jules.jpg");
        urls.add(POSTERS_PATH + "/Korben.jpg");
        urls.add(POSTERS_PATH + "/Toretto.jpg");
        urls.add(POSTERS_PATH + "/Marty.jpg");
        urls.add(POSTERS_PATH + "/Driver.jpg");
        urls.add(POSTERS_PATH + "/Frank.jpg");
        urls.add(POSTERS_PATH + "/Max.jpg");
        urls.add(POSTERS_PATH + "/Daniel.jpg");

        return urls;
    }

    public void getUrlList() {
        getView().getPicUrlListSuccess(getPosters());
    }

    public void getLocalPicUrlList(Activity activity) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE);
        } else {
            images = getLatestPhotoPaths(activity, 9);
            getView().getLocalPicUrlListSuccess(images);
        }
    }


    /**
     * 使用ContentProvider读取SD卡最近图片
     *
     * @param maxCount 读取的最大张数
     * @return
     */
    private List<String> getLatestPhotoPaths(Activity activity, int maxCount) {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String key_DATA = MediaStore.Images.Media.DATA;

        ContentResolver mContentResolver = activity.getContentResolver();

        // 只查询jpg和png的图片,按最新修改排序
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);

        List<String> latestImagePaths = null;
        if (cursor != null) {
            //从最新的图片开始读取.
            //当cursor中没有数据时，cursor.moveToLast()将返回false
            if (cursor.moveToLast()) {
                latestImagePaths = new ArrayList<String>();

                while (true) {
                    // 获取图片的路径
                    String path = "file:" + cursor.getString(0);
                    if (!latestImagePaths.contains(path))
                        latestImagePaths.add(path);

                    if (latestImagePaths.size() >= maxCount || !cursor.moveToPrevious()) {
                        break;
                    }
                }
            }
            cursor.close();
        }

        return latestImagePaths;
    }
}
