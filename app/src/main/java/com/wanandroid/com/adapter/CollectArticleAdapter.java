package com.wanandroid.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wanandroid.com.R;
import com.wanandroid.com.activity.BannerActivity;
import com.wanandroid.com.api.WanService;
import com.wanandroid.com.helper.rxjavahelper.RxObserver;
import com.wanandroid.com.helper.rxjavahelper.RxResultHelper;
import com.wanandroid.com.helper.rxjavahelper.RxSchedulersHelper;
import com.wanandroid.com.model.pojo.ArticleBean;

import java.util.List;

/**
 * user：lqm
 * desc：我的收藏文章列表适配器
 * （这个适配器跟ArticleListAdapter一样的，但是接口收藏列表接口返回的数据collect字段有问题，所以这里分开）
 */

public class CollectArticleAdapter extends BaseItemDraggableAdapter<ArticleBean, BaseViewHolder> {

    private Context mContext;

    public CollectArticleAdapter(Context context, @Nullable List<ArticleBean> data) {
        super(R.layout.home_item, data);
        mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ArticleBean item) {
        helper.setText(R.id.tv_item_auth0r, item.getAuthor())
                .setText(R.id.tv_item_niceDate, item.getNiceDate())
                .setText(R.id.tv_item_title, Html.fromHtml(item.getTitle()))
                .setText(R.id.tv_item_chapterName, item.getChapterName())
                .setText(R.id.tv_item_shoucang, "取消收藏");

//
        TextView tvCollect = (TextView) helper.getView(R.id.tv_item_shoucang);
//        tvCollect.setText(UIUtils.getString(R.string.ic_collect_sel));
//        tvCollect.setTextColor(UIUtils.getColor(R.color.main));
//
        tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unCollectArticler(helper.getLayoutPosition(), item);
            }
        });

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BannerActivity.runActivity(mContext, item.getLink());

                Intent intent = new Intent(mContext, BannerActivity.class);
                intent.putExtra("url", item.getLink());
                mContext.startActivity(intent);
            }
        });

    }

    private void unCollectArticler(int position, ArticleBean bean) {
        WanService.unCollectArticle(bean.getId(),bean.getOriginId(),true)
                .compose(RxSchedulersHelper.io_main())
                .compose(RxResultHelper.handleResult())
                .subscribe(new RxObserver<String>() {
                    @Override
                    public void _onNext(String s) {
//                        T.showShort(mContext, "取消收藏");
                        getData().remove(position);
                        if (position != 0){
                            notifyItemRemoved(position);
                        }else{
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void _onError(String errorMessage) {
//                        T.showShort(mContext, "取消收藏失败");
                    }
                });
    }
}
