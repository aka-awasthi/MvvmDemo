package com.mvvm.demo.db.category;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.mvvm.demo.db.DataConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "tbl_category")
public class CategoryEntity {

    @PrimaryKey
    int id;

    String name;

    @TypeConverters({DataConverter.class})
    List<Integer> child_categories;
}
