package com.initforjesus.datenightquestions.persistence;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity (tableName = "question_table",
         foreignKeys = {
         @ForeignKey (
                 entity = Source.class,
                 parentColumns = "sourceID",
                 childColumns = "sourceID",
                 onDelete = CASCADE
         ), @ForeignKey (
                entity = Category.class,
                parentColumns = "category",
                childColumns =  "category",
                onDelete = CASCADE
         )},
         indices = {@Index("sourceID"), @Index("category")})
public class Question {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name="answered")
    // day answered in the form YYYYMMDD
    // year*10000+month*100+day
    private int answered;

    private int timesSkipped;

    @NonNull
    @ColumnInfo(name="sourceID")
    private String sourceID;

    @NonNull
    @ColumnInfo(name="category")
    private String category;

    public Question(@NonNull String question, @NonNull String sourceID, @NonNull String category) {
        this.question = question;
        this.answered = -1;
        this.sourceID = sourceID;
        this.category = category;
        this.timesSkipped = 0;
    }

    @NonNull
    public String getQuestion() {
        return question;
    }

    public int getAnswered() {
        return answered;
    }

    public void setAnswered(int answered) {
        this.answered = answered;
    }

    public void setAnswered(Calendar dateAanswered) {

        this.answered = getDateStampFromCalendar(dateAanswered);
    }

    public static int getDateStampFromCalendar(Calendar calendar){
        // year*10000+month*100+day
        return calendar.get(Calendar.YEAR) * 10000 +
                (calendar.get(Calendar.MONTH)+1) * 100 +
                calendar.get(Calendar.DAY_OF_MONTH);
    }

    @NonNull
    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(@NonNull String sourceID) {
        this.sourceID = sourceID;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    public int getTimesSkipped() {
        return timesSkipped;
    }

    public void setTimesSkipped(int timesSkipped) {
        this.timesSkipped = timesSkipped;
    }
}
