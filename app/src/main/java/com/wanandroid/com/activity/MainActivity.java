package com.wanandroid.com.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wanandroid.com.R;
import com.wanandroid.com.adapter.FragPagerAdapter;
import com.wanandroid.com.app.AppConst;
import com.wanandroid.com.base.BaseActivity;
import com.wanandroid.com.base.BasePresenter;
import com.wanandroid.com.fragment.HomeFragment;
import com.wanandroid.com.fragment.TypeFragment;
import com.wanandroid.com.fragment.UserFragment;
import com.wanandroid.com.utils.PrefUtils;
import com.wanandroid.com.utils.StartActivity;
import com.wanandroid.com.utils.UIUtils;
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
    @Bind(R.id.person)
    ImageView person;
    @Bind(R.id.tv_name_menu)
    TextView tvNameMenu;
    @Bind(R.id.ift_member_menu)
    IconFontTextView iftMemberMenu;
    @Bind(R.id.tv_member_menu)
    TextView tvMemberMenu;
    @Bind(R.id.rl_member_menu)
    RelativeLayout rlMemberMenu;
    @Bind(R.id.ift_wallet_menu)
    IconFontTextView iftWalletMenu;
    @Bind(R.id.tv_wallet_menu)
    TextView tvWalletMenu;
    @Bind(R.id.rl_wallet_menu)
    RelativeLayout rlWalletMenu;
    @Bind(R.id.ift_album_menu)
    IconFontTextView iftAlbumMenu;
    @Bind(R.id.tv_album_menu)
    TextView tvAlbumMenu;
    @Bind(R.id.rl_album_menu)
    RelativeLayout rlAlbumMenu;
    @Bind(R.id.ift_attire_menu)
    IconFontTextView iftAttireMenu;
    @Bind(R.id.tv_attire_menu)
    TextView tvAttireMenu;
    @Bind(R.id.rl_attire_menu)
    RelativeLayout rlAttireMenu;
    @Bind(R.id.ift_file_menu)
    IconFontTextView iftFileMenu;
    @Bind(R.id.tv_file_menu)
    TextView tvFileMenu;
    @Bind(R.id.rl_file_menu)
    RelativeLayout rlFileMenu;
    @Bind(R.id.tv_setting_menu)
    TextView tvSettingMenu;
    @Bind(R.id.tv_logout_menu)
    TextView tvLogoutMenu;

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

        if (PrefUtils.getBoolean(this, AppConst.IS_LOGIN_KEY, false) == false) {
            tvNameMenu.setText("这里是名字");
        } else {
            tvNameMenu.setText(PrefUtils.getString(this, AppConst.USERNAME_KEY, "这里是名字"));
        }

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

        //如果使用NavigationView的header和menu使用以下代码
//        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
////                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
//
//                Bundle bundle = new Bundle();
//
//                switch (item.getItemId()) {
//                    case R.id.favorite:
//                        Toast.makeText(MainActivity.this, "favorite", Toast.LENGTH_SHORT).show();
//
//                        StartActivity.startActivity(MainActivity.this, CommonActivity.class, null);
//
//                        break;
//                    case R.id.wallet:
//                        Toast.makeText(MainActivity.this, "wallet", Toast.LENGTH_SHORT).show();
//
//                        bundle.putString("text", "wallet");
//                        StartActivity.startActivity(MainActivity.this, CommonActivity.class, bundle);
//                        break;
//                    case R.id.photo:
//                        Toast.makeText(MainActivity.this, "photo", Toast.LENGTH_SHORT).show();
//                        bundle.putString("text", "photo");
//                        StartActivity.startActivity(MainActivity.this, CommonActivity.class, bundle);
//                        break;
//                    case R.id.dress:
//                        Toast.makeText(MainActivity.this, "dress", Toast.LENGTH_SHORT).show();
//                        bundle.putString("text", "dress");
//                        StartActivity.startActivity(MainActivity.this, CommonActivity.class, bundle);
//                        break;
//                    case R.id.file:
//                        Toast.makeText(MainActivity.this, "file", Toast.LENGTH_SHORT).show();
//                        bundle.putString("text", "file");
//                        StartActivity.startActivity(MainActivity.this, SwipeBackDemoActivity.class, bundle);
//                        break;
//                }
//                dlMain.closeDrawer(nav);
//                return false;
//            }
//        });
//
//        nav.getHeaderView(0).findViewById(R.id.person).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "这里是头像", Toast.LENGTH_SHORT).show();
//            }
//        });

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
            R.id.iv_main_1, R.id.mian_tab_1, R.id.iv_main_2, R.id.mian_tab_2, R.id.iv_main_3, R.id.mian_tab_3,
            R.id.person, R.id.tv_name_menu, R.id.rl_member_menu, R.id.rl_wallet_menu, R.id.rl_album_menu, R.id.rl_attire_menu, R.id.rl_file_menu,
            R.id.tv_setting_menu, R.id.tv_logout_menu})
    public void onViewClicked(View view) {

        Bundle bundle = new Bundle();

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
            case R.id.person:
                Toast.makeText(MainActivity.this, "这里是头像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_name_menu:
                Toast.makeText(MainActivity.this, "这里是名字", Toast.LENGTH_SHORT).show();
                StartActivity.startActivity(this, LoginActivity.class, null);
                break;
            case R.id.rl_member_menu:
                Toast.makeText(MainActivity.this, "menu", Toast.LENGTH_SHORT).show();
                StartActivity.startActivity(MainActivity.this, CommonActivity.class, null);
                break;
            case R.id.rl_wallet_menu:
                Toast.makeText(MainActivity.this, "wallet", Toast.LENGTH_SHORT).show();
                bundle.putString("text", "钱包");
                StartActivity.startActivity(MainActivity.this, CommonActivity.class, bundle);
                break;
            case R.id.rl_album_menu:
                Toast.makeText(MainActivity.this, "album", Toast.LENGTH_SHORT).show();
                bundle.putString("text", "相册");
                StartActivity.startActivity(MainActivity.this, CommonActivity.class, bundle);
                break;
            case R.id.rl_attire_menu:
                Toast.makeText(MainActivity.this, "attire", Toast.LENGTH_SHORT).show();
                bundle.putString("text", "装扮");
                StartActivity.startActivity(MainActivity.this, CommonActivity.class, bundle);
                break;
            case R.id.rl_file_menu:
                Toast.makeText(MainActivity.this, "file", Toast.LENGTH_SHORT).show();
                bundle.putString("text", "文件");
                StartActivity.startActivity(MainActivity.this, SwipeBackDemoActivity.class, bundle);
                break;
            case R.id.tv_setting_menu:
                Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_logout_menu:
                logout();
                Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
                break;
        }
        //关闭侧滑菜单
        closeNavigationView();
    }

    //注销
    private void logout() {
        PrefUtils.setBoolean(this, AppConst.IS_LOGIN_KEY, false);
        PrefUtils.clearData(UIUtils.getContext(), AppConst.USERNAME_KEY);
        if (dlMain.isDrawerOpen(nav)) {
            tvNameMenu.setText("这里是名字");
        }
    }

    private void closeNavigationView() {
        if (dlMain.isDrawerOpen(nav)) {
            dlMain.closeDrawer(nav);
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
