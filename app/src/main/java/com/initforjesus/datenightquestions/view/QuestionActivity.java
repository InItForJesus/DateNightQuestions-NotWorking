package com.initforjesus.datenightquestions.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import com.initforjesus.datenightquestions.R;
import com.initforjesus.datenightquestions.persistence.Question;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private QuestionViewModel questionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question);
        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getAllQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(List<Question> questions) {
                displayRandomQuestion();
            }
        });
    }

    private void displayRandomQuestion() {
        Question question = questionViewModel.pickRandomQuestion();
        TextView textView = findViewById(R.id.textView);
        textView.setText(question.getQuestion());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Question question = questionViewModel.getSelectedQuestion();
        TextView textView = findViewById(R.id.textView);
        textView.setText(question.getQuestion());
    }
}
