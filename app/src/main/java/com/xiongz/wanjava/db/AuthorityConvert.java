package com.xiongz.wanjava.db;

import com.alibaba.fastjson.JSON;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

/**
 * @author winkey
 * @date 2019/9/17
 * @describe 权限数据库转换类
 */
public class AuthorityConvert implements PropertyConverter<List<String>,String> {
    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        return JSON.parseArray(databaseValue , String.class);
    }

    @Override
    public String convertToDatabaseValue(List<String> entityProperty) {
        return JSON.toJSONString(entityProperty);
    }
}
