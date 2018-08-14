package com.wanandroid.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.wanandroid.com.R;
import com.wanandroid.com.activity.BannerActivity;
import com.wanandroid.com.activity.MainActivity;
import com.wanandroid.com.model.pojo.ArticleBean;
import com.wanandroid.com.view.myinterface.HomeAdapterClickListener;

import java.util.List;

/**
 * author: fupengyu
 * date: 2018/3/16.
 */

public class HomePageAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    private Context mContext;

    private HomeAdapterClickListener homeAdapterClickListener;

    public HomePageAdapter(Context context, @Nullable List<ArticleBean> data , HomeAdapterClickListener homeAdapterClickListener) {
        super(R.layout.home_item, data);
        mContext = context;
        this.homeAdapterClickListener = homeAdapterClickListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ArticleBean item) {
        helper.setText(R.id.tv_item_auth0r, item.getAuthor())
                .setText(R.id.tv_item_niceDate, item.getNiceDate())
                .setText(R.id.tv_item_title, Html.fromHtml(item.getTitle()))
                .setText(R.id.tv_item_chapterName, item.getChapterName());

        TextView tv_item_auth0r = helper.getView(R.id.tv_item_auth0r);
        TextView tv_item_title = helper.getView(R.id.tv_item_title);
        TextView tv_item_shoucang = helper.getView(R.id.tv_item_shoucang);

        //收藏动画
        ShineButton po_image2 = helper.getView(R.id.po_image2);
        po_image2.init((Activity) this.mContext);

//        if(item.isCollect() == true) {
//            po_image2.setChecked(true,false);
//        }else {
//            po_image2.setChecked(false);
//        }

        tv_item_auth0r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, item.getAuthor(), Toast.LENGTH_SHORT).show();
            }
        });

        tv_item_title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        po_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeAdapterClickListener.onShouCangClickListener(item);
            }
        });

        tv_item_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeAdapterClickListener.onShouCangClickListener(item);
            }
        });

        //文章列表点击事件↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        //长按事件
        helper.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext, "onLongClick()", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //点击事件
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "onClick()", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, BannerActivity.class);
                intent.putExtra("url", item.getLink());
                mContext.startActivity(intent);
            }
        });
        //↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
    }
}
