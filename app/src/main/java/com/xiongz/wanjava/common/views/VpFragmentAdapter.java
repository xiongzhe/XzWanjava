package com.xiongz.wanjava.common.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * viewpager tablayout
 * 通用适配器
 *
 * @author xiongz
 * @date 2019/3/12
 */
public class VpFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mfragmentList;
    private String[] mTabs;

    public VpFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] tabs) {
        super(fm);
        this.mfragmentList = fragmentList;
        this.mTabs = tabs;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mfragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mfragmentList.size();
    }
}
