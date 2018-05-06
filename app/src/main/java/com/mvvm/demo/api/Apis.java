package com.mvvm.demo.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Apis {

    @GET("json")
    Call<ResponseBody> getJson();
}
