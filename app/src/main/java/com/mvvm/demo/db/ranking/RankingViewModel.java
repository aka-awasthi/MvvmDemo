package com.mvvm.demo.db.ranking;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.mvvm.demo.db.AppDatabase;
import com.mvvm.demo.db.product.ProductEntity;
import com.mvvm.demo.db.product.ProductViewModel;

import java.util.List;

public class RankingViewModel extends AndroidViewModel{
    private final LiveData<List<RankingEntity>> list;
    private AppDatabase appDatabase;
    public RankingViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        list = appDatabase.getRankingDao().getAll();
    }

    public LiveData<List<RankingEntity>> getList() {
        return list;
    }

    public void insertAll(RankingEntity[] entities){
        new InsertAsyncTask(appDatabase).execute(entities);
    }

    private static class InsertAsyncTask extends AsyncTask<RankingEntity[], Void, Void> {

        private AppDatabase db;
        InsertAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override protected Void doInBackground(RankingEntity[]... rankingEntities) {
            db.getRankingDao().insertAll(rankingEntities[0]);
            return null;
        }
    }
}
