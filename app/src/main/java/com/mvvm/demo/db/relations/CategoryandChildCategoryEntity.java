package com.mvvm.demo.db.relations;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;

import com.mvvm.demo.db.category.CategoryEntity;
import com.mvvm.demo.db.childcat.ChildCatEntity;

public class CategoryandChildCategoryEntity {

    @Embedded
    CategoryEntity categoryEntity;

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public ChildCatEntity getChildCatEntity() {
        return childCatEntity;
    }

    public void setChildCatEntity(ChildCatEntity childCatEntity) {
        this.childCatEntity = childCatEntity;
    }

    @Embedded
    ChildCatEntity childCatEntity;
}
