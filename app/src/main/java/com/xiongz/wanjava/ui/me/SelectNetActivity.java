package com.xiongz.wanjava.ui.me;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ShellUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiongz.android.core.activities.JzActivity;
import com.xiongz.android.core.ui.recycler.WrapContentLinearLayoutManager;
import com.xiongz.android.core.util.operate.OperateUtil;
import com.xiongz.wanjava.BuildConfig;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.common.timer.BaseTimerTask;
import com.xiongz.wanjava.common.timer.ITimerListener;
import com.xiongz.wanjava.ui.me.adapter.SelectNetAdapter;
import com.xiongz.wanjava.ui.me.entity.NetRouteEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;

import static com.xiongz.wanjava.common.constant.Const.NET_PING;
import static com.xiongz.wanjava.common.constant.Const.NET_PING_DEV;
import static com.xiongz.wanjava.common.constant.Const.NET_ROUTES;
import static com.xiongz.wanjava.common.constant.Const.NET_ROUTES_DEV;
import static com.xiongz.wanjava.common.constant.Const.NET_ROUTE_INDEX;

/**
 * 服务线路选择
 *
 * @author xiongz
 * @date 2018/9/7
 */
public class SelectNetActivity extends JzActivity {

    @BindView(R.id.rv_select_net)
    RecyclerView rvSelectNet;

    // 适配器
    private SelectNetAdapter mAdapter;
    // 线路列表
    private List<NetRouteEntity> mNetRoutes = new ArrayList<>();
    // 计时器
    private Timer mTimer;
    // 默认网络线路下标
    private int mCurNetIndex = 0;

    @Override
    protected Object getContentView() {
        return R.layout.activity_select_net;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(this, "服务线路选择");

        mCurNetIndex = SPUtils.getInstance().getInt(NET_ROUTE_INDEX, mCurNetIndex);
        // 初始化运营商线路
        String[] netRoutes;
        if (BuildConfig.DEBUG) netRoutes = NET_ROUTES_DEV;
        else netRoutes = NET_ROUTES;
        for (int i = 0; i < netRoutes.length; i++) {
            NetRouteEntity netRouteEntity = new NetRouteEntity();
            netRouteEntity.setNetRoute(netRoutes[i]);
            if (i == mCurNetIndex) netRouteEntity.setSelected(true);
            mNetRoutes.add(netRouteEntity);
        }
        // 初始化列表
        initRecyclerView();
        // 初始化定时器
        initTimer();
    }

    // 定时器监听
    private ITimerListener mTimerListener = new ITimerListener() {
        @Override
        public void onTimer() {
            // 设置网络延迟
            setNetRouteDelay();
        }
    };

    /**
     * 初始化定时器
     * 隔10秒去ping服务器
     */
    private void initTimer() {
        mTimer = new Timer();
        BaseTimerTask task = new BaseTimerTask(mTimerListener);
        mTimer.schedule(task, 0, 10000);
    }

    /**
     * handler
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int pos = msg.what;
            mAdapter.notifyItemChanged(pos);
        }
    };

    /**
     * 设置网络延迟
     */
    private void setNetRouteDelay() {
        for (int i = 0; i < mNetRoutes.size(); i++) {
            NetRouteEntity netRouteEntity = mNetRoutes.get(i);
            getNetDelay(i, netRouteEntity);
        }
    }

    /**
     * 获取网络延迟
     */
    private void getNetDelay(final int i, final NetRouteEntity netRouteEntity) {
        new Thread() {
            @Override
            public void run() {
                try {
                    ShellUtils.CommandResult result;
                    if (BuildConfig.DEBUG) {
                        result = ShellUtils.execCmd(String.format("ping -c 1 %s", NET_PING_DEV[i]), false);
                    } else {
                        result = ShellUtils.execCmd(String.format("ping -c 1 %s", NET_PING[i]), false);
                    }
                    String successMsg = result.successMsg;
                    if (TextUtils.isEmpty(successMsg)) {
                        netRouteEntity.setNetDelay(-1f);
                    } else {
                        int prefix = TextUtils.indexOf(successMsg, "time") + 5;
                        int suffix = TextUtils.indexOf(successMsg, "ms");
                        String time = TextUtils.substring(successMsg, prefix, suffix).trim();
                        netRouteEntity.setNetDelay(Float.parseFloat(time));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 更新视图
                Message message = new Message();
                message.what = i;
                mHandler.sendMessage(message);
            }
        }.start();
    }

    /**
     * 初始化网络连接列表
     */
    private void initRecyclerView() {
        final WrapContentLinearLayoutManager manager = new WrapContentLinearLayoutManager(this);
        rvSelectNet.setLayoutManager(manager);
        mAdapter = new SelectNetAdapter(this, R.layout.recycler_item_net_route, mNetRoutes);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                if (OperateUtil.isFastClick()) return;
                if (mCurNetIndex != position) {
                    NetRouteEntity entity = mNetRoutes.get(position);
                    entity.setSelected(true);
                    mAdapter.notifyItemChanged(position);
                    NetRouteEntity preEntity = mNetRoutes.get(mCurNetIndex);
                    preEntity.setSelected(false);
                    mAdapter.notifyItemChanged(mCurNetIndex);
                    mCurNetIndex = position;

                    // 存储
                    SPUtils.getInstance().put(NET_ROUTE_INDEX, mCurNetIndex);
                }
            }
        });
        rvSelectNet.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        if (mTimerListener != null) mTimerListener = null;
        if (mTimer != null) mTimer = null;
        super.onDestroy();
    }

}