package com.mvvm.demo.db.childcat;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mvvm.demo.db.variants.VariantEntity;

import java.util.List;

@Dao
public interface ChildCatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ChildCatEntity... childCatEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ChildCatEntity childCatEntity);

    @Query("select * from tbl_childcategory")
    LiveData<List<ChildCatEntity>> getAll();

    @Query("select * from tbl_childcategory where childcatid = 1")
    LiveData<ChildCatEntity> getChildCategory();
}
