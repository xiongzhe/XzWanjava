package com.xiongz.wanjava.common.images;

import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.xiongz.android.core.ui.image.GlideUtil;
import com.xiongz.wanjava.common.constant.GlideOptions;

import java.util.List;

/**
 * 预览图片适配器
 *
 * @author xiongz
 * @date 2018/9/18
 */
public class PreviewImgAdapter extends PagerAdapter {

    private List<String> mImages;
    private DialogFragment mDialog;

    public PreviewImgAdapter(List<String> images, DialogFragment dialog) {
        this.mImages = images;
        this.mDialog = dialog;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(mDialog.getActivity());
        GlideUtil.withOption(mDialog.getActivity(), mImages.get(position),
                photoView, GlideOptions.getInstance().getDefaultOption());
        container.addView(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        return photoView;
    }

    @Override
    public int getCount() {
        if (mImages == null) {
            return 0;
        } else {
            return mImages.size();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
