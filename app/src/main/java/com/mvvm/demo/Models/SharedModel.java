package com.mvvm.demo.Models;

import java.util.List;

public class SharedModel {
    String ranking;
    List<SharedProducts> products;

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public List<SharedProducts> getProducts() {
        return products;
    }

    public void setProducts(List<SharedProducts> products) {
        this.products = products;
    }
}
