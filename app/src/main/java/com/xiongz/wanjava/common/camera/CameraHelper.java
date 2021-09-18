package com.xiongz.wanjava.common.camera;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TakePhotoOptions;
import com.xiongz.android.core.ui.loader.XzLoader;
import com.xiongz.wanjava.common.constant.FileConst;

import java.io.File;

/**
 * 相机帮助类
 *
 * @author xiongz
 * @date 2018/9/17
 */
public class CameraHelper {

    private View rootView;

    public static CameraHelper of(View rootView) {
        return new CameraHelper(rootView);
    }

    private CameraHelper(View rootView) {
        this.rootView = rootView;
    }

    /**
     * 选择图片
     *
     * @param takePhoto
     */
    public void selectPhoto(TakePhoto takePhoto, int limit, Context context) {
        // configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        XzLoader.showLoading(context);
        if (limit > 1) {
            takePhoto.onPickMultiple(limit);
            return;
        }
        takePhoto.onPickFromGallery();
    }

    /**
     * 拍照
     */
    public void takePic(TakePhoto takePhoto, boolean isNeedCompress, Context context) {
        File file = new File(FileConst.APP_PATH + "/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);

        if (isNeedCompress) {
            configCompress(takePhoto, context);
        } else {
            XzLoader.showLoading(context);
        }
        configTakePhotoOption(takePhoto);

        takePhoto.onPickFromCapture(imageUri);
    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(false);
        builder.setCorrectImage(false);
        takePhoto.setTakePhotoOptions(builder.create());
    }

    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 102400;
        int width = 800;
        int height = 800;
        boolean showProgressBar = false; // 是否显示压缩加载框
        boolean enableRawFile = true; // 压缩后是否保持原图

        // 自带压缩
//        CompressConfig config = new CompressConfig.Builder().setMaxSize(maxSize)
//                .setMaxPixel(width >= height ? width : height)
//                .enableReserveRaw(enableRawFile)
//                .create();
        // luban压缩
        LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
        CompressConfig config = CompressConfig.ofLuban(option);
        config.enableReserveRaw(enableRawFile);

        // XzLoader.showLoading(Xz.getApplicationContext());
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    private void configCompress(TakePhoto takePhoto, Context context) {
        int maxSize = 102400;
        int width = 800;
        int height = 800;
        boolean showProgressBar = false; // 是否显示压缩加载框
        boolean enableRawFile = true; // 压缩后是否保持原图

        // 自带压缩
//        CompressConfig config = new CompressConfig.Builder().setMaxSize(maxSize)
//                .setMaxPixel(width >= height ? width : height)
//                .enableReserveRaw(enableRawFile)
//                .create();

        // luban压缩
        LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
        CompressConfig config = CompressConfig.ofLuban(option);
        config.enableReserveRaw(enableRawFile);

        XzLoader.showLoading(context);
        takePhoto.onEnableCompress(config, showProgressBar);
    }
}
