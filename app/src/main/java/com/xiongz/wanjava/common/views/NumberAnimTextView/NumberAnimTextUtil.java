package com.xiongz.wanjava.common.views.NumberAnimTextView;

/**
 * NumberAnimTextView工具类
 * Created by xiongz on 2018/1/10.
 */
public class NumberAnimTextUtil {

    //默认时长
    private static final int DEFAULT_TIMES = 500;

    /**
     * 设置NumberAnimTextView文本
     *
     * @param textView NumberAnimTextView控件
     * @param text     显示文本
     */
    public static void setNumberAnimText(NumberAnimTextView textView, String text) {
        setNumberAnimText(textView, DEFAULT_TIMES, text);
    }

    /**
     * 设置NumberAnimTextView文本
     *
     * @param textView  NumberAnimTextView控件
     * @param mDuration 动画时长
     * @param text      显示文本
     */
    public static void setNumberAnimText(NumberAnimTextView textView, long mDuration, String text) {
        if (textView != null) {
            textView.setDuration(mDuration);
            textView.setNumberString(text);
        }
    }
}
