package com.mvvm.demo.Models;

import java.util.List;

public class OrderedModel {
    String ranking;
    List<OrderedProducts> products;

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public List<OrderedProducts> getProducts() {
        return products;
    }

    public void setProducts(List<OrderedProducts> products) {
        this.products = products;
    }
}
