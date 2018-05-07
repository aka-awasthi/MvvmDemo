package com.mvvm.demo.db.variants;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mvvm.demo.db.category.CategoryEntity;

import java.util.List;

@Dao
public interface VariantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(VariantEntity... variantEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(VariantEntity variantEntity);

    @Query("select * from tbl_variant where product_id = :id")
    LiveData<List<VariantEntity>> getAll(int id);

}
