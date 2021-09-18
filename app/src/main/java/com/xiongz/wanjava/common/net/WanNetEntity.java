package com.xiongz.wanjava.common.net;

/**
 * 网络接口返回结构
 *
 * @author xiongz
 * @date 2021/9/1
 */
public class WanNetEntity<T> {

    private T data;
    private Integer errorCode;
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
