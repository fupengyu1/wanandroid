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
 * date: 2018/3/28.
 */

public class SearchAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    private final Context context;

    public SearchAdapter(Context context, @Nullable List<ArticleBean> data) {
        super(R.layout.home_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ArticleBean item) {
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

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BannerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", item.getLink());
                context.startActivity(intent);
            }
        });
    }
}
