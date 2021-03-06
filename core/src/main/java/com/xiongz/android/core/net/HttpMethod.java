package com.xiongz.android.core.net;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 请求类型
 * Created by xiongz on 2017/12/13.
 */
@StringDef({HttpMethod.GET, HttpMethod.POST, HttpMethod.POST_PARAMS,
        HttpMethod.POST_RAW, HttpMethod.PUT, HttpMethod.PUT_RAW,
        HttpMethod.DELETE, HttpMethod.UPLOAD, HttpMethod.POST_MULTIPART})
@Retention(RetentionPolicy.SOURCE)
public @interface HttpMethod {
    String GET = "GET";
    String POST = "POST";
    String POST_PARAMS = "POST_PARAMS";
    String POST_RAW = "POST_RAW";
    String PUT = "PUT";
    String PUT_RAW = "PUT_RAW";
    String DELETE = "DELETE";
    String UPLOAD = "UPLOAD";
    String POST_MULTIPART = "POST_MULTIPART";
}
