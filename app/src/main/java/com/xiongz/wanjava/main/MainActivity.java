package com.xiongz.wanjava.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FragmentUtils;
import com.xiongz.android.core.activities.JzActivity;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.ui.home.HomeFragment;
import com.xiongz.wanjava.ui.me.MeFragment;
import com.xiongz.wanjava.ui.project.ProjectFragment;
import com.xiongz.wanjava.ui.tree.TreeArrFragment;
import com.xiongz.wanjava.ui.wechat.WechatFragment;

import butterknife.BindView;
import q.rorbin.badgeview.QBadgeView;

/**
 * 主页面
 *
 * @author xiongz
 * @date 2018/9/4
 */
public class MainActivity extends JzActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public final static String CUR_TAB_INDEX = "index";

    @BindView(R.id.bnv_main)
    BottomNavigationView navigation;
    @BindView(R.id.fl_main)
    FrameLayout flMain;

    // 底部tab item
    private BottomNavigationItemView itemView;
    // 消息角标
    private QBadgeView mQBadgeView;
    // 相关模块
    private Fragment[] mFrags = new Fragment[5];
    // 当前页面下标
    private int mTabIndex = 0;

    @Override
    protected Object getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CUR_TAB_INDEX, mTabIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTabIndex = savedInstanceState.getInt(CUR_TAB_INDEX);
        switchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_home);

        // finish其他activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityUtils.finishOtherActivities(MainActivity.class, true);
            }
        }, 200);

        // 消息未读角标
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        View tab = menuView.getChildAt(1);
        itemView = (BottomNavigationItemView) tab;
        mQBadgeView = new QBadgeView(this);
        mQBadgeView.setGravityOffset(15, 0, true);
        if (itemView != null) mQBadgeView.bindTarget(itemView);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int tab;
        switch (menuItem.getItemId()) {
            // 首页
            case R.id.navigation_home:
                tab = 0;
                if (mFrags[tab] == null) {
                    mFrags[tab] = new HomeFragment();
                    FragmentUtils.add(getSupportFragmentManager(), mFrags[tab], R.id.fl_main);
                }
                FragmentUtils.showHide(mFrags[tab], mFrags[mTabIndex]);
                mTabIndex = tab;
                return true;
            // 项目
            case R.id.navigation_message:
                tab = 1;
                if (mFrags[tab] == null) {
                    mFrags[tab] = new ProjectFragment();
                    FragmentUtils.add(getSupportFragmentManager(), mFrags[tab], R.id.fl_main);
                }
                FragmentUtils.showHide(mFrags[tab], mFrags[mTabIndex]);
                mTabIndex = tab;
                return true;
            // 广场
            case R.id.navigation_contact:
                tab = 2;
                if (mFrags[tab] == null) {
                    mFrags[tab] = new TreeArrFragment();
                    FragmentUtils.add(getSupportFragmentManager(), mFrags[tab], R.id.fl_main);
                }
                FragmentUtils.showHide(mFrags[tab], mFrags[mTabIndex]);
                mTabIndex = tab;
                return true;
            // 公众号
            case R.id.navigation_form:
                tab = 3;
                if (mFrags[tab] == null) {
                    mFrags[tab] = new WechatFragment();
                    FragmentUtils.add(getSupportFragmentManager(), mFrags[tab], R.id.fl_main);
                }
                FragmentUtils.showHide(mFrags[tab], mFrags[mTabIndex]);
                mTabIndex = tab;
                return true;
            // 我的
            case R.id.navigation_mine:
                tab = 4;
                if (mFrags[tab] == null) {
                    mFrags[tab] = new MeFragment();
                    FragmentUtils.add(getSupportFragmentManager(), mFrags[tab], R.id.fl_main);
                }
                FragmentUtils.showHide(mFrags[tab], mFrags[mTabIndex]);
                mTabIndex = tab;
                return true;
        }
        return false;
    }

    /**
     * 切换页面
     */
    private void switchFragment() {
        switch (mTabIndex) {
            case 0:
                navigation.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                navigation.setSelectedItemId(R.id.navigation_message);
                break;
            case 2:
                navigation.setSelectedItemId(R.id.navigation_contact);
                break;
            case 3:
                navigation.setSelectedItemId(R.id.navigation_form);
                break;
            case 4:
                navigation.setSelectedItemId(R.id.navigation_mine);
                break;
        }
    }
}
