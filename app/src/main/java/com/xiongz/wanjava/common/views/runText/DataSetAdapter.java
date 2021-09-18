package com.xiongz.wanjava.common.views.runText;


import java.util.ArrayList;
import java.util.List;

/**
 * 文字轮播
 * @author xiongz
 * @date 2018/9/8
 */
public abstract  class DataSetAdapter<T> {
    List<T> data = new ArrayList<>();

    public DataSetAdapter() {
    }

    public DataSetAdapter(List<T> data) {
        this.data = data;
    }

    /**
     * @param index 当前角标
     * @return 待显示的字符串
     */
    final public String getText(int index) {
        return text(data.get(index));
    }

    protected abstract String text(T t);

    public int getItemCount() {
        return null == data || data.isEmpty() ? 0 : data.size();
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isEmpty() {
        return null == data || data.isEmpty();
    }
}
