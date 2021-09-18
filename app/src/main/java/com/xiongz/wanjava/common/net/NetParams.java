package com.xiongz.wanjava.common.net;

/**
 * 网络请求公共参数
 *
 * @author xiongz
 * @date 2018/9/27
 */
public class NetParams {

    /*** wan ****/
    /**
     * 新翻页方式
     *
     * @param url
     * @param page
     * @return
     */
    public static String getPageUrl(String url, int page) {
        return url.replace("{page}", String.valueOf(page));
    }

    /**
     * 新翻页方式
     *
     * @param url
     * @param page
     * @return
     */
    public static String getPageUrl(String url, int page, int id) {
        return url.replace("{page}", String.valueOf(page))
                .replace("{id}", String.valueOf(id));
    }
}
