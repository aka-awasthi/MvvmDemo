package com.mvvm.demo.api;

public abstract class BaseLoader {

    public abstract void showLoader();
    public abstract void dismissLoader();
    public abstract void onSuccess( String response);
    public abstract void onFailed(String message);


}
