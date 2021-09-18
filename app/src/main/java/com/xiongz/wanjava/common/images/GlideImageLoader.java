package com.xiongz.wanjava.common.images;

import android.content.Context;
import android.widget.ImageView;

import com.xiongz.android.core.ui.image.GlideUtil;
import com.xiongz.wanjava.common.constant.GlideOptions;
import com.youth.banner.loader.ImageLoader;

/**
 * @author xiongz
 * @date 2019/2/25
 */
public class GlideImageLoader extends ImageLoader {

    private ImageView.ScaleType mScaleType;

    public GlideImageLoader(ImageView.ScaleType type) {
        mScaleType = type;
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        imageView.setScaleType(mScaleType);
        GlideUtil.withOption(context, path, imageView, GlideOptions.getInstance().getNoCacheOption());
    }
}