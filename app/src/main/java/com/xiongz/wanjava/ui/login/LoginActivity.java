package com.xiongz.wanjava.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.joanzapata.iconify.widget.IconTextView;
import com.xiongz.android.core.activities.JzActivity;
import com.xiongz.android.core.net.rx.RxNetClient;
import com.xiongz.android.core.ui.loader.XzLoader;
import com.xiongz.android.core.util.file.FileUtil;
import com.xiongz.android.core.util.json.FastjsonUtil;
import com.xiongz.android.core.util.operate.OperateUtil;
import com.xiongz.wanjava.BuildConfig;
import com.xiongz.wanjava.R;
import com.xiongz.wanjava.common.data.LoginEntity;
import com.xiongz.wanjava.common.event.LoginEvent;
import com.xiongz.wanjava.common.net.ConstUrl;
import com.xiongz.wanjava.common.net.NetManager;
import com.xiongz.wanjava.common.net.ObserverProxy;
import com.xiongz.wanjava.common.net.WanNetEntity;
import com.xiongz.wanjava.common.utils.StringUtil;
import com.xiongz.wanjava.common.views.ClearEditText;
import com.xiongz.wanjava.db.ProfileManager;
import com.xiongz.wanjava.db.UserProfile;
import com.xiongz.wanjava.ui.me.SelectNetActivity;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.xiongz.wanjava.common.constant.Const.NET_ROUTES;
import static com.xiongz.wanjava.common.constant.Const.NET_ROUTES_DEV;
import static com.xiongz.wanjava.common.constant.Const.NET_ROUTE_INDEX;

import org.greenrobot.eventbus.EventBus;

/**
 * ?????????
 *
 * @author xiongz
 * @date 2021/9/18
 */
public class LoginActivity extends JzActivity {

    @BindView(R.id.tv_privacy)
    TextView tvPrivacy;
    @BindView(R.id.cet_login_username)
    ClearEditText etUname;
    @BindView(R.id.cet_login_password)
    ClearEditText etPwd;
    @BindView(R.id.btn_login_login)
    Button btnLogin;
    @BindView(R.id.tv_login_route_select)
    TextView tvNetRoute;
    @BindView(R.id.tv_login_pwd_switch)
    IconTextView tvPwdSwitch;

    //??????????????????
    private boolean isPwdVisible = false;

    @Override
    protected Object getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(this, "??????");
        String string = getResources().getString(R.string.privacy7);
        tvPrivacy.setText(StringUtil.getClickableSpan(this, string));
        tvPrivacy.setMovementMethod(LinkMovementMethod.getInstance());
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

    @OnClick({R.id.btn_login_login, R.id.tv_login_pwd_switch, R.id.ll_login_route_select, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // ????????????
            case R.id.ll_login_route_select:
                if (OperateUtil.isFastClick()) return;
                ActivityUtils.startActivity(SelectNetActivity.class);
                break;
            // ??????
            case R.id.btn_login_login:
                String userName = etUname.getText().toString().trim();
                String password = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    ToastUtils.showShort("???????????????");
                } else if (TextUtils.isEmpty(password)) {
                    ToastUtils.showShort("???????????????");
                } else {
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("username", userName);
                    params.put("password", password);
                    requestLogin(params);
                }
                break;
            // ????????????
            case R.id.tv_login_pwd_switch:
                FileUtil.setIconFont("iconfont.ttf", tvPwdSwitch);
                if (isPwdVisible) {
                    tvPwdSwitch.setText("{ic-pwd-open}");
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPwdVisible = false;
                } else {
                    tvPwdSwitch.setText("{ic-pwd-close}");
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isPwdVisible = true;
                }
                break;
            // ?????????
            case R.id.tv_register:

                break;
        }
    }

    /**
     * ??????
     */
    public void requestLogin(final HashMap<String, Object> params) {
        String url = NetManager.getInstance().getUrl(ConstUrl.APP_LOGIN);
        RxNetClient.builder()
                .url(url)
                .params(params)
                .build()
                .post()
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
                        if (e instanceof SocketTimeoutException) {
                            ToastUtils.showLong("????????????,???????????????!" + e.getMessage());
                        } else if (e instanceof ConnectException) {
                            ToastUtils.showLong("??????????????????!");
                        } else {
                            ToastUtils.showLong("????????????!");
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
     * ??????????????????
     */
    private void parseData(String result) {
        WanNetEntity netEntity = FastjsonUtil.parseObject(result, WanNetEntity.class);
        JSONObject data = (JSONObject) netEntity.getData();
        String list = data.toJSONString();
        LoginEntity loginEntity = FastjsonUtil.parseObject(list, LoginEntity.class);
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(loginEntity.getId());
        userProfile.setToken(loginEntity.getToken());
        userProfile.setEmail(loginEntity.getEmail());
        userProfile.setNickname(loginEntity.getNickname());
        userProfile.setPassword(loginEntity.getPassword());
        userProfile.setAdmin(loginEntity.isAdmin());
        userProfile.setIcon(loginEntity.getIcon());
        userProfile.setPublicName(loginEntity.getPublicName());
        ProfileManager.insertUserProfile(userProfile);
        EventBus.getDefault().post(new LoginEvent());
        onBackPressed();
    }
}
