package com.mvvm.demo.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DataConverter {

    @TypeConverter
    public String fromIntList(List<Integer> countryLang) {
        if (countryLang == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        String json = gson.toJson(countryLang, type);
        return json;
    }

    @TypeConverter
    public List<Integer> toIntList(String intlist) {
        if (intlist == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> countryLangList = gson.fromJson(intlist, type);
        return countryLangList;
    }
}
