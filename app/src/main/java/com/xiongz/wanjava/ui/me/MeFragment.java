package com.xiongz.wanjava.ui.me;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.xiongz.android.core.fragments.JzFragment;
import com.xiongz.android.core.net.rx.RetrofitManager;
import com.xiongz.android.core.net.rx.RxNetClient;
import com.xiongz.android.core.ui.loader.XzLoader;
import com.xiongz.android.core.ui.refresh.RefreshHandler;
import com.xiongz.android.core.util.json.FastjsonUtil;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.common.constant.FileConst;
import com.xiongz.wanjava.common.event.LoginEvent;
import com.xiongz.wanjava.common.net.ConstUrl;
import com.xiongz.wanjava.common.net.NetManager;
import com.xiongz.wanjava.common.net.ObserverProxy;
import com.xiongz.wanjava.common.net.WanNetEntity;
import com.xiongz.wanjava.common.web.CommonWebActivity;
import com.xiongz.wanjava.db.ProfileManager;
import com.xiongz.wanjava.db.UserProfile;
import com.xiongz.wanjava.ui.login.LoginActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的
 *
 * @author xiongz
 * @date 2021/8/31
 */
public class MeFragment extends JzFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.me_name)
    TextView tvName;
    @BindView(R.id.me_info)
    TextView tvInfo;
    @BindView(R.id.me_integral)
    TextView tvIntegral;
    @BindView(R.id.btn_login_out)
    Button btnLoginOut;

    // 刷新
    private RefreshHandler mRefreshHandler;
    // 登录用户信息
    private UserProfile mUserProfile;

    @Override
    public Object setLayout() {
        return R.layout.fragment_me;
    }

    @Override
    public void onAttach(Context context) {
        mIsRegisterBus = true;
        super.onAttach(context);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mRefreshHandler = new RefreshHandler(swipe, this, R.color.colorPrimary);
        mRefreshHandler.startRefresh();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mUserProfile = ProfileManager.getCurUserProfile();
        if (mUserProfile == null) {
            tvName.setText("请先登录~");
            tvInfo.setText("id：--　排名：--");
            tvIntegral.setText("0");
            btnLoginOut.setVisibility(View.GONE);
        } else {
            if (TextUtils.isEmpty(mUserProfile.getNickname())) {
                tvName.setText(mUserProfile.getUsername());
            } else {
                tvName.setText(mUserProfile.getNickname());
            }
            requestIntegral();
            btnLoginOut.setVisibility(View.VISIBLE);
        }
        mRefreshHandler.stopRefresh();
    }

    @OnClick({R.id.layout_avatar, R.id.layout_integral, R.id.layout_collect, R.id.layout_article,
            R.id.layout_todo, R.id.layout_web, R.id.layout_join, R.id.layout_demo, R.id.layout_setting,
            R.id.btn_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 点击头像区域去登录
            case R.id.layout_avatar:
                if (mUserProfile == null) ActivityUtils.startActivity(LoginActivity.class);
                break;
            // 我的积分
            case R.id.layout_integral:
                ToastUtils.showLong("我的积分");
                break;
            // 我的收藏
            case R.id.layout_collect:
                ToastUtils.showLong("我的收藏");
                break;
            // 我的文章
            case R.id.layout_article:
                ToastUtils.showLong("我的文章");
                break;
            // todoList
            case R.id.layout_todo:
                ToastUtils.showLong("TODO");
                break;
            // 开源网站
            case R.id.layout_web:
                Bundle bundle = new Bundle();
                bundle.putString(WanWebActivity.WEB_TITLE, "玩Android网站");
                bundle.putString(WanWebActivity.WEB_URL, "https://www.wanandroid.com/");
                ActivityUtils.startActivity(bundle, WanWebActivity.class);
                break;
            // 加入我们
            case R.id.layout_join:
                ToastUtils.showLong("加入我们");
                break;
            // 一些示例
            case R.id.layout_demo:
                ToastUtils.showLong("一些示例");
                break;
            // 系统设置
            case R.id.layout_setting:
                ToastUtils.showLong("系统设置");
                break;
            // 退出登录
            case R.id.btn_login_out:
                if (mUserProfile != null) {
                    new AlertDialog.Builder(getProxyActivity())
                            .setMessage("确定退出吗?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    loginOut();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
                }
                break;
        }
    }

    /********** 接收通知 **********/
    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toRefresh(LoginEvent event) {
        onRefresh();
    }

    /**
     * 退出登录
     */
    public void loginOut() {
        // 删除本地缓存图片
        FileConst.deleteFile();
        // 删除本地数据库用户数据
        ProfileManager.clearUserProfile();
        // 清除cookie
        RetrofitManager.getInstance().clearPersistentCookieJar();
        // 刷新
        onRefresh();
    }

    /**
     * 登录
     */
    public void requestIntegral() {
        String url = NetManager.getInstance().getUrl(ConstUrl.INTEGRAL);
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
                        if (e instanceof SocketTimeoutException) {
                            ToastUtils.showLong("网络超时,请稍后再试!" + e.getMessage());
                        } else if (e instanceof ConnectException) {
                            ToastUtils.showLong("网络连接异常!");
                        } else {
                            ToastUtils.showLong("获取信息异常!");
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        XzLoader.dismissDialog();
                    }
                }));
    }

    /**
     * 解析返回数据
     */
    private void parseData(String result) {
        WanNetEntity netEntity = FastjsonUtil.parseObject(result, WanNetEntity.class);
        JSONObject data = (JSONObject) netEntity.getData();
        tvInfo.setText("id：" + data.getInteger("userId") + "　排名：" + data.getString("rank"));
        tvIntegral.setText(String.valueOf(data.getInteger("coinCount")));
    }
}
