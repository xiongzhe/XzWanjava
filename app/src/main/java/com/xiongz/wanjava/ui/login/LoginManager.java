package com.xiongz.wanjava.ui.login;

import com.blankj.utilcode.util.ActivityUtils;
import com.xiongz.wanjava.db.ProfileManager;
import com.xiongz.wanjava.db.UserProfile;

/**
 * 登录管理类
 *
 * @author xiongz
 * @date 2021/9/22
 */
public class LoginManager {

    /**
     * 判断是否登录
     */
    public static void login(LoginCallback callback) {
        UserProfile userProfile = ProfileManager.getCurUserProfile();
        if (userProfile != null) {
            callback.doSomething();
        } else {
            ActivityUtils.startActivity(LoginActivity.class);
        }
    }
}
