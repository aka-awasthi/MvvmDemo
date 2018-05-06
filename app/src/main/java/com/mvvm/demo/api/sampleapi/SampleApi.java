package com.mvvm.demo.api.sampleapi;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.mvvm.demo.Constants;
import com.mvvm.demo.api.Apis;
import com.mvvm.demo.api.BaseLoader;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SampleApi  implements Callback<ResponseBody>,Constants {

    final String TAG = getClass().getName();
    public SampleApi(){
//        this.bitBnsViewModel = bitBnsViewModel;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SAMPLEAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Apis apis = retrofit.create(Apis.class);

        Call<ResponseBody> call = apis.getJson();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            Log.d(TAG,response.body().string());
        } catch (IOException e) {
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
