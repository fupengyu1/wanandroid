package com.wanandroid.com.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author: fupengyu
 * date: 2018/4/10.
 */

public class PictureBean implements Serializable{

    private List<String> list;

    public List<String> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
