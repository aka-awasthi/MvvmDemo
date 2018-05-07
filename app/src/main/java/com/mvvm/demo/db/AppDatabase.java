package com.mvvm.demo.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.mvvm.demo.db.category.CategoryDao;
import com.mvvm.demo.db.category.CategoryEntity;
import com.mvvm.demo.db.childcat.ChildCatDao;
import com.mvvm.demo.db.childcat.ChildCatEntity;
import com.mvvm.demo.db.product.ProductDao;
import com.mvvm.demo.db.product.ProductEntity;
import com.mvvm.demo.db.ranking.RankingDao;
import com.mvvm.demo.db.ranking.RankingEntity;

import com.mvvm.demo.db.variants.VariantDao;
import com.mvvm.demo.db.variants.VariantEntity;

@Database(entities = {CategoryEntity.class, ChildCatEntity.class, VariantEntity.class, ProductEntity.class, RankingEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
  private static AppDatabase INSTANCE;
  public static AppDatabase getDatabase(Context context) {
    if (INSTANCE == null) {
      INSTANCE =
          Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app-db").fallbackToDestructiveMigration()
              .build();
    }
    return INSTANCE;
  }

  static final Migration MIGRATION_1_2 = new Migration(1, 2) {
    @Override
    public void migrate(SupportSQLiteDatabase database) {
      // Since we didn't alter the table, there's nothing else to do here.
    }
  };

    public abstract CategoryDao getCategoryDao();
    public abstract ProductDao getProductDao();
    public abstract VariantDao getVariantDao();
    public abstract ChildCatDao getChildCatDao();
    public abstract RankingDao getRankingDao();




}
