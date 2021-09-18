package com.xiongz.wanjava.common.utils;

import android.graphics.Color;

import com.xiongz.wanjava.R;

import java.util.Random;

/**
 * 颜色工具类
 */
public class ColorUtil {

    public static final int[] COLORS_RES = {R.color.colors_00BAAD, R.color.colors_53AFF6,
            R.color.colors_A5D63F, R.color.colors_8C7BB1, R.color.colors_EF9714, R.color.colors_FF5733};

    // 前一种颜色
    private static int preInt;

    /**
     * 随机获取一种颜色,且相邻两个之间不一样
     *
     * @return
     */
    public static int getRandomColor() {
        int randomInt = new Random().nextInt(COLORS_RES.length);
        if (randomInt == preInt) {
            return getRandomColor();
        } else {
            preInt = randomInt;
            return COLORS_RES[randomInt];
        }
    }

    /**
     * 随机获取一种颜色(不要太靠近白色)
     *
     * @return
     */
    public static int getRandomColorWithOutWhite() {
        Random random = new Random();
        int red = random.nextInt(190);
        int green = random.nextInt(190);
        int blue = random.nextInt(190);
        return Color.rgb(red, green, blue);
    }
}
