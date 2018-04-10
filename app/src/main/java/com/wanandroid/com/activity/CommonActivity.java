package com.wanandroid.com.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.wanandroid.com.base.BaseSwipeBackActivity;
import com.wanandroid.com.presenter.CommonPresent;
import com.wanandroid.com.view.CommonView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * author: fupengyu
 * date: 2018/4/8.
 */

public class CommonActivity extends BaseSwipeBackActivity<CommonView, CommonPresent> implements CommonView {

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

    PictureAdapter pictureAdapter;

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
    protected CommonPresent createPresenter() {
        return new CommonPresent();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_commom;
    }

    @Override
    protected void testTransferee() {

        Logger.e("testTransferee()");

        config = TransferConfig.build()
                .setThumbnailImageList(thumbnailImageList)
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
                        saveImageByUniversal(imageView);
                    }
                })
                .create();

        rclCommon1.setAdapter(new CommonActivity.NineGridAdapter());
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

//////////////////////////////////////////////////////////////////////////////////

        rclCommon1.setLayoutManager(new GridLayoutManager(this, 3));

        rclCommon.setLayoutManager(new GridLayoutManager(CommonActivity.this, 3));
        pictureAdapter = new PictureAdapter(CommonActivity.this, null);
        rclCommon.setAdapter(pictureAdapter);

        mPresenter.getUrlList();
    }

    @Override
    public void getPicUrlListSuccess(List<String> posters) {

        if (pictureAdapter != null && posters != null) {

            Logger.e("posters == " + posters);

            pictureAdapter.setNewData(posters);
        }
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
