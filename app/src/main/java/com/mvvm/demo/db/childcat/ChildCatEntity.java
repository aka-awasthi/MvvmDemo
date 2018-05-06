package com.mvvm.demo.db.childcat;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tbl_childcategory")
public class ChildCatEntity {

    int categoryid;

    @PrimaryKey
    int childcatid;

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getChildcatid() {
        return childcatid;
    }

    public void setChildcatid(int childcatid) {
        this.childcatid = childcatid;
    }
}
