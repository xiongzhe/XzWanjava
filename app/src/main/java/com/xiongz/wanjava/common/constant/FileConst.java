package com.xiongz.wanjava.common.constant;

import com.blankj.utilcode.util.FileUtils;
import com.xiongz.android.core.util.file.FileUtil;

/**
 * 文件常量
 *
 * @author xiongz
 * @date 2018/10/5
 */
public class FileConst {

    // app文件相对路径
    public final static String APP_DIR = "wan";
    // app文件路径
    public final static String APP_PATH = FileUtil.SDCARD_DIR + "/" + APP_DIR;

    /**
     * 删除文件
     */
    public static void deleteFile() {
        new Thread() {
            @Override
            public void run() {
                // 存储图片路径
                FileUtils.deleteAllInDir(FileConst.APP_PATH);
            }
        }.start();
    }
}
