package com.mvvm.demo.db.category;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CategoryEntity... categoryEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CategoryEntity categoryEntity);

    @Query("select id,name from tbl_category where id not in (select childcatid from tbl_childcategory)")
    LiveData<List<CategoryEntity>> getCategory();

    @Query("select * from tbl_category where id = 1")
    LiveData<CategoryEntity> getCategoryEntity();
}
