package com.mvvm.demo.db.ranking;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.mvvm.demo.db.product.ProductEntity;

import java.util.List;

@Dao
public interface RankingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(RankingEntity... rankingEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(RankingEntity rankingEntity);

    @Query("select * from tbl_ranking")
    LiveData<List<RankingEntity>> getAll();
}
