package com.wanandroid.com.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.wanandroid.com.R;

import java.util.List;

/**
 * author: fupengyu
 * date: 2018/4/11.
 */

public class PictureAdapter2 extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public PictureAdapter2(Context context, @Nullable List<String> data) {
        super(R.layout.picture_layout, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        Logger.e("item2 == " + item);

        Glide.with(mContext).load(item).asBitmap().into((ImageView) helper.itemView.findViewById(R.id.firstImage));
//        Glide.with(mContext).load("file:/storage/emulated/0/DCIM/Camera/1523418420717.jpg").asBitmap().into((ImageView) helper.itemView.findViewById(R.id.firstImage));

    }
}
