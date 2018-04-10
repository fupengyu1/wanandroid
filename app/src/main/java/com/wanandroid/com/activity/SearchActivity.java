package com.wanandroid.com.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.library.viewspread.helper.BaseViewHelper;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.orhanobut.logger.Logger;
import com.wanandroid.com.R;
import com.wanandroid.com.adapter.SearchAdapter;
import com.wanandroid.com.base.BaseSwipeBackActivity;
import com.wanandroid.com.model.pojo.ArticleBean;
import com.wanandroid.com.model.pojo.HotKeyBean;
import com.wanandroid.com.presenter.SearchPresenter;
import com.wanandroid.com.utils.AutoLinefeedLayout;
import com.wanandroid.com.utils.LoadingUtils;
import com.wanandroid.com.utils.SharesUtils;
import com.wanandroid.com.view.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class SearchActivity extends BaseSwipeBackActivity<SearchView, SearchPresenter> implements SearchView, BaseQuickAdapter.RequestLoadMoreListener {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search);
//    }

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    @Bind(R.id.toolbar_container)
    FrameLayout toolbarContainer;

    @Bind(R.id.et_search_activity)
    EditText etSearchActivity;
    @Bind(R.id.iv_search_delete)
    ImageView ivSearchDelete;
    @Bind(R.id.rl_search_title)
    RelativeLayout rlSearchTitle;
    @Bind(R.id.tv_close_activity)
    TextView tvCloseActivity;
    @Bind(R.id.tv_share_activity)
    TextView tvShareActivity;
    @Bind(R.id.ll_search_title)
    LinearLayout llSearchTitle;
    @Bind(R.id.tv_search_hot)
    TextView tvSearchHot;
    @Bind(R.id.auto_search_hot)
    AutoLinefeedLayout autoSearchHot;
    @Bind(R.id.rc_search)
    RecyclerView rcSearch;
    @Bind(R.id.rl_search_hot)
    RelativeLayout rlSearchHot;

    private BaseViewHelper helper;
    private SearchAdapter searchAdapter;

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        super.initView();

        setToolbar();

        //显示loading动画
        LoadingUtils.showLoadingView(SearchActivity.this, "正在加载中。。。");

        //配置adapter
        rcSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        searchAdapter = new SearchAdapter(SearchActivity.this, null);
        rcSearch.setAdapter(searchAdapter);
        searchAdapter.setOnLoadMoreListener(this, rcSearch);
        searchAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        //获取热词
        mPresenter.getHotKeyData();

        //点击搜索显示白色过渡动画
        helper = new BaseViewHelper
                .Builder(SearchActivity.this)
                .isFullWindow(true)//是否全屏显示
                .isShowTransition(false)//是否显示过渡动画
                .setDimColor(Color.WHITE)//遮罩颜色
                .setDimAlpha(300)//遮罩透明度
                .create();//开始动画


//        helper = new BaseViewHelper
//                .Builder(SearchActivity.this)
//                //.setEndView()//如果是两个切换的视图  这里设定最终显示的视图
////                .setTranslationView(v)//设置过渡视图
//                .isFullWindow(false)//是否全屏显示
//                .isShowTransition(false)//是否显示过渡动画
//                .setDimColor(Color.WHITE)//遮罩颜色
//                .setDimAlpha(400)//遮罩透明度
//                //.setTranslationX(0)//x轴平移
//                //.setRotation(360)//旋转
//                //.setScaleX(0)//x轴缩放
//                //.setScaleY(0)//y轴缩放
//                //.setTranslationY(0)//y轴平移
//                //.setDuration(800)//过渡时长
//                //.setInterpolator(new AccelerateDecelerateInterpolator())//设置插值器
//                //设置监听
////                .setOnAnimationListener(new BaseViewHelper.OnAnimationListener() {
////                    @Override
////                    public void onAnimationStartIn() {
////                        Log.e("TAG","onAnimationStartIn");
////                    }
////
////                    @Override
////                    public void onAnimationEndIn() {
////                        Log.e("TAG","onAnimationEndIn");
////                    }
////
////                    @Override
////                    public void onAnimationStartOut() {
////                        Log.e("TAG","onAnimationStartOut");
////                    }
////
////                    @Override
////                    public void onAnimationEndOut() {
////                        Log.e("TAG","onAnimationEndOut");
////                    }
////                })
//                .create();//开始动画


        //MaterialSearchView已弃用搜索框相关
        toolbar.setTitle("Search");
//        setSupportActionBar(toolbar);
        searchView.setVoiceSearch(true);
        searchView.setCursorDrawable(R.drawable.color_cursor_white);
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
    }

    private void setToolbar() {


    }

    @Override
    public void initData() {
        super.initData();

    }

    /**
     * MaterialSearchView 放弃
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void initListener() {
        super.initListener();

        etSearchActivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Logger.i("editable == " + editable.toString() + ", \n etSearchActivity.getText().toString() == " + etSearchActivity.getText().toString());

                //显示loading动画
                LoadingUtils.showLoadingView(SearchActivity.this, "正在加载中。。。");

                if (!etSearchActivity.getText().toString().equals("")) {
                    mPresenter.searchData(etSearchActivity.getText().toString());
                }else {
                    LoadingUtils.hideLoadingView();
                }

            }
        });

        //MaterialSearchView 放弃
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic

                mPresenter.searchData(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        //MaterialSearchView 放弃
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
                toolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                toolbar.setNavigationIcon(null);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //MaterialSearchView 放弃
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {

        if (helper != null && helper.isShowing()) {
            helper.backActivity(this);
        } else {
            super.onBackPressed();
        }

        //MaterialSearchView 放弃
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void getHotKeySuccess(List<HotKeyBean> data) {

        //关闭Loading动画
        LoadingUtils.hideLoadingView();

        autoSearchHot.removeAllViews();

        for (int i = 0; i < data.size(); i++) {
            View inflate = LinearLayout.inflate((Context) SearchActivity.this, R.layout.search_hot_item, null);
            TextView tv_hot_word = (TextView) inflate.findViewById(R.id.tv_hot_word);
            final String name = data.get(i).getName();
            tv_hot_word.setText(name);
            autoSearchHot.addView(inflate);

            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    etSearchActivity.setText(name);
                    // 将光标移至字符串尾部
                    CharSequence charSequence = etSearchActivity.getText();
                    if (charSequence instanceof Spannable) {
                        Spannable spanText = (Spannable) charSequence;
                        Selection.setSelection(spanText, charSequence.length());
                    }
                }
            });
        }
    }

    @Override
    public void getHotKeyFail(String message) {

        LoadingUtils.hideLoadingView();

        Toast.makeText((Context) SearchActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searchDataSuccess(List<ArticleBean> data) {

//        Gson gson = new Gson();
//        String s = gson.toJson(data);
//
//        Logger.e(s);
        //隐藏loading
        LoadingUtils.hideLoadingView();

        if (data == null || data.size() == 0) {

            Toast.makeText((Context) SearchActivity.this, "抱歉，没有搜索项。。。", Toast.LENGTH_SHORT).show();

            tvSearchHot.setVisibility(View.VISIBLE);
            autoSearchHot.setVisibility(View.VISIBLE);
            rcSearch.setVisibility(View.GONE);
        } else {
            tvSearchHot.setVisibility(View.GONE);
            autoSearchHot.setVisibility(View.GONE);
            rcSearch.setVisibility(View.VISIBLE);
        }
        searchAdapter.setNewData(data);
    }

    @Override
    public void searchDataFail(String message) {
        //隐藏loading
        LoadingUtils.hideLoadingView();
        Toast.makeText((Context) SearchActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMoreDataSuccess(List<ArticleBean> data) {
        //隐藏loading
        LoadingUtils.hideLoadingView();
        if (data.size() == 0) {
            searchAdapter.loadMoreEnd();
        } else {
            searchAdapter.addData(data);
            searchAdapter.loadMoreComplete();
        }
    }

    @Override
    public void loadMoreDataFail(String message) {
        //隐藏loading
        LoadingUtils.hideLoadingView();
        Toast.makeText((Context) SearchActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.et_search_activity, R.id.iv_search_delete, R.id.tv_close_activity, R.id.tv_share_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_search_activity:

                break;
            case R.id.iv_search_delete:
                etSearchActivity.setText("");

                tvSearchHot.setVisibility(View.VISIBLE);
                autoSearchHot.setVisibility(View.VISIBLE);
                rcSearch.setVisibility(View.GONE);

                break;
            case R.id.tv_close_activity:
                hintKbOne();
                if (helper != null && helper.isShowing()) {
                    helper.backActivity(this);
                } else {
                    super.onBackPressed();
                }
                break;
            case R.id.tv_share_activity:
                SharesUtils.share(SearchActivity.this, "测试分享");
//
//                String imagePath = "http://www.wanandroid.com/blogimgs/acc23063-1884-4925-bdf8-0b0364a7243e.png";
//
//                final DownLoadImage downLoadImage = new DownLoadImage(UIUtils.getContext(), imagePath, new ImageDownLoadCallBack() {
//                    @Override
//                    public void onDownLoadSuccess(File file) {
//                        Logger.i("file == " + file);
//
//                        Uri uri = Uri.fromFile(file);
//                        SharesUtils.shareImage(SearchActivity.this, uri, "测试分享图片");
//                    }
//
//                    @Override
//                    public void onDownLoadSuccess(Bitmap bitmap) {
//                        Logger.i("bitmap == " + bitmap);
//                    }
//
//                    @Override
//                    public void onDownLoadFailed() {
//
//                        Logger.e("onDownLoadFailed()");
//
//                    }
//                });
//
//                new Thread() {
//                    public void run() {
//                        downLoadImage.run();
//                    }
//                }.start();
                break;
        }
    }

    private String getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        Toast.makeText((Context) SearchActivity.this, "Uri:" + uriPath, Toast.LENGTH_SHORT).show();
        return uriPath;
    }

    //此方法，如果显示则隐藏，如果隐藏则显示
    private void hintKbOne() {
        InputMethodManager imm = (InputMethodManager) SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * adapter加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        String keyWord = etSearchActivity.getText().toString();
        if (!TextUtils.isEmpty(keyWord)) {
            mPresenter.getMoreData(etSearchActivity.getText().toString());
        }
    }
}
