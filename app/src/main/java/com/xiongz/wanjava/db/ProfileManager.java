package com.xiongz.wanjava.db;

import com.xiongz.android.core.util.string.Base64Util;

import java.util.List;

/**
 * 数据库信息管理类
 *
 * @author xiongz
 * @date 2018/9/4
 */
public class ProfileManager {

    /********** 用户信息 **********/

    /**
     * 插入用户信息
     *
     * @param userProfile 用户信息
     */
    public static void insertUserProfile(UserProfile userProfile) {
        UserProfileDao dao = DbManager.getInstance().getUserProfileDao();
        dao.detachAll(); // 清除缓存
        dao.deleteAll();
        dao.insert(userProfile);
    }

    /**
     * 插入用户信息
     *
     * @param userProfile 用户信息
     * @param pwd         密码
     */
    public static void insertUserProfile(UserProfile userProfile,String userName, String pwd) {
        UserProfileDao dao = DbManager.getInstance().getUserProfileDao();
        dao.deleteAll();
        userProfile.setPassword(Base64Util.getEncodeStr(pwd));
        userProfile.setUsername(userName);
        dao.insert(userProfile);
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    public static UserProfile getCurUserProfile() {
        UserProfile userProfile = null;
        UserProfileDao dao = DbManager.getInstance().getUserProfileDao();
        dao.detachAll(); // 清除缓存
        List<UserProfile> datas = dao.loadAll();
        if (datas != null && datas.size() > 0) {
            userProfile = datas.get(0);
        }
        return userProfile;
    }

    /**
     * 删除用户信息
     */
    public static void clearUserProfile() {
        UserProfileDao dao = DbManager.getInstance().getUserProfileDao();
        dao.detachAll(); // 清除缓存
        dao.deleteAll();
    }

    /**
     * 修改用户密码
     *
     * @return
     */
    public static void updateUserPwd(String pwd) {
        UserProfileDao dao = DbManager.getInstance().getUserProfileDao();
        dao.detachAll(); // 清除缓存
        List<UserProfile> datas = dao.loadAll();
        if (datas != null && datas.size() > 0) {
            UserProfile userProfile = datas.get(0);
            userProfile.setPassword(Base64Util.getEncodeStr(pwd));
            dao.update(userProfile);
        }
    }
}
