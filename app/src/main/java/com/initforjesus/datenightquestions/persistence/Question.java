package com.initforjesus.datenightquestions.persistence;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "question_table")
public class Question {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name="answered")
    // day answered in the form YYYYMMDD
    // year*10000+month*100+day
    private int answered;

    public Question(@NonNull String question) {
        this.question = question;
        this.answered = -1;
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
}
