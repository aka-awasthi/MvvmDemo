package com.mvvm.demo.Models;

import java.util.List;

public class ViewedModel {
    String ranking;
    List<ViewedProducts> products;

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public List<ViewedProducts> getProducts() {
        return products;
    }

    public void setProducts(List<ViewedProducts> products) {
        this.products = products;
    }
}
