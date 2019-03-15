package com.initforjesus.datenightquestions.persistence;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class QuestionRepository {

    private QuestionDao questionDao;
    private SourceDao sourceDao;
    private CategoryDao categoryDao;
    private LiveData<List<Question>> allQuestions;
    private LiveData<List<Source>> allSources;
    private LiveData<List<Category>> allCategories;

    public QuestionRepository(Application application) {
        QuestionDatabase questionDatabase = QuestionDatabase.getDatabase(application);
        questionDao = questionDatabase.questionDao();
        sourceDao = questionDatabase.sourceDao();
        categoryDao = questionDatabase.categoryDao();
        allQuestions = questionDao.getAllQuestions();
        allSources = sourceDao.getAllSources();
        allCategories = categoryDao.getAllCategories();
    }

    public LiveData<List<Question>> getAllQuestions() {

        return allQuestions;
    }

    public LiveData<List<Source>> getAllSources() {
        return allSources;
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }


    //Not sure what this is doing TODO learn more about this
    public void update(Question question) {
        new updateQuesitionAsyncTask(questionDao).execute(question);
    }

    public void update(Source source) {

        new updatSourceAsyncTask(sourceDao).execute(source);
    }

    public void update(Category category) {

        new updateCategoryAsyncTask(categoryDao).execute(category);
    }

    public LiveData<Source> getSource(String sourceID) {
        return sourceDao.findSourceBySourceID(sourceID);
    }


    //Not sure what this is doing TODO learn more about this
    private static class updateQuesitionAsyncTask extends AsyncTask<Question, Void, Void> {

        private QuestionDao mAsyncTaskDao;

        updateQuesitionAsyncTask(QuestionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Question... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class updatSourceAsyncTask extends AsyncTask<Source, Void, Void> {

        private SourceDao mAsyncTaskDao;

        updatSourceAsyncTask(SourceDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Source... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class updateCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao mAsyncTaskDao;

        updateCategoryAsyncTask(CategoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Category... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
