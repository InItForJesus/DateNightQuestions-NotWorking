package com.initforjesus.datenightquestions.persistence;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "source_table")
public class Source {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sourceID")
    private String sourceID;

    @ColumnInfo(name = "sourceName")
    private String sourceName;

    @ColumnInfo(name = "sourceURL")
    private String sourceURL;

    public Source(@NonNull String sourceID, String sourceName, String sourceURL) {
        this.sourceID = sourceID;

        if (sourceName == null) {
            this.sourceName = sourceID;
        } else {
            this.sourceName = sourceName;
        }
        this.sourceURL = sourceURL;
    }

    @NonNull
    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(@NonNull String sourceID) {
        this.sourceID = sourceID;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        if (sourceName != null) {
            this.sourceName = sourceName;
        }
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }
}
