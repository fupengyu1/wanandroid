package com.wanandroid.com.presenter;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wanandroid.com.R;
import com.wanandroid.com.adapter.SearchAdapter;
import com.wanandroid.com.api.WanService;
import com.wanandroid.com.base.BasePresenter;
import com.wanandroid.com.helper.rxjavahelper.RxObserver;
import com.wanandroid.com.helper.rxjavahelper.RxResultHelper;
import com.wanandroid.com.helper.rxjavahelper.RxSchedulersHelper;
import com.wanandroid.com.model.ResponseData;
import com.wanandroid.com.model.pojoVO.ArticleListVO;
import com.wanandroid.com.model.pojoVO.TypeTagVO;
import com.wanandroid.com.utils.AutoLinefeedLayout;
import com.wanandroid.com.utils.LoadingUtils;
import com.wanandroid.com.utils.UIUtils;
import com.wanandroid.com.view.TypeView;

import java.util.ArrayList;
import java.util.List;

/**
 * author: fupengyu
 * date: 2018/4/2.
 */

public class TypePresenter extends BasePresenter<TypeView> {


    private TypeView mTypeView;

    private int mTabSelect; //标记选中的Tab标签
    private int mTagSelect; //标记选中的Tag标签，用户设置背景色
    private int mCurrentPage;
    private SearchAdapter mAdapter;
    private AutoLinefeedLayout llTag;
    private List<TextView> tagTvs;
    private int mId;
    private FragmentActivity mActivity;

    public TypePresenter(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void getTagData() {

        mTypeView = getView();

        WanService.getTypeTagData()
                .compose(RxSchedulersHelper.<ResponseData<List<TypeTagVO>>>io_main())
                .compose(RxResultHelper.<List<TypeTagVO>>handleResult())
                .subscribe(new RxObserver<List<TypeTagVO>>() {
                    @Override
                    public void _onNext(List<TypeTagVO> typeTagVOS) {
                        Gson gson = new Gson();
                        String s = gson.toJson(typeTagVOS);
//                        Logger.e("s == " + s);

                        setTabUI(typeTagVOS);
                        mTabSelect = 0;
                        mTagSelect = 0;
                        getServerData(typeTagVOS.get(0).getChildren().get(0).getId());

                    }

                    @Override
                    public void _onError(String errorMessage) {

                    }
                });
    }

    private void getServerData(int id) {

        mCurrentPage = 0; //从第0页开始
        mAdapter = mTypeView.getAdapter();

        WanService.getTypeDataById(mCurrentPage, id)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>io_main())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .subscribe(new RxObserver<ArticleListVO>() {
                    @Override
                    public void _onNext(ArticleListVO articleListVO) {
                        if (articleListVO.getDatas() != null) {
                            getView().getRefreshDataSuccess(articleListVO.getDatas());
                            mTypeView.getTagLayout().setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataError(errorMessage);
                    }
                });

    }

    private void setTabUI(final List<TypeTagVO> mTagDatas) {
        TabLayout tabLayout = mTypeView.getTabLayout();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (TypeTagVO bean : mTagDatas) {
            tabLayout.addTab(tabLayout.newTab().setText(bean.getName()));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTagUI(mTagDatas, tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (llTag != null && llTag.getVisibility() == View.VISIBLE) {
                    llTag.setVisibility(View.GONE);
                } else {
                    setTagUI(mTagDatas, tab.getPosition());
                }
            }
        });
    }

    //二级Tag
    private void setTagUI(final List<TypeTagVO> mTagDatas, final int position) {
        llTag = mTypeView.getTagLayout();
        llTag.setVisibility(View.VISIBLE);
        llTag.removeAllViews();
        if (tagTvs == null) {
            tagTvs = new ArrayList<>();
        } else {
            tagTvs.clear();
        }
        for (int i = 0; i < mTagDatas.get(position).getChildren().size(); i++) {
            View view = LinearLayout.inflate(mActivity, R.layout.item_tag_layout, null);
            final TextView tvItem = (TextView) view.findViewById(R.id.tv_item);
            tvItem.setText(mTagDatas.get(position).getChildren().get(i).getName());
            llTag.addView(view);
            tagTvs.add(tvItem);

            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //显示loading动画
                    LoadingUtils.showLoadingView(mActivity, "正在加载中。。。");

                    //点击某个tag按钮
                    mTabSelect = position;
                    mTagSelect = finalI;
                    tvItem.setTextColor(UIUtils.getColor(R.color.white));
                    tvItem.setBackgroundResource(R.drawable.shape_tag_sel);
                    mId = mTagDatas.get(position).getChildren().get(finalI).getId();
                    getServerData(mId);
                }
            });
        }

        //设置选中的tag背景
        for (int j = 0; j < tagTvs.size(); j++) {
            if (position == mTabSelect && j == mTagSelect) {
                tagTvs.get(j).setTextColor(UIUtils.getColor(R.color.white));
                tagTvs.get(j).setBackgroundResource(R.drawable.shape_tag_sel);
            } else {
                tagTvs.get(j).setTextColor(UIUtils.getColor(R.color.text0));
                tagTvs.get(j).setBackgroundResource(R.drawable.shape_tag_nor);
            }
        }

    }

    public void getMoreData() {

        mCurrentPage += 1;

        WanService.getTypeDataById(mCurrentPage, mId)
                .compose(RxSchedulersHelper.<ResponseData<ArticleListVO>>io_main())
                .compose(RxResultHelper.<ArticleListVO>handleResult())
                .subscribe(new RxObserver<ArticleListVO>() {
                    @Override
                    public void _onNext(ArticleListVO articleListVO) {
                        getView().getMoreDataSuccess(articleListVO.getDatas());
                    }

                    @Override
                    public void _onError(String errorMessage) {
                        getView().getDataError(errorMessage);
                    }
                });

    }
}
