package com.mvvm.demo.db.category;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.mvvm.demo.db.AppDatabase;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private final LiveData<List<CategoryEntity>> list;
    private AppDatabase appDatabase;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        list = appDatabase.getCategoryDao().getAll();
    }
    public LiveData<List<CategoryEntity>> getList() {
        return list;
    }

    public void insertAll(CategoryEntity[] entities){
        new InsertAsyncTask(appDatabase).execute(entities);
    }

    private static class InsertAsyncTask extends AsyncTask<CategoryEntity[], Void, Void> {

        private AppDatabase db;

        InsertAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override protected Void doInBackground(CategoryEntity[]... categoryEntities) {
            db.getCategoryDao().insertAll(categoryEntities[0]);
            return null;
        }
    }
}
