package com.mvvm.demo.db.product;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

public class ProductEntity {

    @PrimaryKey
    int id;

    String name;

    String date_added;

}
