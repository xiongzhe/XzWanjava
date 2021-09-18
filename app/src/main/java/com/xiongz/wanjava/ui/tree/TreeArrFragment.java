package com.xiongz.wanjava.ui.tree;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xiongz.android.core.fragments.JzFragment;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.common.views.VpFragmentAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 广场
 *
 * @author xiongz
 * @date 2021/8/31
 */
public class TreeArrFragment extends JzFragment {

    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    // tabs
    private String[] TABS;
    // fragment list
    private ArrayList<Fragment> mFragments;

    @Override
    public Object setLayout() {
        return R.layout.fragment_tree_arr;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mFragments = new ArrayList<>();
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        TABS = new String[]{"广场", "每日一问", "体系", "导航"};
        mFragments.add(PlazaFragment.newInstance());
        mFragments.add(AskFragment.newInstance());
        mFragments.add(SystemFragment.newInstance());
        mFragments.add(NavigationFragment.newInstance());
        viewPager.setAdapter(new VpFragmentAdapter(getChildFragmentManager(), mFragments, TABS));
        viewPager.setOffscreenPageLimit(TABS.length);
        tab.setupWithViewPager(viewPager);
    }
}