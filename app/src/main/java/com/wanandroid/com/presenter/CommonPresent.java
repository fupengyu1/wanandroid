package com.wanandroid.com.presenter;

import com.wanandroid.com.base.BasePresenter;
import com.wanandroid.com.view.CommonView;

import java.util.ArrayList;
import java.util.List;

/**
 * author: fupengyu
 * date: 2018/4/8.
 */

public class CommonPresent extends BasePresenter<CommonView> {

    private static final String POSTERS_PATH = "https://raw.githubusercontent.com/stfalcon-studio/FrescoImageViewer/v.0.5.0/images/posters";

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

    public void getUrlList(){
        getView().getPicUrlListSuccess(getPosters());
    }
}
