package com.wanandroid.com.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.orhanobut.logger.Logger;
import com.wanandroid.com.R;
import com.wanandroid.com.adapter.PictureAdapter;
import com.wanandroid.com.adapter.PictureAdapter2;
import com.wanandroid.com.base.BaseSwipeBackActivity;
import com.wanandroid.com.presenter.CommonPresenter;
import com.wanandroid.com.utils.DialogUtils;
import com.wanandroid.com.view.CommonView;
import com.wanandroid.com.view.myinterface.MyDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * author: fupengyu
 * date: 2018/4/8.
 */

public class CommonActivity extends BaseSwipeBackActivity<CommonView, CommonPresenter> implements CommonView {

    @Bind(R.id.tv_common_tag)
    TextView tvCommonTag;
    @Bind(R.id.tv_common_title)
    TextView tvCommonTitle;
    @Bind(R.id.tl_common)
    Toolbar tlCommon;
    @Bind(R.id.rcl_common)
    RecyclerView rclCommon;
    @Bind(R.id.rcl_common_1)
    RecyclerView rclCommon1;
    @Bind(R.id.rcl_commom_2)
    RecyclerView rclCommom2;

    PictureAdapter pictureAdapter;
    PictureAdapter2 pictureAdapter2;


    {
        thumbnailImageList = new ArrayList<>();
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486263782969.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1485055822651.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486194909983.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486194996586.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486195059137.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486173497249.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486173526402.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486173639603.png@233w_160h_20q");
        thumbnailImageList.add("http://static.fdc.com.cn/avatar/sns/1486172566083.png@233w_160h_20q");

        sourceImageList = new ArrayList<>();
        sourceImageList.add("http://img2.woyaogexing.com/2018/01/25/f5d815584c61d376!500x500.jpg");
        sourceImageList.add("http://img2.woyaogexing.com/2018/01/25/f39e625574dd6169!500x500.jpg");
        sourceImageList.add("http://img2.woyaogexing.com/2018/01/25/4771243daf1c4e38!500x500.jpg");
        sourceImageList.add("http://img2.woyaogexing.com/2018/01/25/991349aa8c98c502!500x500.jpg");
        sourceImageList.add("http://img2.woyaogexing.com/2018/01/25/090cf5fd769351a7!500x500.jpg");
        sourceImageList.add("http://img2.woyaogexing.com/2018/02/02/be4ffaa3df84a9fd!500x500.jpg");
        sourceImageList.add("http://img2.woyaogexing.com/2018/01/16/ebb71389722b2bc4!500x500.jpg");
        sourceImageList.add("http://img2.woyaogexing.com/2018/01/16/56adca0f49dde198!500x500.jpg");
        sourceImageList.add("http://img2.woyaogexing.com/2018/01/16/78b37fd847279e8c!500x500.jpg");
    }


    @Override
    protected CommonPresenter createPresenter() {
        return new CommonPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_commom;
    }

    @Override
    protected void testTransferee() {

        //网络获取图片并加载
        config = TransferConfig.build()
//                .setThumbnailImageList(thumbnailImageList)
                .setSourceImageList(sourceImageList)
                .setMissPlaceHolder(R.mipmap.ic_empty_photo)
                .setErrorPlaceHolder(R.mipmap.ic_empty_photo)
                .setProgressIndicator(new ProgressBarIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setJustLoadHitImage(true)
                .setRecyclerView(rclCommon1)
                .setImageId(R.id.firstImage)
                .setOnLongClcikListener(new Transferee.OnTransfereeLongClickListener() {
                    @Override
                    public void onLongClick(ImageView imageView, int pos) {

                        DialogUtils.showDialog(CommonActivity.this, new MyDialog() {
                            @Override
                            public void setPositiveButton() {
                                Toast.makeText(CommonActivity.this, "确定", Toast.LENGTH_SHORT).show();
                                saveImageByUniversal(imageView);
                            }

                            @Override
                            public void setNegativeButton() {
                                Toast.makeText(CommonActivity.this, "取消", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .create();
        rclCommon1.setAdapter(new NineGridAdapter());
    }

    @Override
    public void initView() {
        super.initView();

        String text = null;
        try {
            Bundle extras = getIntent().getExtras();
            text = extras.getString("text");
            Logger.e("text == " + text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvCommonTag.setText(text);
        tvCommonTitle.setText(text);

        ////////////////////////////////////////////////////////////////////////////////////////////

        rclCommon1.setLayoutManager(new GridLayoutManager(this, 3));

        rclCommon.setLayoutManager(new GridLayoutManager(CommonActivity.this, 3));
        pictureAdapter = new PictureAdapter(CommonActivity.this, null);
        rclCommon.setAdapter(pictureAdapter);

        rclCommom2.setLayoutManager(new GridLayoutManager(this,3));
        pictureAdapter2 = new PictureAdapter2(CommonActivity.this,null);
        rclCommom2.setAdapter(pictureAdapter2);

        mPresenter.getUrlList();
        mPresenter.getLocalPicUrlList(CommonActivity.this);
    }

    @Override
    public void initData() {
        super.initData();

        mPresenter.getLocalPicUrlList(CommonActivity.this);
    }

    @Override
    public void getPicUrlListSuccess(List<String> posters) {
        pictureAdapter.setNewData(posters);
    }

    @Override
    public void getLocalPicUrlListSuccess(List<String> images) {
        pictureAdapter2.setNewData(images);
    }

    /**
     * 使用ContentProvider读取SD卡最近图片
     *
     * @param maxCount 读取的最大张数
     * @return
     */
    private List<String> getLatestPhotoPaths(int maxCount) {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
        String key_DATA = MediaStore.Images.Media.DATA;

        ContentResolver mContentResolver = getContentResolver();

        // 只查询jpg和png的图片,按最新修改排序
        Cursor cursor = mContentResolver.query(mImageUri, new String[]{key_DATA},
                key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
                new String[]{"image/jpg", "image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED);

        List<String> latestImagePaths = null;
        if (cursor != null) {
            //从最新的图片开始读取.
            //当cursor中没有数据时，cursor.moveToLast()将返回false
            if (cursor.moveToLast()) {
                latestImagePaths = new ArrayList<String>();

                while (true) {
                    // 获取图片的路径
                    String path = "file:/" + cursor.getString(0);
                    if (!latestImagePaths.contains(path))
                        latestImagePaths.add(path);

                    if (latestImagePaths.size() >= maxCount || !cursor.moveToPrevious()) {
                        break;
                    }
                }
            }
            cursor.close();
        }

        return latestImagePaths;
    }

    private class NineGridAdapter extends CommonAdapter<String> {
        public NineGridAdapter() {
            super(CommonActivity.this, R.layout.picture_layout, sourceImageList);
//            super(CommonActivity.this, R.layout.picture_layout, thumbnailImageList);
        }

        @Override
        protected void convert(ViewHolder viewHolder, String item, final int position) {
            final ImageView imageView = viewHolder.getView(R.id.firstImage);
            ImageLoader.getInstance().displayImage(item, imageView, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    imageView.setOnClickListener(null);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    imageView.setOnClickListener(null);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    bindTransferee(imageView, position);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    imageView.setOnClickListener(null);
                }
            });
        }
    }


    private void bindTransferee(ImageView imageView, final int position) {
        // 如果指定了缩略图，那么缩略图一定要先加载完毕
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.setNowThumbnailIndex(position);
                transferee.apply(config).show();
            }
        });
    }
}
