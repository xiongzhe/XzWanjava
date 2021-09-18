package com.xiongz.wanjava.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiongz.android.core.fragments.JzFragment;
import com.xiongz.android.core.net.rx.RxNetClient;
import com.xiongz.android.core.ui.loader.XzLoader;
import com.xiongz.android.core.ui.refresh.RefreshHandler;
import com.xiongz.android.core.util.json.FastjsonUtil;
import com.xiongz.android.core.util.operate.OperateUtil;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.common.images.GlideImageLoader;
import com.xiongz.wanjava.common.net.ConstUrl;
import com.xiongz.wanjava.common.net.NetManager;
import com.xiongz.wanjava.common.net.NetParams;
import com.xiongz.wanjava.common.net.ObserverProxy;
import com.xiongz.wanjava.common.net.WanNetEntity;
import com.xiongz.wanjava.common.web.CommonWebActivity;
import com.xiongz.wanjava.db.ProfileManager;
import com.xiongz.wanjava.db.UserProfile;
import com.xiongz.wanjava.ui.home.adapter.ArticleAdapter;
import com.xiongz.wanjava.ui.home.entity.ArticleEntity;
import com.xiongz.wanjava.ui.home.entity.BannerEntity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页
 *
 * @author xiongz
 * @date 2021/9/1
 */
public class HomeFragment extends JzFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    // 适配器
    private ArticleAdapter mAdapter;
    // 消息数据列表
    private final List<ArticleEntity> mDatas = new ArrayList<>();
    // 当前页数
    private int mPage = 0;
    // 总记录数
    private int mTotal;
    // 刷新
    private RefreshHandler mRefreshHandler;
    // banner layout
    private View mBannerView;
    // 通栏
    private Banner mBanner;
    // 通栏新闻列表
    private List<BannerEntity> mBannerDatas = new ArrayList<>();

    @Override
    public Object setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRefreshHandler = new RefreshHandler(swipe, this, R.color.colorPrimary);
        initList();
        initBanner();
        XzLoader.showLoading(getProxyActivity());
        requestBanner();
        requestArticle();
    }

    @Override
    public void onRefresh() {
        mDatas.clear();
        mPage = 0;
        requestArticle();
        requestBanner();
    }

    /**
     * 初始化列表
     */
    private void initList() {
        LinearLayoutManager manager = new LinearLayoutManager(getProxyActivity());
        recyclerView.setLayoutManager(manager);
        mAdapter = new ArticleAdapter(getProxyActivity(), true, mDatas);
        View emptyView = LayoutInflater.from(getProxyActivity()).inflate(R.layout.view_empty_data, null);
        emptyView.findViewById(R.id.rl_empty_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XzLoader.showLoading(getProxyActivity());
                onRefresh();
            }
        });
        mAdapter.setEmptyView(emptyView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                if (OperateUtil.isFastClick()) return;
                ArticleEntity entity = mAdapter.getData().get(position);
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPage++;
                requestArticle();
            }
        }, recyclerView);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * 初始化通栏
     */
    private void initBanner() {
        mBannerView = LayoutInflater.from(getProxyActivity()).inflate(R.layout.view_banner, null);
        mBanner = mBannerView.findViewById(R.id.banner);
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        mBanner.setImageLoader(new GlideImageLoader(ImageView.ScaleType.CENTER_CROP));
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerEntity entity = mBannerDatas.get(position);
                Bundle bundle = new Bundle();
                bundle.putString(CommonWebActivity.WEB_TITLE, entity.getTitle());
                bundle.putString(CommonWebActivity.WEB_URL, entity.getUrl());
                ActivityUtils.startActivity(bundle, CommonWebActivity.class);
            }
        });
    }

    /**
     * 设置通栏
     */
    private void notifyBanner(List<BannerEntity> banners) {
        mBannerDatas.clear();
        mBannerDatas.addAll(banners);
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (BannerEntity entity : banners) {
            titles.add(entity.getTitle());
            images.add(entity.getImagePath());
        }
        mBanner.setImages(images);
        mBanner.setBannerTitles(titles);
        mBanner.start();
        if (mAdapter.getHeaderLayoutCount() > 0) {
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.addHeaderView(mBannerView);
        }
    }

    /**
     * 消息
     */
    private void requestArticle() {
        String url = NetManager.getInstance().getUrl(NetParams.getPageUrl(ConstUrl.ARTICLE_LIST, mPage));
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
                        XzLoader.dismissDialog();
                        mRefreshHandler.stopRefreshNow();
                    }
                }));
    }

    /**
     * 解析返回数据
     */
    private void parseData(String result) {
        WanNetEntity netEntity = FastjsonUtil.parseObject(result, WanNetEntity.class);
        JSONObject data = (JSONObject) netEntity.getData();
        mTotal = data.getInteger("total");
        String list = data.getJSONArray("datas").toJSONString();
        List<ArticleEntity> datas = FastjsonUtil.parseArray(list, ArticleEntity.class);
        if (mPage == 0) mAdapter.getData().clear(); // 刷新则清除数据
        int size = mAdapter.getData().size();
        if (size >= mTotal) {
            mAdapter.loadMoreEnd();
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.addData(datas);
            mAdapter.loadMoreComplete();
        }
    }

    /**
     * 轮播图请求
     */
    private void requestBanner() {
        String url = NetManager.getInstance().getUrl(ConstUrl.HOME_BANNER);
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
                        parseBannerData(result);
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
    private void parseBannerData(String result) {
        WanNetEntity netEntity = FastjsonUtil.parseObject(result, WanNetEntity.class);
        JSONArray data = (JSONArray) netEntity.getData();
        String list = FastjsonUtil.toJSONString(data);
        List<BannerEntity> banner = FastjsonUtil.parseArray(list, BannerEntity.class);
        notifyBanner(banner);
    }
}
