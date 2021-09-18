package com.xiongz.wanjava.common.constant;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.xiongz.wanjava.R;

/**
 * Glide默认配置项
 * Created by xiongz on 2018/1/4.
 */
public class GlideOptions {

    private static final class Holder {
        private static final GlideOptions INSTANCE = new GlideOptions();
    }

    public static GlideOptions getInstance() {
        return GlideOptions.Holder.INSTANCE;
    }

    /**
     * 获取默认的图片
     *
     * @return
     */
    public RequestOptions getDefaultOption() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_img_default)
                .error(R.drawable.ic_img_default);
        return options;
    }

    /**
     * 获取默认的图片
     *
     * @return
     */
    public RequestOptions getDefaultOption(int resId) {
        RequestOptions options = new RequestOptions()
                .placeholder(resId)
                .error(resId);
        return options;
    }

    /**
     * 获取默认头像的图片
     *
     * @return
     */
    public RequestOptions getDefaultAvatarOption() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_avatar_default)
                .error(R.drawable.ic_avatar_default);
        return options;
    }

    /**
     * 获取默认营业执照的图片
     *
     * @return
     */
    public RequestOptions getDefaultBusLicOption() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_bus_license)
                .error(R.drawable.ic_bus_license);
        return options;
    }

    /**
     * 不要缓存
     *
     * @return
     */
    public RequestOptions getNoCacheOption() {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        return options;
    }

    /**
     * 获取江中默认的图片
     *
     * @return
     */
    public RequestOptions getJzDefaultOption() {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.bg_default)
                .error(R.drawable.bg_default);
        return options;
    }
}
