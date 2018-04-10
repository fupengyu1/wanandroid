package com.wanandroid.com.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wanandroid.com.R;
import com.wanandroid.com.adapter.FragPagerAdapter;
import com.wanandroid.com.base.BaseActivity;
import com.wanandroid.com.base.BasePresenter;
import com.wanandroid.com.fragment.HomeFragment;
import com.wanandroid.com.fragment.TypeFragment;
import com.wanandroid.com.fragment.UserFragment;
import com.wanandroid.com.utils.StartActivity;
import com.wanandroid.com.view.myinterface.HomeFragmentListener;
import com.wanandroid.com.view.myinterface.TypeFragmentListener;
import com.wanandroid.com.view.myinterface.UserFragmentListener;
import com.wanandroid.com.widget.IconFontTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements HomeFragmentListener, TypeFragmentListener, UserFragmentListener {

    @Bind(R.id.main_viewpager)
    ViewPager mainViewpager;
    @Bind(R.id.ll_main_bottom)
    LinearLayout llMainBottom;
    @Bind(R.id.mian_tab_1)
    TextView mianTab1;
    @Bind(R.id.mian_tab_2)
    TextView mianTab2;
    @Bind(R.id.mian_tab_3)
    TextView mianTab3;
    @Bind(R.id.nav)
    NavigationView nav;
    @Bind(R.id.dl_main)
    DrawerLayout dlMain;
    @Bind(R.id.ll_main_1)
    LinearLayout llMain1;
    @Bind(R.id.ll_main_2)
    LinearLayout llMain2;
    @Bind(R.id.ll_main_3)
    LinearLayout llMain3;
    @Bind(R.id.iv_main_1)
    IconFontTextView ivMain1;
    @Bind(R.id.iv_main_2)
    IconFontTextView ivMain2;
    @Bind(R.id.iv_main_3)
    IconFontTextView ivMain3;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();

        setTabColor(mianTab1, ivMain1);

        fragments.add(HomeFragment.newInstance());
        fragments.add(TypeFragment.newInstance());
        fragments.add(UserFragment.newInstance());

        mainViewpager.setAdapter(new FragPagerAdapter(getSupportFragmentManager(), fragments));
        mainViewpager.setCurrentItem(0, false);

        mainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setTabColor(mianTab1, ivMain1);
                        break;
                    case 1:
                        setTabColor(mianTab2, ivMain2);
                        break;
                    case 2:
                        setTabColor(mianTab3, ivMain3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();

                switch (item.getItemId()) {
                    case R.id.favorite:
                        Toast.makeText(MainActivity.this, "favorite", Toast.LENGTH_SHORT).show();

                        StartActivity.startActivity(MainActivity.this, CommonActivity.class, null);

                        break;
                    case R.id.wallet:
                        Toast.makeText(MainActivity.this, "wallet", Toast.LENGTH_SHORT).show();

                        bundle.putString("text", "wallet");
                        StartActivity.startActivity(MainActivity.this, CommonActivity.class, bundle);
                        break;
                    case R.id.photo:
                        Toast.makeText(MainActivity.this, "photo", Toast.LENGTH_SHORT).show();
                        bundle.putString("text", "photo");
                        StartActivity.startActivity(MainActivity.this, CommonActivity.class, bundle);
                        break;
                    case R.id.dress:
                        Toast.makeText(MainActivity.this, "dress", Toast.LENGTH_SHORT).show();
                        bundle.putString("text", "dress");
                        StartActivity.startActivity(MainActivity.this, CommonActivity.class, bundle);
                        break;
                    case R.id.file:
                        Toast.makeText(MainActivity.this, "file", Toast.LENGTH_SHORT).show();
                        bundle.putString("text", "file");
                        StartActivity.startActivity(MainActivity.this, SwipeBackDemoActivity.class, bundle);
                        break;
                }
                dlMain.closeDrawer(nav);
                return false;
            }
        });

        nav.getHeaderView(0).findViewById(R.id.person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "这里是头像", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setTabColor(TextView mianTab, IconFontTextView iv_main) {

        mianTab1.setTextColor(Color.GRAY);
        mianTab2.setTextColor(Color.GRAY);
        mianTab3.setTextColor(Color.GRAY);
        ivMain1.setTextColor(Color.GRAY);
        ivMain2.setTextColor(Color.GRAY);
        ivMain3.setTextColor(Color.GRAY);

        mianTab.setTextColor(Color.BLUE);
        iv_main.setTextColor(Color.BLUE);
    }

    @OnClick({R.id.ll_main_1, R.id.ll_main_2, R.id.ll_main_3,
            R.id.iv_main_1, R.id.mian_tab_1, R.id.iv_main_2, R.id.mian_tab_2, R.id.iv_main_3, R.id.mian_tab_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_main_1:
                mainViewpager.setCurrentItem(0);
                setTabColor(mianTab1, ivMain1);
                break;
            case R.id.ll_main_2:
                mainViewpager.setCurrentItem(1);
                setTabColor(mianTab2, ivMain2);
                break;
            case R.id.ll_main_3:
                mainViewpager.setCurrentItem(2);
                setTabColor(mianTab3, ivMain3);
                break;
            case R.id.iv_main_1:
                mainViewpager.setCurrentItem(0);
                setTabColor(mianTab1, ivMain1);
                break;
            case R.id.mian_tab_1:
                mainViewpager.setCurrentItem(0);
                setTabColor(mianTab1, ivMain1);
                break;
            case R.id.iv_main_2:
                mainViewpager.setCurrentItem(1);
                setTabColor(mianTab2, ivMain2);
                break;
            case R.id.mian_tab_2:
                mainViewpager.setCurrentItem(1);
                setTabColor(mianTab2, ivMain2);
                break;
            case R.id.iv_main_3:
                mainViewpager.setCurrentItem(2);
                setTabColor(mianTab3, ivMain3);
                break;
            case R.id.mian_tab_3:
                mainViewpager.setCurrentItem(2);
                setTabColor(mianTab3, ivMain3);
                break;
        }
    }

    @Override
    public void showNavigationView() {
        if (dlMain.isDrawerOpen(nav)) {
            dlMain.closeDrawer(nav);
        } else {
            dlMain.openDrawer(nav);
        }
    }

    @Override
    public void isDrawerLockMode(Boolean aBoolean) {
        //在android抽屉开发中，需要关闭手势滑动，来滑出弹出框

        if (aBoolean == false) {
            //打开手势滑动
            dlMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            //关闭手势滑动
            dlMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

    }
}
