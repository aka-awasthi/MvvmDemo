package com.mvvm.demo.Models;

import java.util.List;

public class Category {
    int id;
    String name;
    List<Product> products;
    List<Integer> child_categories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Integer> getChild_categories() {
        return child_categories;
    }

    public void setChild_categories(List<Integer> child_categories) {
        this.child_categories = child_categories;
    }
}
