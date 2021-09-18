package com.xiongz.wanjava.ui.wechat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.xiongz.android.core.fragments.JzFragment;
import com.xiongz.android.core.net.rx.RxNetClient;
import com.xiongz.android.core.ui.loader.XzLoader;
import com.xiongz.android.core.util.json.FastjsonUtil;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.common.net.ConstUrl;
import com.xiongz.wanjava.common.net.NetManager;
import com.xiongz.wanjava.common.net.ObserverProxy;
import com.xiongz.wanjava.common.net.WanNetEntity;
import com.xiongz.wanjava.common.views.VpFragmentAdapter;
import com.xiongz.wanjava.ui.project.ProjectSubFragment;
import com.xiongz.wanjava.ui.project.entity.TreeEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 公众号
 *
 * @author xiongz
 * @date 2021/8/31
 */
public class WechatFragment extends JzFragment {

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
        return R.layout.fragment_wechat;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mFragments = new ArrayList<>();
        XzLoader.showLoading(getProxyActivity());
        requestWxTree();
    }

    /**
     * 公众号分类请求
     */
    private void requestWxTree() {
        String url = NetManager.getInstance().getUrl(ConstUrl.WX_ARTICLE_TREE);
        RxNetClient.builder()
                .url(url)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverProxy(getProxyActivity(), new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String result) {
                        parseData(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    /**
     * 解析返回数据
     */
    private void parseData(String result) {
        WanNetEntity netEntity = FastjsonUtil.parseObject(result, WanNetEntity.class);
        JSONArray data = (JSONArray) netEntity.getData();
        String list = FastjsonUtil.toJSONString(data);
        List<TreeEntity> trees = FastjsonUtil.parseArray(list, TreeEntity.class);
        TABS = new String[trees.size()];
        for (int i = 0; i < trees.size(); i++) {
            TABS[i] = trees.get(i).getName();
            tab.addTab(tab.newTab().setText(TABS[i]));
            mFragments.add(WechatSubFragment.newInstance(i, trees.get(i).getId()));
        }
        viewPager.setAdapter(new VpFragmentAdapter(getChildFragmentManager(), mFragments, TABS));
        viewPager.setOffscreenPageLimit(TABS.length);
        tab.setupWithViewPager(viewPager);
    }
}