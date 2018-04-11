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
 * date: 2018/4/10.
 */

public class PictureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Context mContext;

    public PictureAdapter(Context context, @Nullable List<String> data) {
        super(R.layout.picture_layout, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        ImageView firstImage = (ImageView) helper.itemView.findViewById(R.id.firstImage);

        Glide.with(mContext).load(item).into(firstImage);

        helper.setText(R.id.tv_pic_test,item);

        int adapterPosition = helper.getAdapterPosition();

        Logger.e("item == " + item + "position == " + adapterPosition);

    }
}
