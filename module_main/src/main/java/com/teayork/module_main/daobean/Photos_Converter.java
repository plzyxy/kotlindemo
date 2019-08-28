package com.teayork.module_main.daobean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

/**
 * author：pengzhixian on 2019-08-19 16:39
 * e-mail：759560522@qq.com
 */
public class Photos_Converter implements PropertyConverter<List<String>, String> {
    @Override
    public List<String> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        // 先得获得这个，然后再typeToken.getType()，否则会异常
        TypeToken<List<String>> typeToken = new TypeToken<List<String>>(){};
        return new Gson().fromJson(databaseValue, typeToken.getType());
    }

    @Override
    public String convertToDatabaseValue(List<String> arrays) {
        if (arrays == null||arrays.size()==0) {
            return null;
        } else {
            String sb = new Gson().toJson(arrays);
            return sb;

        }
    }
}
