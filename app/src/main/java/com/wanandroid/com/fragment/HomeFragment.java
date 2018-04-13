package com.wanandroid.com.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.gyf.barlibrary.ImmersionBar;
import com.library.viewspread.helper.BaseViewHelper;
import com.orhanobut.logger.Logger;
import com.wanandroid.com.R;
import com.wanandroid.com.activity.BannerActivity;
import com.wanandroid.com.activity.SearchActivity;
import com.wanandroid.com.adapter.HomePageAdapter;
import com.wanandroid.com.base.BaseFragment;
import com.wanandroid.com.model.ResponseData;
import com.wanandroid.com.model.pojo.ArticleBean;
import com.wanandroid.com.model.pojo.BannerBean;
import com.wanandroid.com.presenter.HomePresenter;
import com.wanandroid.com.view.HomeView;
import com.wanandroid.com.view.myinterface.HomeAdapterClickListener;
import com.wanandroid.com.view.myinterface.HomeFragmentListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author: fupengyu
 * date: 2018/3/5.
 */

public class HomeFragment extends BaseFragment<HomeView, HomePresenter> implements HomeView, OnBannerListener, BaseQuickAdapter.RequestLoadMoreListener, HomeAdapterClickListener {

    //    @Bind(R.id.banner)
//    Banner banner;
    @Bind(R.id.rcv_home)
    RecyclerView rcvHome;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.rl_search)
    RelativeLayout rlSearch;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_home_menu)
    ImageView ivHomeMenu;

    private Banner banner;

    private List<BannerBean> bannerDatas = new ArrayList<>();

    private HomePageAdapter homePageAdapter;
    private TextView tv_header;
    private SimpleMarqueeView marqueeView;
    private List<ArticleBean> listData = new ArrayList<>();

    private HomeFragmentListener homeFragmentListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImmersionBar.setTitleBar(getActivity(), toolbar);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        homeFragmentListener = (HomeFragmentListener) context;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void initView(View rootView) {
        super.initView(rootView);

        rcvHome.setLayoutManager(new LinearLayoutManager(getContext()));
        homePageAdapter = new HomePageAdapter(getContext(), null, new HomeAdapterClickListener() {
            @Override
            public void onShouCangClickListener(ArticleBean item) {
                Logger.e("onShouCangClickListener item == " + item);

                mPresenter.getCollectArticle(item);
            }
        });
        rcvHome.setAdapter(homePageAdapter);
        homePageAdapter.setOnLoadMoreListener(this, rcvHome);
        homePageAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //添加头布局
        View headView = View.inflate(getActivity(), R.layout.home_header, null);
//        marqueeView = (SimpleMarqueeView) headView.findViewById(R.id.simpleMarqueeView);
        banner = (Banner) headView.findViewById(R.id.banner);
        homePageAdapter.addHeaderView(headView);
        onRefresh();
    }

    @Override
    public void initListener() {
        super.initListener();

        rcvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;

                Logger.e("banner.getHeight() == " + banner.getHeight());

                if (totalDy <= banner.getHeight()) {
                    float alpha = (float) totalDy / banner.getHeight();
                    toolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(mActivity, R.color.colorPrimary), alpha));
//                    Animation animation = new AlphaAnimation(0f, alpha);
//                    tvSearch.startAnimation(animation);
//                    ivSearch.startAnimation(animation);

                    tvSearch.setAlpha(alpha);
                    ivSearch.setAlpha(alpha);
                    ivHomeMenu.setAlpha(alpha);

                } else {
                    toolbar.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(mActivity, R.color.colorPrimary), 1));
                    tvSearch.setAlpha(1f);
                    ivSearch.setAlpha(1f);
                    ivHomeMenu.setAlpha(1f);
                }
                //在Fragment里使用的时候，并且加载Fragment的Activity配置了android:configChanges="orientation|keyboardHidden|screenSize"属性时，
                //不建议使用ImmersionBar里的addViewSupportTransformColor()方法实现标题滑动渐变
                //原因是会导致影响其他页面的沉浸式效果，除非每个页面的沉浸式参数都一样
//                mImmersionBar.addViewSupportTransformColor(mToolbar, R.color.colorPrimary);
//                if (totalDy <= bannerHeight) {
//                    float alpha = (float) totalDy / bannerHeight;
//                    mImmersionBar.statusBarAlpha(alpha)
//                            .init();
//                } else {
//                    mImmersionBar.statusBarAlpha(1.0f)
//                            .init();
//                }
            }
        });

        //搜索按钮点击事件
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
//                startActivity(intent);

                new BaseViewHelper
                        .Builder(getActivity(), view)
                        .startActivity(intent);
            }
        });

        ivHomeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                MainActivity.initMainActivity().showNavigationView();

                homeFragmentListener.showNavigationView();

            }
        });
    }

    @Override
    public void initData() {
        super.initData();

    }

    private void onRefresh() {
        mPresenter.getBannerData();
        mPresenter.getRefreshData();
    }

    @Override
    public void showRefreshView(Boolean refresh) {

    }

    @Override
    public void getBannerDataSuccess(List<BannerBean> data) {

        bannerDatas = data;

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合

        List<String> imagePathList = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            String imagePath = data.get(i).getImagePath();

            Logger.i("imagePath == " + imagePath);

            imagePathList.add(imagePath);
        }
        banner.setImages(imagePathList);

        //点击事件
        banner.setOnBannerListener(this);

        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    public void getDataError(String message) {

    }

    @Override
    public void getRefreshDataSuccess(final List<ArticleBean> data) {

        listData = data;

        homePageAdapter.setNewData(data);

        //公告
//        final List<String> datas = new ArrayList<>();
//        //SimpleMarqueeView<T>，SimpleMF<T>：泛型T指定其填充的数据类型，比如String，Spanned等
//        SimpleMF<String> marqueeFactory = new SimpleMF<String>(getActivity());
////        marqueeFactory.setData(data);
//        for (int i = 0; i < data.size(); i++) {
//            datas.add(data.get(i).getTitle());
//            marqueeFactory.setData(datas);
//        }
//        marqueeView.setAnimDuration(1000);
////        marqueeView.setInAndOutAnim(R.anim.in_top, R.anim.bottom_out);
//        marqueeView.setMarqueeFactory(marqueeFactory);
//        marqueeView.startFlipping();
//
//        marqueeView.setOnItemClickListener(new OnItemClickListener<TextView, String>() {
//            @Override
//            public void onItemClickListener(TextView mView, String mData, int mPosition) {
//                /**
//                 * 注意：
//                 * 当MarqueeView有子View时，mView：当前显示的子View，mData：mView所填充的数据，mPosition：mView的索引
//                 * 当MarqueeView无子View时，mView：null，mData：null，mPosition：－1
//                 */
//                Toast.makeText(getContext(), String.format("mPosition:%s,mData:%s,mView:%s,.", mPosition, mData, mView), Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(getActivity(), BannerActivity.class);
//                intent.putExtra("url", data.get(mPosition).getLink());
//                getActivity().startActivity(intent);
//            }
//        });

    }

    @Override
    public void getMoreDataSuccess(List<ArticleBean> data) {

        listData = data;

        if (data.size() != 0) {
            homePageAdapter.addData(data);
            homePageAdapter.loadMoreComplete();
        } else {
            homePageAdapter.loadMoreEnd();
        }
    }

    @Override
    public void getCollectArticleSuc(ResponseData<String> s) {
        if (s.getErrorCode() == 0) {
            Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), s.getErrorMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getCollectArticleFail(String errorMessage) {
        Logger.e("getCollectArticleSuc errorMessage == " + errorMessage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(getActivity(), BannerActivity.class);
        intent.putExtra("url", bannerDatas.get(position).getUrl());
        startActivity(intent);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getMoreData();
    }

    @Override
    public void onShouCangClickListener(ArticleBean item) {
        mPresenter.getCollectArticle(item);
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

            //Picasso 加载图片简单用法
//            Picasso.with(context).load(path).into(imageView);
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColorTransformEnable(false)
                .navigationBarColor(R.color.colorPrimary)
                .init();

//        mImmersionBar.statusBarDarkFont(true, 0.2f)
//                .navigationBarColor(R.color.btn3)
//                .init();
    }
}
