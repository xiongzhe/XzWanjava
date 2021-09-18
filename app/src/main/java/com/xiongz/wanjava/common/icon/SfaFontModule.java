package com.xiongz.wanjava.common.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * 营销e网通自定义字体图标库module
 * Created by xiongz on 2017/12/10
 */
public class SfaFontModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return SfaIcons.values();
    }
}
