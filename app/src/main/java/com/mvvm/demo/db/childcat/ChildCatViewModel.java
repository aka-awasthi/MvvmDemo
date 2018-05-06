package com.mvvm.demo.db.childcat;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.mvvm.demo.db.AppDatabase;
import com.mvvm.demo.db.category.CategoryEntity;
import com.mvvm.demo.db.category.CategoryViewModel;

import java.util.List;

public class ChildCatViewModel extends AndroidViewModel{

    private final LiveData<List<ChildCatEntity>> list;
    private AppDatabase appDatabase;
    public ChildCatViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        list = appDatabase.getChildCatDao().getAll();
    }
    public LiveData<List<ChildCatEntity>> getList() {
        return list;
    }

    public void insertAll(ChildCatEntity[] entities){
        new InsertAsyncTask(appDatabase).execute(entities);
    }

    private static class InsertAsyncTask extends AsyncTask<ChildCatEntity[], Void, Void> {

        private AppDatabase db;

        InsertAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override protected Void doInBackground(ChildCatEntity[]... childCatEntities) {
            db.getChildCatDao().insertAll(childCatEntities[0]);
            return null;
        }
    }
}
