package com.initforjesus.datenightquestions.persistence;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SourceDao {
    @Update
    void update(Source source);

    @Query("DELETE FROM source_table")
    void deleteAll();

    @Query("SELECT * from source_table WHERE sourceID = :sourceID")
    LiveData<Source> findSourceBySourceID(String sourceID);

    @Query("SELECT * from source_table ORDER BY sourceID ASC")
    LiveData<List<Source>> getAllSources();

    @Insert
    void insertAll(Source... dataEntities);

}
