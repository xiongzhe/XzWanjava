package com.xiongz.wanjava.application;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * App入口
 * Created by xiongz on 2017/12/13
 */
public class WanApp extends TinkerApplication {

    public WanApp() {
        super(ShareConstants.TINKER_ENABLE_ALL,
                "com.xiongz.wanjava.application.WanAppLike",
                "com.tencent.tinker.loader.TinkerLoader",
                false);
    }
}
