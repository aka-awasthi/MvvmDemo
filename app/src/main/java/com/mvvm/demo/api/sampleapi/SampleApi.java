package com.mvvm.demo.api.sampleapi;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvvm.demo.Constants;
import com.mvvm.demo.api.Apis;
import com.mvvm.demo.api.BaseLoader;
import com.mvvm.demo.db.AppDatabase;
import com.mvvm.demo.db.category.CategoryEntity;
import com.mvvm.demo.db.childcat.ChildCatEntity;
import com.mvvm.demo.db.product.ProductEntity;
import com.mvvm.demo.db.ranking.RankingEntity;
import com.mvvm.demo.db.variants.VariantEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SampleApi  implements Callback<ResponseBody>,Constants {
    AppDatabase appDatabase;
    final String TAG = getClass().getName();
    public SampleApi(AppDatabase database){
        this.appDatabase = database;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SAMPLEAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Apis apis = retrofit.create(Apis.class);

        Call<ResponseBody> call = apis.getJson();
        call.enqueue(this);
    }


    @Override
    public void onResponse(final Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
           final List<CategoryEntity> catlist = new ArrayList<>();
           final  List<ProductEntity> prodlist = new ArrayList<>();
            final List<VariantEntity> varentitylist = new ArrayList<>();
            final List<ChildCatEntity> childCatEntities = new ArrayList<>();

//          Log.d(TAG,response.body().string());
            JSONObject node = new JSONObject(response.body().string());
            final JSONArray rankings = node.getJSONArray("rankings");
            JSONArray categoryarray = node.getJSONArray("categories");

            for (int i =0 ; i< categoryarray.length(); i++){
                int catid = categoryarray.getJSONObject(i).getInt("id");
                String catname = categoryarray.getJSONObject(i).getString("name");
                //// prepare categories
                CategoryEntity entity = new CategoryEntity();
                entity.setId(catid);
                entity.setName(catname);
                catlist.add(entity);
                // prepare rankings

                // prepare child categories
                JSONArray childarray = categoryarray.getJSONObject(i).getJSONArray("child_categories");
                for (int j=0;j<childarray.length();j++){
                    ChildCatEntity c = new ChildCatEntity();
                    c.setCategoryid(catid);
                    c.setChildcatid(Integer.parseInt(""+childarray.getInt(j)));
                    childCatEntities.add(c);
                }
                /// prepare products
                JSONArray productarray = categoryarray.getJSONObject(i).getJSONArray("products");
                if (productarray != null && productarray.length() !=0){
                    for (int j =0; j< productarray.length();j++){
                        int productid = productarray.getJSONObject(j).getInt("id");
                        JSONObject taxobject = productarray.getJSONObject(j).getJSONObject("tax");
                        String taxname = taxobject.getString("name");
                        float taxvalue = Float.parseFloat(""+taxobject.get("value"));
                        String productname = productarray.getJSONObject(j).getString("name");
                        String date_added = productarray.getJSONObject(j).getString("date_added");
                        ProductEntity productEntity = new ProductEntity();
                        productEntity.setId(productid);
                        productEntity.setName(productname);
                        productEntity.setCategory_id(catid);
                        productEntity.setDate_added(date_added);
                        productEntity.setTax_cat(taxname);
                        productEntity.setTax_value(taxvalue);
                        productEntity.setOrder_count(0);
                        productEntity.setView_count(0);
                        productEntity.setShares(0);
                        prodlist.add(productEntity);
                        ///prepare variant
                        Type listType = new TypeToken<List<VariantEntity>>(){}.getType();
                        List<VariantEntity> varients = new Gson().fromJson(productarray.getJSONObject(j).getJSONArray("variants").toString(), listType);
                        for (VariantEntity e:varients) {
                            e.setProduct_id(productid);
                        }
                        varentitylist.addAll(varients);
                    }
                }
            }
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    // insert categories
                    if (catlist.size() !=0) {
                        CategoryEntity[] arr = new CategoryEntity[catlist.size()];
                        catlist.toArray(arr);
                        appDatabase.getCategoryDao().insertAll(arr);
                    }
                    // insert products
                    if (prodlist.size() !=0){
                        ProductEntity[] parr = new ProductEntity[prodlist.size()];
                        prodlist.toArray(parr);
                        appDatabase.getProductDao().insertAll(parr);
                    }
                    // insert variants
                    if (varentitylist.size() !=0){
                        VariantEntity[] varr = new VariantEntity[varentitylist.size()];
                        varentitylist.toArray(varr);
                        appDatabase.getVariantDao().insertAll(varr);
                    }

                    // insert child categories
                    if (childCatEntities.size() !=0){
                        ChildCatEntity[] carr = new ChildCatEntity[childCatEntities.size()];
                        childCatEntities.toArray(carr);
                        appDatabase.getChildCatDao().insertAll(carr);
                    }
                    /// now fill the rankings it is happening in background..while ui works
                    List<RankingEntity> rentitylist= new ArrayList<>();
                    if (rankings.length() !=0){
                        for (int i =0; i< rankings.length();i++){
                            try {
                                String rank = rankings.getJSONObject(i).getString("ranking");
                                Log.d(TAG,rank);
                                JSONArray carray = rankings.getJSONObject(i).getJSONArray("products");
                                for (int j=0;j<carray.length();j++){
                                    RankingEntity rankingEntity = new RankingEntity();
                                    JSONObject jsonobject = carray.getJSONObject(j);
                                    Iterator<String> keys = jsonobject.keys();
                                    rankingEntity.setRanking(rank);
                                    while(keys.hasNext() ) {
                                        String key = (String)keys.next();
                                        if (key.equalsIgnoreCase("id")){
                                            int id = jsonobject.getInt(key);

                                            rankingEntity.setProductid(id);

                                        }else {
                                            int count = jsonobject.getInt(key);

                                            rankingEntity.setCount(count);
                                        }
                                    }
                                    rentitylist.add(rankingEntity);
                                }
                                if (rentitylist.size() !=0){
                                    RankingEntity[] rarr = new RankingEntity[rentitylist.size()];
                                    rentitylist.toArray(rarr);
                                    appDatabase.getRankingDao().insertAll(rarr);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        if (t != null) {
            Log.d(TAG, t.getMessage());
        }else{
            Log.d(TAG,"Unknown error");
        }
    }
}
