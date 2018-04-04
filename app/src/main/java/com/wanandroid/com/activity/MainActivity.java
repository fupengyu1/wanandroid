package com.wanandroid.com.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanandroid.com.R;
import com.wanandroid.com.adapter.FragPagerAdapter;
import com.wanandroid.com.base.BaseActivity;
import com.wanandroid.com.base.BasePresenter;
import com.wanandroid.com.fragment.HomeFragment;
import com.wanandroid.com.fragment.TypeFragment;
import com.wanandroid.com.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener {

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

    private List<Fragment> fragments = new ArrayList<>();
    private int mCurrentPage = 0;

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

        setTabColor(mianTab1, 1);

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
                        setTabColor(mianTab1, 1);
                        break;
                    case 1:
                        setTabColor(mianTab2, 2);
                        break;
                    case 2:
                        setTabColor(mianTab3, 3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTabColor(TextView mianTab, int i) {

        mianTab1.setText("1111");
        mianTab2.setText("2222");
        mianTab3.setText("3333");
        mianTab1.setTextColor(Color.GRAY);
        mianTab2.setTextColor(Color.GRAY);
        mianTab3.setTextColor(Color.GRAY);

        switch (i) {
            case 1:
                mianTab.setText("选中1");
                break;
            case 2:
                mianTab.setText("选中2");
                break;
            case 3:
                mianTab.setText("选中3");
                break;
        }

        mianTab.setTextColor(Color.BLUE);

    }

    @OnClick({R.id.mian_tab_1, R.id.mian_tab_2, R.id.mian_tab_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mian_tab_1:
                mainViewpager.setCurrentItem(0);
                setTabColor(mianTab1, 1);
                break;
            case R.id.mian_tab_2:
                mainViewpager.setCurrentItem(1);
                setTabColor(mianTab2, 2);
                break;
            case R.id.mian_tab_3:
                mainViewpager.setCurrentItem(2);
                setTabColor(mianTab3, 3);
                break;
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
