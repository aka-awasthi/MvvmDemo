package com.mvvm.demo.db.variants;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.mvvm.demo.db.AppDatabase;
import com.mvvm.demo.db.category.CategoryEntity;
import com.mvvm.demo.db.category.CategoryViewModel;

import java.util.List;

public class VariantViewModel extends AndroidViewModel{

    private AppDatabase appDatabase;
    public VariantViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }
    public LiveData<List<VariantEntity>> getList(int id) {
        return  appDatabase.getVariantDao().getAll(id);
    }

    public void insertAll(VariantEntity[] entities){
        new InsertAsyncTask(appDatabase).execute(entities);
    }

    private static class InsertAsyncTask extends AsyncTask<VariantEntity[], Void, Void> {

        private AppDatabase db;

        InsertAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override protected Void doInBackground(VariantEntity[]... variantEntities) {
            db.getVariantDao().insertAll(variantEntities[0]);
            return null;
        }
    }
}
