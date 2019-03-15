package com.initforjesus.datenightquestions.persistence;

import android.content.Context;

import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Question.class, Source.class, Category.class}, version = 1, exportSchema = false)
public abstract class QuestionDatabase extends RoomDatabase {

    private static volatile QuestionDatabase INSTANCE;

    public abstract QuestionDao questionDao();
    public abstract SourceDao sourceDao();
    public abstract CategoryDao categoryDao();

    public synchronized static QuestionDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (QuestionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }

        }
        return INSTANCE;
    }

    private static QuestionDatabase buildDatabase(final Context context) {
        System.out.println("QuestionDatabase.BuildDatabase");
        return Room.databaseBuilder(context.getApplicationContext(),
                QuestionDatabase.class, "Questions.db")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        System.out.println("QuestionDatabase.BuildDatabase -- onCreate");
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("QuestionDatabase.BuildDatabase -- onCreate -- call insertAll");
//                                getDatabase(context).questionDao().insertAll(QuestionDataLoader.loadInitialData());
                                try {
                                    //getDatabase(context).questionDao().insertAll(QuestionDataLoader.loadInitialDataFromAsset(context));
                                    System.out.println("QuestionDatabase.BuildDatabase -- onCreate -- call loadInitialDataFromJsonAsset");
                                    QuestionDataLoader.loadInitialDataFromJsonAsset(context);
                                } catch (Exception e)
                                {
                                    System.out.println(e);
                                }
                                System.out.println("QuestionDatabase.BuildDatabase -- onCreate -- insertAll done ");
                            }
                        });
                    }
                })
                .build();
    }
}
