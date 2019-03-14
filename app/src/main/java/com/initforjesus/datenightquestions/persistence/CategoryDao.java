package com.initforjesus.datenightquestions.persistence;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CategoryDao {
    @Update
    void update(Category category);

    @Query("DELETE FROM category_table")
    void deleteAll();

    @Query("SELECT * from category_table ORDER BY category ASC")
    LiveData<List<Category>> getAllCategories();

    @Insert
    void insertAll(Category... dataEntities);
}
