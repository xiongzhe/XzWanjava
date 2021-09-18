package com.xiongz.wanjava.common.net;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.xiongz.wanjava.BuildConfig;
import com.xiongz.wanjava.common.constant.Const;

import static com.xiongz.wanjava.common.constant.Const.NET_ROUTE_INDEX;

/**
 * 网络服务管理器
 * 用户切换不同运营商的网络服务
 *
 * @author xiongz
 * @date 2019/1/31
 */
public class NetManager {

    private static final class Holder {
        private static final NetManager INSTANCE = new NetManager();
    }

    public static NetManager getInstance() {
        return NetManager.Holder.INSTANCE;
    }

    /**
     * 获取请求url
     *
     * @param route 路由
     * @return
     */
    public String getUrl(String route) {
        // 获取当前网络服务(默认电信)
        int curNetIndex = SPUtils.getInstance().getInt(NET_ROUTE_INDEX, 0);
        if (BuildConfig.DEBUG) {
            return "https://" + Const.NET_PING_DEV[curNetIndex] + route;
        } else {
            return "https://" + Const.NET_PING[curNetIndex] + route;
        }
    }


    /**
     * 获取请求url
     *
     * @return
     */
    public String getImageUrl(String path) {
        if(BuildConfig.DEBUG){
            if (!TextUtils.isEmpty(path) && path.contains("CBFCA060C0735750")) {
                return"http://moa.crjz.com:7073/ossApi/" + path;
            } else {
                return "http://moa.crjz.com:7073/jzfApi/" + path;
            }
        }else{
            if (!TextUtils.isEmpty(path) && path.contains("CBFCA060C0735750")) {
                return getUrl(":7073/ossApi/" + path);
            } else {
                return getUrl(":7073/jzfApi/" + path);
            }
        }
    }

    /**
     * 获取请求url
     *
     * @return
     */
    public String getUploadUrl() {
        // 获取当前网络服务(默认电信)
        int curNetIndex = SPUtils.getInstance().getInt(NET_ROUTE_INDEX, 0);
        if (BuildConfig.DEBUG) {
            return "http://moa.crjz.com:7073/gw/b4ae318a/A44EF0E51BC1C913";
        } else {
            return "http://" + Const.NET_PING[curNetIndex] + ":7073/gw/b4ae318a/A44EF0E51BC1C913";
        }
    }
}
