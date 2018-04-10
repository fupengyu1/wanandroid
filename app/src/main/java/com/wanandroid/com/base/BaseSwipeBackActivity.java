package com.wanandroid.com.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.Logger;
import com.wanandroid.com.R;
import com.wanandroid.com.app.App;

import java.util.List;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public abstract class BaseSwipeBackActivity<V, T extends BasePresenter<V>> extends SwipeBackActivity {


    protected T mPresenter;

    private ImmersionBar mImmersionBar;
    private InputMethodManager imm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.activities.add(this);
        init();

        //判断是否使用MVP模式
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);//因为之后所有的子类都要实现对应的View接口
        }

        //子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
        setContentView(provideContentViewId());
        ButterKnife.bind(this);

        Logger.e("isImmersionBarEnabled() == " + isImmersionBarEnabled());

        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }

//        excuteStatesBar();

        options = new DisplayImageOptions
                .Builder()
                .showImageOnLoading(R.mipmap.ic_empty_photo)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .build();
        transferee = Transferee.getDefault(this);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        initView();
        initData();
        initListener();
        testTransferee();
    }

    /**
     * 解决4.4设置状态栏颜色之后，布局内容嵌入状态栏位置问题
     */
//    private void excuteStatesBar() {
//        ViewGroup mContentView = (ViewGroup) getWindow().findViewById(Window.ID_ANDROID_CONTENT);
//        View mChildView = mContentView.getChildAt(0);
//        if (mChildView != null) {
//            //注意不是设置 ContentView 的 FitsSystemWindows,
//            // 而是设置 ContentView 的第一个子 View ，预留出系统 View 的空间.
//            mChildView.setFitsSystemWindows(true);
//        }
//    }
    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        }
    }

    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    //在setContentView()调用之前调用，可以设置WindowFeature(如：this.requestWindowFeature(Window.FEATURE_NO_TITLE);)
    public void init() {
    }

    public void initView() {
    }

    public void initData() {
    }

    public void initListener() {
    }

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    protected static final int READ_EXTERNAL_STORAGE = 100;
    protected static final int WRITE_EXTERNAL_STORAGE = 101;

    protected DisplayImageOptions options;

    protected Transferee transferee;
    protected TransferConfig config;

    protected GridView gvImages;
    protected List<String> thumbnailImageList;
    protected List<String> sourceImageList;

    /**
     * 使用 Universal 作为图片加载器时，保存图片到相册使用的方法
     *
     * @param imageView
     */
    protected void saveImageByUniversal(ImageView imageView) {
        if (checkWriteStoragePermission()) {
            BitmapDrawable bmpDrawable = (BitmapDrawable) imageView.getDrawable();
            MediaStore.Images.Media.insertImage(
                    getContentResolver(),
                    bmpDrawable.getBitmap(),
                    String.valueOf(System.currentTimeMillis()),
                    "");
            Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkWriteStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE);
            return false;
        }
        return true;
    }

    protected abstract void testTransferee();



}
