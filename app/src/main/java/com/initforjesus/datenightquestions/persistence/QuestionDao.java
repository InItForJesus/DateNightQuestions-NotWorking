package com.initforjesus.datenightquestions.persistence;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface QuestionDao {

    @Update
    void update(Question question);

    @Query("DELETE FROM question_table")
    void deleteAll();

    @Query("SELECT * from question_table ORDER BY question ASC")
    LiveData<List<Question>> getAllQuestions();

    @Insert
    void insertAll(Question... dataEntities);
}
