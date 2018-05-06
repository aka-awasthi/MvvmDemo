package com.mvvm.demo.db.product;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tbl_product")
public class ProductEntity {

    @PrimaryKey
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTax_cat() {
        return tax_cat;
    }

    public void setTax_cat(String tax_cat) {
        this.tax_cat = tax_cat;
    }

    public float getTax_value() {
        return tax_value;
    }

    public void setTax_value(float tax_value) {
        this.tax_value = tax_value;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    int category_id;
    String name;
    String tax_cat;

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getOrder_count() {
        return order_count;
    }

    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    float tax_value;
    String date_added;
    int view_count;
    int order_count;
    int shares;
}
