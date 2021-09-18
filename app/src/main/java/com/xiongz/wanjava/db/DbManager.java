package com.xiongz.wanjava.db;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * 数据库管理类
 * Created by xiongz on 2017/12/15
 */
public class DbManager {

    private ReleaseOpenHelper mDbHelper = null;
    private DaoSession mDaoSession = null;
    private UserProfileDao mUserProfileDao = null;

    private DbManager() {
    }

    public DbManager init(Context context) {
        initDao(context);
        return this;
    }

    private static final class Holder {
        private static final DbManager INSTANCE = new DbManager();
    }

    public static DbManager getInstance() {
        return Holder.INSTANCE;
    }

    private void initDao(Context context) {
        if (mDbHelper == null) {
            mDbHelper = new ReleaseOpenHelper(context, "wanjava.db");
        }
        final Database db = mDbHelper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mDaoSession.clear();
        mUserProfileDao = mDaoSession.getUserProfileDao();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public final UserProfileDao getUserProfileDao() {
        return mUserProfileDao;
    }
}
