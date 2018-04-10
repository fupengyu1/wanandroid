package com.wanandroid.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanandroid.com.R;
import com.wanandroid.com.activity.BannerActivity;
import com.wanandroid.com.model.pojo.ArticleBean;

import java.util.List;

/**
 * author: fupengyu
 * date: 2018/3/16.
 */

public class HomePageAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    private Context mContext;

    public HomePageAdapter(Context context, @Nullable List<ArticleBean> data) {
        super(R.layout.home_item, data);
        mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ArticleBean item) {
        helper.setText(R.id.tv_item_auth0r, item.getAuthor())
                .setText(R.id.tv_item_niceDate, item.getNiceDate())
                .setText(R.id.tv_item_title, Html.fromHtml(item.getTitle()))
                .setText(R.id.tv_item_chapterName, item.getChapterName());

        TextView tv_item_auth0r = helper.getView(R.id.tv_item_auth0r);
        TextView tv_item_title = helper.getView(R.id.tv_item_title);
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
