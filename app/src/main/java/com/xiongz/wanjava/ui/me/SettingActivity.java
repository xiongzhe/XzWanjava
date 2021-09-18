package com.xiongz.wanjava.ui.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.xiongz.android.core.activities.JzActivity;
import com.xiongz.android.core.net.rx.RetrofitManager;
import com.xiongz.android.core.util.operate.OperateUtil;
import com.xiongz.android.core.util.storage.CacheUtil;
import com.xiongz.wanjava.BuildConfig;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.common.constant.FileConst;
import com.xiongz.wanjava.common.net.ConstUrl;
import com.xiongz.wanjava.common.web.CommonWebActivity;
import com.xiongz.wanjava.db.ProfileManager;
import com.xiongz.wanjava.ui.login.LoginActivity;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xiongz.wanjava.common.constant.Const.NET_ROUTES;
import static com.xiongz.wanjava.common.constant.Const.NET_ROUTES_DEV;
import static com.xiongz.wanjava.common.constant.Const.NET_ROUTE_INDEX;

/**
 * 系统设置
 *
 * @author xiongz
 * @date 2018/9/7
 */
public class SettingActivity extends JzActivity {

    @BindView(R.id.tv_setting_cache)
    TextView tvCache;
    @BindView(R.id.ll_setting_cache)
    LinearLayout llCache;
    @BindView(R.id.tv_setting_route_select)
    TextView tvNetRoute;
    @BindView(R.id.ll_setting_about_us)
    LinearLayout llAboutUs;
    @BindView(R.id.ll_setting_statement)
    LinearLayout llStatement;
    @BindView(R.id.tv_setting_version)
    TextView tvVersion;
    @BindView(R.id.ll_setting_version)
    LinearLayout llUpdate;
    @BindView(R.id.btn_setting_login_out)
    Button btnLoginOut;

    @Override
    protected Object getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(this, "系统设置");

        tvCache.setText(CacheUtil.getTotalCacheSize(this));
        tvVersion.setText(TextUtils.concat("v", AppUtils.getAppVersionName()));
    }

    @Override
    public void onResume() {
        super.onResume();
        int curNetIndex = SPUtils.getInstance().getInt(NET_ROUTE_INDEX, 0);
        if (BuildConfig.DEBUG) {
            tvNetRoute.setText(NET_ROUTES_DEV[curNetIndex].trim());
        } else {
            tvNetRoute.setText(NET_ROUTES[curNetIndex].trim());
        }
    }

    @OnClick({R.id.ll_setting_route_select, R.id.ll_setting_cache, R.id.ll_setting_version,
            R.id.btn_setting_login_out, R.id.ll_setting_about_us, R.id.ll_setting_statement,
            R.id.ll_law, R.id.ll_protocol, R.id.ll_privacy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 服务线路选择
            case R.id.ll_setting_route_select:
                if (OperateUtil.isFastClick()) return;
                ActivityUtils.startActivity(SelectNetActivity.class);
                break;
            // 清除缓存
            case R.id.ll_setting_cache:
                CacheUtil.clearAllCache(SettingActivity.this);
                break;
            // 版本更新
            case R.id.ll_setting_version:
                Beta.checkUpgrade();
                break;
            // 关于我们
            case R.id.ll_setting_about_us:
                if (OperateUtil.isFastClick()) return;
                Bundle aboutUsBundle = new Bundle();
                aboutUsBundle.putString(CommonWebActivity.WEB_TITLE, "关于我们");
                aboutUsBundle.putString(CommonWebActivity.WEB_URL, ConstUrl.ABOUT_US);
                ActivityUtils.startActivity(aboutUsBundle, CommonWebActivity.class);
                break;
            // 免责声明
            case R.id.ll_setting_statement:
                if (OperateUtil.isFastClick()) return;
                Bundle statementBundle = new Bundle();
                statementBundle.putString(CommonWebActivity.WEB_TITLE, "免责声明");
                statementBundle.putString(CommonWebActivity.WEB_URL, ConstUrl.STATEMENT);
                ActivityUtils.startActivity(statementBundle, CommonWebActivity.class);
                break;
            // 退出登录
            case R.id.btn_setting_login_out:
                logout();
                break;
            // 法律声明
            case R.id.ll_law:
                if (OperateUtil.isFastClick()) return;
                Bundle bundle1 = new Bundle();
                bundle1.putString(CommonWebActivity.WEB_TITLE, "法律声明");
                bundle1.putString(CommonWebActivity.WEB_URL, ConstUrl.LAW);
                ActivityUtils.startActivity(bundle1, CommonWebActivity.class);
                break;
            // 用户协议
            case R.id.ll_protocol:
                if (OperateUtil.isFastClick()) return;
                Bundle bundle2 = new Bundle();
                bundle2.putString(CommonWebActivity.WEB_TITLE, "用户协议");
                bundle2.putString(CommonWebActivity.WEB_URL, ConstUrl.PROTOCOL);
                ActivityUtils.startActivity(bundle2, CommonWebActivity.class);
                break;
            // 隐私政策
            case R.id.ll_privacy:
                if (OperateUtil.isFastClick()) return;
                Bundle bundle3 = new Bundle();
                bundle3.putString(CommonWebActivity.WEB_TITLE, "隐私政策");
                bundle3.putString(CommonWebActivity.WEB_URL, ConstUrl.PRIVACY);
                ActivityUtils.startActivity(bundle3, CommonWebActivity.class);
                break;
        }
    }

    /**
     * 退出
     */
    private void logout() {
        // 删除本地缓存图片
        FileConst.deleteFile();
        // 删除本地数据库用户数据
        ProfileManager.clearUserProfile();
        // 清除cookie
        RetrofitManager.getInstance().clearPersistentCookieJar();
        // 退出
        ActivityUtils.startActivity(LoginActivity.class);
    }
}