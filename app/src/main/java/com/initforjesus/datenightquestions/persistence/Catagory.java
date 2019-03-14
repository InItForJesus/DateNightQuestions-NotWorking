package com.initforjesus.datenightquestions.persistence;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "source_table")
public class Catagory {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "catagory")
    private String catagory;

    public Catagory(@NonNull String catagory) {
        this.catagory = catagory;
    }

    @NonNull
    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(@NonNull String catagory) {
        this.catagory = catagory;
    }
}
