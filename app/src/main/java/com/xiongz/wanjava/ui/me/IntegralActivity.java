package com.xiongz.wanjava.ui.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiongz.android.core.activities.JzActivity;
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
import com.xiongz.wanjava.ui.me.adapter.IntegralAdapter;
import com.xiongz.wanjava.ui.me.entity.IntegralEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 积分排行
 *
 * @author xiongz
 * @date 2021/9/24
 */
public class IntegralActivity extends JzActivity implements Toolbar.OnMenuItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String INTEGRAL_RANK = "rank";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.integral_merank)
    TextView tvRank;
    @BindView(R.id.integral_mename)
    TextView tvName;
    @BindView(R.id.integral_mecount)
    TextView tvCount;

    // 适配器
    private IntegralAdapter mAdapter;
    // 消息数据列表
    private final List<IntegralEntity> mDatas = new ArrayList<>();
    // 当前页数
    private int mPage = 0;
    // 总记录数
    private int mTotal;
    // 刷新
    private RefreshHandler mRefreshHandler;
    // 我的积分
    private IntegralEntity mRank;

    @Override
    protected Object getContentView() {
        return R.layout.activity_integral;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = setTitle(this, "积分排行");
        toolbar.inflateMenu(R.menu.menu_integral);
        toolbar.setOnMenuItemClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mRank = bundle.getParcelable(INTEGRAL_RANK);
            tvRank.setText(mRank.getRank());
            tvName.setText(mRank.getUsername());
            tvCount.setText(String.valueOf(mRank.getCoinCount()));
        }

        mRefreshHandler = new RefreshHandler(swipe, this, R.color.colorPrimary);
        initList();
        XzLoader.showLoading(this);
        request();
    }

    @Override
    public boolean onMenuItemClick(final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            // 规则
            case R.id.action_rules:
                Bundle bundle = new Bundle();
                bundle.putString(WanWebActivity.WEB_TITLE, "积分规则");
                bundle.putString(WanWebActivity.WEB_URL, "https://www.wanandroid.com/blog/show/2653");
                ActivityUtils.startActivity(bundle, WanWebActivity.class);
                break;
            // 积分记录
            case R.id.action_history:
                ToastUtils.showLong("积分记录");
                break;
        }
        return true;
    }


    @Override
    public void onRefresh() {
        mDatas.clear();
        mPage = 0;
        request();
    }

    /**
     * 初始化列表
     */
    private void initList() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        mAdapter = new IntegralAdapter(this, R.layout.item_integral,
                mDatas, Integer.parseInt(mRank.getRank()));
        View emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty_data, null);
        emptyView.findViewById(R.id.rl_empty_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XzLoader.showLoading(IntegralActivity.this);
                onRefresh();
            }
        });
        mAdapter.setEmptyView(emptyView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                if (OperateUtil.isFastClick()) return;
                IntegralEntity entity = mAdapter.getData().get(position);
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
        String url = NetManager.getInstance().getUrl(NetParams.getPageUrl(ConstUrl.INTEGRAL_RANK, mPage));
        RxNetClient.builder()
                .url(url)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverProxy(this, new Observer<String>() {
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
        List<IntegralEntity> datas = FastjsonUtil.parseArray(list, IntegralEntity.class);
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