package com.mvvm.demo.db.product;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ProductEntity... productEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ProductEntity productEntity);

    @Query("select * from tbl_product  where category_id = :id")
    LiveData<List<ProductEntity>> getAll(int id);

    @Query("select * from tbl_product")
    LiveData<List<ProductEntity>> getAll();

//    @Query("update tbl_product set order_count = :count where id = :productid")
//    void updateOrdercount(int productid,int count);
//
//    @Query("update tbl_product set shares = :shares where id = :productid")
//    void updateShares(int productid,int shares);
//
//    @Query("update tbl_product set shares = :shares where id = :productid")
//    void updateShares(int productid,int shares);

    @Query("select * from tbl_product where id in (select productid from tbl_ranking where ranking = :ranking)")
    LiveData<List<ProductEntity>> getproductEntity(String ranking);
}
