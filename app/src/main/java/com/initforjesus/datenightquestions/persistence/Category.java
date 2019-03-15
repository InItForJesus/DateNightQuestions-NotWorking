package com.initforjesus.datenightquestions.persistence;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_table")
public class Category {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "category")
    private String category;

    public Category(@NonNull String category) {
        this.category = category;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }
}
