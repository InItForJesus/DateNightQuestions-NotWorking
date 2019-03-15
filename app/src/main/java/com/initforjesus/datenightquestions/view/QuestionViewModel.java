package com.initforjesus.datenightquestions.view;

import android.app.Application;

import com.initforjesus.datenightquestions.persistence.Question;
import com.initforjesus.datenightquestions.persistence.QuestionRepository;
import com.initforjesus.datenightquestions.persistence.Source;

import java.util.List;
import java.util.Random;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class QuestionViewModel extends AndroidViewModel {

    private QuestionRepository questionRepository;
    private LiveData<List<Question>> allQuestions;
    private Random randomNumberGenerator;

    private Question selectedQuestion;

    public QuestionViewModel(Application application) {
        super(application);
        questionRepository = new QuestionRepository(application);
        randomNumberGenerator = new Random();
        allQuestions = questionRepository.getAllQuestions();
    }
    public LiveData<List<Question>> getAllQuestions() {
        return allQuestions;
    }

    public void update(Question question) {
        questionRepository.update(question);
    }

    public Question pickRandomQuestion() {
        List<Question> questions = allQuestions.getValue();
        if (questions != null && questions.size() > 0) {
            int randomIndex = randomNumberGenerator.nextInt(questions.size());
            selectedQuestion = questions.get(randomIndex);
        }
        else {
            selectedQuestion =  new Question("No Questions Available", "Unavailable", "Unavailable");
        }
        return selectedQuestion;
    }

    public Question getSelectedQuestion() {
        return selectedQuestion;
    }

    public LiveData<Source> getSource(String sourceID) {
        return questionRepository.getSource(sourceID);
    }
}
