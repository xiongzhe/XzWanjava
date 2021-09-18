package com.xiongz.wanjava.ui.tree;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiongz.android.core.fragments.JzFragment;
import com.xiongz.android.core.net.rx.RxNetClient;
import com.xiongz.android.core.ui.loader.XzLoader;
import com.xiongz.android.core.ui.refresh.RefreshHandler;
import com.xiongz.android.core.util.json.FastjsonUtil;
import com.xiongz.android.core.util.operate.OperateUtil;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.common.net.ConstUrl;
import com.xiongz.wanjava.common.net.NetManager;
import com.xiongz.wanjava.common.net.NetParams;
import com.xiongz.wanjava.common.net.ObserverProxy;
import com.xiongz.wanjava.common.net.WanNetEntity;
import com.xiongz.wanjava.ui.home.adapter.ArticleAdapter;
import com.xiongz.wanjava.ui.home.entity.ArticleEntity;
import com.xiongz.wanjava.ui.wechat.WechatSubFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 广场
 *
 * @author xiongz
 * @date 2021/8/31
 */
public class PlazaFragment extends JzFragment implements SwipeRefreshLayout.OnRefreshListener {

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

    public static PlazaFragment newInstance() {
        return new PlazaFragment();
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_plaza;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRefreshHandler = new RefreshHandler(swipe, this, R.color.colorPrimary);
        initList();
        XzLoader.showLoading(getProxyActivity());
        request();
    }

    @Override
    public void onRefresh() {
        mDatas.clear();
        mPage = 0;
        request();
    }

    @OnClick({R.id.fab_button})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.fab_button:
                ToastUtils.showLong("点击添加");
                break;
            default:
                break;
        }
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
                request();
            }
        }, recyclerView);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * 请求
     */
    private void request() {
        String url = NetManager.getInstance().getUrl(NetParams.getPageUrl(ConstUrl.PLAZA_LIST_BY_TREE, mPage));
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
                        mRefreshHandler.stopRefresh();
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
}
