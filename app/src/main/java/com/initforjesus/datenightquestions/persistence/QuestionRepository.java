package com.initforjesus.datenightquestions.persistence;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

public class QuestionRepository {

    private QuestionDao questionDao;
    private LiveData<List<Question>> allQuestions;

    public QuestionRepository(Application application) {
        QuestionDatabase questionDatabase = QuestionDatabase.getDatabase(application);
        questionDao = questionDatabase.questionDao();
        allQuestions = questionDao.getAllQuestions();
    }

    public LiveData<List<Question>> getAllQuestions() {
        return allQuestions;
    }

    //Not sure what this is doing TODO learn more about this
    public void update(Question question) {
        new updateAsyncTask(questionDao).execute(question);
    }

    //Not sure what this is doing TODO learn more about this
    private static class updateAsyncTask extends AsyncTask<Question, Void, Void> {

        private QuestionDao mAsyncTaskDao;

        updateAsyncTask(QuestionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Question... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
